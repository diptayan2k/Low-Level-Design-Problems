package services.impl;

import dao.BookingDao;
import dao.ParkingDao;
import models.Level;
import models.Slot;
import models.SlotStatus;
import models.VehicleType;
import services.AdminService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AdminServiceImpl implements AdminService {


    public ParkingDao parkingDao;
    public BookingDao bookingDao;

    public AdminServiceImpl(ParkingDao parkingDao, BookingDao bookingDao) {
        this.parkingDao = parkingDao;
        this.bookingDao = bookingDao;
    }

    @Override
    public void addLevel(String levelId, int bikeSLots, int carSlots, int busSlots){
        Level level = new Level(levelId);
        parkingDao.addLevel(level);
        addSlots(levelId, VehicleType.BIKE, bikeSLots);
        addSlots(levelId, VehicleType.CAR, carSlots);
        addSlots(levelId, VehicleType.BUS, busSlots);
    }

    @Override
    public void addSlots(String levelId, VehicleType type, int num){
        Level level = parkingDao.getLevel(levelId);
        AtomicInteger slitSize = level.getTotCount();
        for(int i = 0; i< num; i++){
            Slot slot = new Slot(String.format("%d",slitSize.addAndGet(1)),
                    type,
                    SlotStatus.AVAILABLE,
                    level.getId());
            level.addSlot(slot);
            parkingDao.addAvailableSlot(type, slot);
        }
    }

    @Override
    public void removeSlots(String levelId, VehicleType type, int num){
        Level level = new Level(levelId);
        // todo : to be implemented later
    }

    @Override
    public List<String> viewStatus(){
        Map<String, Level> levelMap = parkingDao.getLevels();
        List<String> status = new ArrayList<>();
        for (Map.Entry<String, Level> entry : levelMap.entrySet()){
            status.add(String.format("Level : %s Bike : %d car : %d, Bus %d",
                    entry.getKey(),
                    entry.getValue().getCount(VehicleType.BIKE),
                    entry.getValue().getCount(VehicleType.CAR),
                    entry.getValue().getCount(VehicleType.BUS)));
        }

        return status;

    }

}

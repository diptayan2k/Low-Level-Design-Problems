package dao.inMemory;

import dao.BookingDao;
import dao.ParkingDao;
import models.Parking;

import java.util.HashMap;
import java.util.Map;

public class BookingDaoImpl implements BookingDao {
    public Map<String, Parking> bookings;

    public BookingDaoImpl() {
        bookings = new HashMap<>();
    }

    @Override
    public void bookSlot(Parking parking){
        bookings.put(parking.getId(), parking);
    }


    @Override
    public void clearSlot(String parkingId, String vehicleNo){
        Parking parking = bookings.get(parkingId);
        if(parking == null){
            // todo
            throw  new IllegalArgumentException();
        }
        if(!vehicleNo.equals(parking.getVechicleNo())){
            // todo :
            throw new IllegalArgumentException();
        }
        bookings.remove(parking.getId());
    }

    @Override
    public Parking getBooking(String vehicleNo){
        Parking parking = bookings.get(vehicleNo);
        if(parking == null){
            // todo :
            throw  new IllegalArgumentException();
        }
        return  parking;
    }


}

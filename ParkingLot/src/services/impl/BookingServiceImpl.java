package services.impl;

import dao.BookingDao;
import dao.ParkingDao;
import exceptions.SlotAlreadyLocked;
import exceptions.SlotNotFound;
import models.Parking;
import models.Slot;
import models.SlotStatus;
import models.VehicleType;
import provider.lock.SlotLockProvider;
import provider.slot.SlotProvider;
import services.BookingService;
import services.PaymentService;

import java.time.LocalDateTime;

public class BookingServiceImpl implements BookingService {

    public SlotProvider slotProvider;
    public SlotLockProvider slotLockProvider;

    public BookingDao bookingDao;
    public ParkingDao parkingDao;

    public PaymentService paymentService;

    public BookingServiceImpl(SlotProvider slotProvider, SlotLockProvider slotLockProvider, BookingDao bookingDao, ParkingDao parkingDao, PaymentService paymentService) {
        this.slotProvider = slotProvider;
        this.slotLockProvider = slotLockProvider;
        this.bookingDao = bookingDao;
        this.parkingDao = parkingDao;
        this.paymentService = paymentService;
    }

    @Override
    public Parking parkVehicle(VehicleType type, String lisencePlate) throws SlotNotFound, SlotAlreadyLocked {
        Slot slot = slotProvider.getSlot(type);
        if(slot == null){
            throw new SlotNotFound("Slot not found for " + lisencePlate);
        }

        slotLockProvider.getSLotLock(slot.getUniqueId(), lisencePlate);
        try {
            Parking booking = bookingDao.getBooking(lisencePlate);
            throw new SlotAlreadyLocked("Incalid vehicle no");
        }catch (IllegalArgumentException e){
        }
        Parking parking = new Parking(LocalDateTime.now(), lisencePlate, slot.getLevelId(), slot.getId());
        bookingDao.bookSlot(parking);
        parkingDao.removeAvailableSlotParking(type, slot);
        parkingDao.setSlotStatus(slot.getLevelId(), slot.getId(), SlotStatus.BOOKED);
        slotLockProvider.releaseSlotLock(slot.getUniqueId(), lisencePlate);
        return parking;

    }

    @Override
    public float exitVehicle(String lisencePlate) throws SlotAlreadyLocked {
        Parking parking = bookingDao.getBooking(lisencePlate);
        if(parking == null){
            throw  new IllegalArgumentException("Vehicle not parked");
        }
        Slot slot = parkingDao.getSlot(parking.getLevelId(), parking.getSlotId());
        float amount = paymentService.getAmount(parking, slot);
        slotLockProvider.getSLotLock(slot.getUniqueId(), lisencePlate);
        bookingDao.clearSlot(parking.getId(), lisencePlate);
        parkingDao.addAvailableSlotParking(slot.getSlotType(), slot);
        parkingDao.setSlotStatus(slot.getLevelId(), slot.getId(), SlotStatus.AVAILABLE);
        slotLockProvider.releaseSlotLock(slot.getUniqueId(), lisencePlate);


        return amount;
    }

}

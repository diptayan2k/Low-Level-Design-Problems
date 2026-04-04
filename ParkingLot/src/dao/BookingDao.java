package dao;

import models.Parking;

public interface BookingDao {
    void bookSlot(Parking parking);

    void clearSlot(String parkingId, String vehicleNo);

    Parking getBooking(String vehicleNo);
}

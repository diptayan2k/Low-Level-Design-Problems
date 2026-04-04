package services;

import exceptions.SlotAlreadyLocked;
import exceptions.SlotNotFound;
import models.Parking;
import models.VehicleType;

public interface BookingService {
    Parking parkVehicle(VehicleType type, String lisencePlate) throws SlotNotFound, SlotAlreadyLocked;

    float exitVehicle(String lisencePlate) throws SlotAlreadyLocked;
}

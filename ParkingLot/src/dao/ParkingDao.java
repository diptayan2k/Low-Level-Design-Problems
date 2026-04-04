package dao;

import models.Level;
import models.Slot;
import models.SlotStatus;
import models.VehicleType;

import java.util.List;
import java.util.Map;

public interface ParkingDao {
    void addLevel(Level level);

    void addSlots(String levelId, List<Slot> slots);

    Slot getSlot(String levelId, String slotId);

    void setSlotStatus(String leveId, String slotId, SlotStatus status);

    void setSlotStatusAvailable(String leveId, String slotId);

    void setSlotStatusNotAvailable(String leveId, String slotId);

    void addAvailableSlot(VehicleType type, Slot slot);

    void addAvailableSlotParking(VehicleType type, Slot slot);

    void removeAvailableSlot(VehicleType type, Slot slot);

    void removeAvailableSlotParking(VehicleType type, Slot slot);

    Slot getSlot(VehicleType vehicleType);

    Map<String, Level> getLevels();


    Level getLevel(String id);
}

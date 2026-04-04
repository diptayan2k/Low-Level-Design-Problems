package provider.slot;

import models.Slot;
import models.VehicleType;

public interface SlotProvider {


    Slot getSlot(VehicleType type);
}

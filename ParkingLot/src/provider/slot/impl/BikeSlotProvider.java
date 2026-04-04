package provider.slot.impl;

import dao.ParkingDao;
import models.Slot;
import models.VehicleType;
import provider.slot.SlotProvider;

public class BikeSlotProvider  extends AbstractSlotProvider {

    public BikeSlotProvider(SlotProvider nextSLotProvider, ParkingDao parkingDao) {
        super(nextSLotProvider, parkingDao);
    }

    public int getPrioritySize(){
        return VehicleType.BIKE.getPrioritySize();
    }

}

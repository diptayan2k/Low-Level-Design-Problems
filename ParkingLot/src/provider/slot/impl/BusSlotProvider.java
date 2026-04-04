package provider.slot.impl;

import dao.ParkingDao;
import models.VehicleType;
import provider.slot.SlotProvider;

public class BusSlotProvider extends AbstractSlotProvider {

    public BusSlotProvider(SlotProvider nextSLotProvider, ParkingDao parkingDao) {
        super(nextSLotProvider, parkingDao);
    }
    @Override
    public int getPrioritySize(){
        return VehicleType.BUS.getPrioritySize();
    }
}

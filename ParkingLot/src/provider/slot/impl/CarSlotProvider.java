package provider.slot.impl;

import dao.ParkingDao;
import models.VehicleType;
import provider.slot.SlotProvider;

public class CarSlotProvider extends AbstractSlotProvider {
    public SlotProvider nextSlotProvider;

    public CarSlotProvider(SlotProvider nextSLotProvider, ParkingDao parkingDao) {
        super(nextSLotProvider, parkingDao);
    }
    @Override
    public int getPrioritySize(){
        return VehicleType.CAR.getPrioritySize();
    }
}

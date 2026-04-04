package provider.slot.impl;

import dao.ParkingDao;
import models.Slot;
import models.VehicleType;
import provider.slot.SlotProvider;

public abstract class AbstractSlotProvider implements SlotProvider {

    public SlotProvider nextSlotProvider;
    public ParkingDao parkingDao;



    public AbstractSlotProvider(SlotProvider nextSlotProvider, ParkingDao parkingDao) {
        this.nextSlotProvider = nextSlotProvider;
        this.parkingDao = parkingDao;
    }

    @Override
    public Slot getSlot(VehicleType type){
        Slot slot = null;
        if(type.getPrioritySize() <= getPrioritySize()){
            slot = parkingDao.getSlot(type);
            if(slot!=null)  return slot;
        }
        if (nextSlotProvider == null) return slot;
        return nextSlotProvider.getSlot(type);
    }

    public abstract int getPrioritySize();


}

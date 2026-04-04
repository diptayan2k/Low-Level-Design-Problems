package dao.inMemory;

import dao.ParkingDao;
import models.Level;
import models.Slot;
import models.SlotStatus;
import models.VehicleType;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ParkingDaoImpl implements ParkingDao {

    public Map<String, Level> levels;
    public Map<VehicleType, Map<String, Slot> > availableSlots;

    public ParkingDaoImpl() {
        levels = new HashMap<>();
        availableSlots = new HashMap<>();
    }

    @Override
    public void addLevel(Level level){
        levels.put(level.getId(), level);
    }

    @Override
    public void addSlots(String levelId, List<Slot> slots){
        Level level = levels.get(levelId);
        if(level == null){
            // todo : handle this
            return;
        }
        for (Slot slot : slots){
            level.addSlot(slot);
        }
    }


    @Override
    public Slot getSlot(String levelId, String slotId){
        Level level = levels.get(levelId);
        if(level == null){
            // todo : handle this
            return null;
        }
        Slot slot = level.getSlot(slotId);
        return slot;
    }

    @Override
    public void setSlotStatus(String leveId, String slotId, SlotStatus status){
        Slot slot = getSlot(leveId, slotId);
        slot.setStatus(status);
    }

    @Override
    public void setSlotStatusAvailable(String leveId, String slotId){
        setSlotStatus( leveId,  slotId, SlotStatus.AVAILABLE);
        Level level = getLevel(leveId);
        AtomicInteger totCnt = level.getTotCount();
        totCnt.addAndGet(1);
    }

    @Override
    public void setSlotStatusNotAvailable(String leveId, String slotId){
        setSlotStatus( leveId,  slotId, SlotStatus.BOOKED);
        Level level = getLevel(leveId);
        AtomicInteger totCnt = level.getTotCount();

    }

    @Override
    public void addAvailableSlot(VehicleType type, Slot slot){
        Map<String, Slot> slots = availableSlots.getOrDefault(type, new HashMap<>());
        slots.put(slot.getUniqueId(), slot);
        availableSlots.put(type, slots);

    }

    @Override
    public void addAvailableSlotParking(VehicleType type, Slot slot){
        Map<String, Slot> slots = availableSlots.getOrDefault(type, new HashMap<>());
        Level level = getLevel(slot.getLevelId());
        Map<VehicleType , AtomicInteger> cnt = level.count;
        AtomicInteger val = cnt.get(type);
        val.addAndGet(1);
        slots.put(slot.getUniqueId(), slot);
        availableSlots.put(type, slots);

    }

    @Override
    public void removeAvailableSlot(VehicleType type, Slot slot){
        Map<String, Slot> slots = availableSlots.get(type);
        slots.remove(slot.getUniqueId());
        availableSlots.put(type, slots);
    }

    @Override
    public void removeAvailableSlotParking(VehicleType type, Slot slot) {
        Map<String, Slot> slots = availableSlots.get(type);
        Level level = getLevel(slot.getLevelId());
        Map<VehicleType , AtomicInteger> cnt = level.count;
        AtomicInteger val = cnt.get(type);
        val.addAndGet(-1);
        slots.remove(slot.getUniqueId());
        availableSlots.put(type, slots);
    }

    @Override
    public Slot getSlot(VehicleType vehicleType){
        Map<String, Slot> slots = availableSlots.getOrDefault(vehicleType, new HashMap<>());

        if(slots.isEmpty()){
            return null;
        }
        for (Map.Entry<String, Slot> slot : slots.entrySet()){
            if(slot!=null){
                return  slot.getValue();
            }
        }
        return null;
    }

    @Override
    public Map<String, Level> getLevels(){
        return  this.levels;
    }

    @Override
    public Level getLevel(String id){
        return this.levels.get(id);
    }



}

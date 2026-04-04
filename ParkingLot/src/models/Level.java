package models;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Level {

    public String id;
    public Map<String, Slot> slots;

    public Map<VehicleType, AtomicInteger> count;

    public AtomicInteger totCount;

    public Level(String id) {
        this.id = id;
        this.totCount = new AtomicInteger(0);
        this.slots = new HashMap<>();
        this.count = new HashMap<>();
    }

    public AtomicInteger getTotCount(){
        return this.totCount;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount(VehicleType type){
        AtomicInteger val =  count.getOrDefault(type, new AtomicInteger(0));
        return val.get();
    }

    public void addSlot(Slot slot){
        slots.put(slot.getId(), slot);
        AtomicInteger oldCount = count.getOrDefault(slot.getSlotType(), new AtomicInteger(0));
        oldCount.addAndGet(1);
        count.put(slot.getSlotType(), oldCount);
    }

    public void removeSlot(Slot slot){
        AtomicInteger oldCount = count.getOrDefault(slot.getSlotType(), new AtomicInteger(0));
        oldCount.addAndGet(-1);
        count.put(slot.getSlotType(), oldCount);
    }

    public Slot getSlot(String slotId){
        Slot slot = slots.get(slotId);
        if (slot == null){
            // todo
            throw new IllegalArgumentException();
        }
        return slot;
    }
}




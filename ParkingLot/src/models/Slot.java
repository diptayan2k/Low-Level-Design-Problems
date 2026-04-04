package models;

public class Slot {
    public String id;
    public VehicleType slotType;
    public SlotStatus status;

    public String levelId;

    public Slot(String id, VehicleType slotType, SlotStatus status, String levelId) {
        this.id = id;
        this.slotType = slotType;
        this.status = status;
        this.levelId = levelId;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getId() {
        return id;
    }

    public VehicleType getSlotType() {
        return slotType;
    }

    public SlotStatus getStatus() {
        return status;
    }

    public void setStatus(SlotStatus status) {
        this.status = status;
    }

    public String getUniqueId(){
        return String.format("%s:%s", levelId, id);
    }
}

package models;

import java.time.LocalDateTime;

public class Parking {
    public String id;
    public LocalDateTime entryTime;
    public String vechicleNo;
    public String levelId;
    public String slotId;


    public Parking(LocalDateTime entryTime, String vechicleNo, String levelId, String slotId) {
        this.entryTime = entryTime;
        this.vechicleNo = vechicleNo;
        this.levelId = levelId;
        this.slotId = slotId;
        this.id = vechicleNo;
    }

    public String getLevelId() {
        return levelId;
    }

    public String getSlotId() {
        return slotId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public String getVechicleNo() {
        return vechicleNo;
    }

    public void setVechicleNo(String vechicleNo) {
        this.vechicleNo = vechicleNo;
    }

    @Override
    public String toString() {
        return "Parking{" +
                "id='" + id + '\'' +
                ", entryTime=" + entryTime +
                ", vechicleNo='" + vechicleNo + '\'' +
                ", levelId='" + levelId + '\'' +
                ", slotId='" + slotId + '\'' +
                '}';
    }
}

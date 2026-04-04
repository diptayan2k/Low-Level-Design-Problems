package models;

public enum VehicleType {
    BIKE(1),
    CAR(2),
    BUS(3);

    private int prioritySize;

    VehicleType(int prioritySize) {
        this.prioritySize = prioritySize;
    }

    public int getPrioritySize() {
        return prioritySize;
    }

    public void setPrioritySize(int prioritySize) {
        this.prioritySize = prioritySize;
    }
}

package models;

public enum Level {

    DEBUG(1),
    INFO(2),
    WARN(3),
    ERROR(4),
    FATAL(5);

    public int priority;

    Level(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}

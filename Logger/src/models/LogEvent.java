package models;

import models.Level;

import java.time.LocalDateTime;

public class LogEvent {

    public String message;
    public LocalDateTime timestamp;
    public String source;

    public Level level;


    public LogEvent(String message, Level level) {
        this.message = message;
        this.level = level;
        this.source = Thread.currentThread().getName();
        this.timestamp = LocalDateTime.now();
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "LogEvent{" +
                "message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", source='" + source + '\'' +
                ", level=" + level +
                '}';
    }
}

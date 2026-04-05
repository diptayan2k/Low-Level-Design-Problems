package logger;

import models.Level;

public interface LogStrategy {

    void publish(Level level, String message) throws InterruptedException;

    void sendLogs() throws InterruptedException;
}

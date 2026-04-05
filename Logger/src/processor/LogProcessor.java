package processor;

import models.LogEvent;
import observers.Observer;

public interface LogProcessor {
    void addObserver(Observer observer);

    void sendLogs(LogEvent event);
}

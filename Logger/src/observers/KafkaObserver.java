package observers;

import models.LogEvent;
import processor.LogProcessor;

public class KafkaObserver implements Observer{

    @Override
    public void publish(LogEvent logEvent) {
        System.out.println(String.format("Log published to kafka %s",logEvent.toString()));
    }

    @Override
    public void setMinLevel(LogProcessor logProcessor) {
        logProcessor.addObserver(this);
    }
}

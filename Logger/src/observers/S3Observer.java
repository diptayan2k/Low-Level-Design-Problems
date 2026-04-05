package observers;

import models.Level;
import models.LogEvent;
import processor.LogProcessor;

public class S3Observer implements Observer{


    @Override
    public void publish(LogEvent logEvent) {
        System.out.println(String.format("Log published to s3 %s",logEvent.toString()));
    }

    @Override
    public void setMinLevel(LogProcessor logProcessor) {
        logProcessor.addObserver(this);

    }
}

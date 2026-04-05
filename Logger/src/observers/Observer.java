package observers;

import models.Level;
import models.LogEvent;
import processor.LogProcessor;

public interface Observer {

    public void publish(LogEvent logEvent);

    void setMinLevel(LogProcessor logProcessor);
}

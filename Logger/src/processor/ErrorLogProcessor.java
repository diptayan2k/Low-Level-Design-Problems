package processor;

import models.Level;
import models.LogEvent;

public class ErrorLogProcessor extends AbstractLogProcessor {

    public ErrorLogProcessor(LogProcessor nextLogProcessor, Level level) {
        super(nextLogProcessor, level);
    }

}

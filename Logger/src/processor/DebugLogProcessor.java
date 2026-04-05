package processor;

import models.Level;

public class DebugLogProcessor extends AbstractLogProcessor {

    public DebugLogProcessor(LogProcessor nextLogProcessor, Level level) {
        super(nextLogProcessor, level);
    }
}

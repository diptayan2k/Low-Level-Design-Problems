package processor;

import models.Level;

public class WarnLogProcessor extends AbstractLogProcessor {

    public WarnLogProcessor(LogProcessor nextLogProcessor, Level level) {
        super(nextLogProcessor, level);
    }
}

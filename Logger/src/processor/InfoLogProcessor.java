package processor;

import models.Level;

public class InfoLogProcessor extends AbstractLogProcessor {

    public InfoLogProcessor(LogProcessor nextLogProcessor, Level level) {
        super(nextLogProcessor, level);
    }
}

package processor;

import models.Level;

public class FatalLogProcessor extends AbstractLogProcessor {

    public FatalLogProcessor(LogProcessor nextLogProcessor, Level level) {
        super(nextLogProcessor, level);
    }

}

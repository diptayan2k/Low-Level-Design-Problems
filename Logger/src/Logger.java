import models.Level;
import logger.LogStrategy;

public class Logger {

    public LogStrategy logProcessor;

    public static volatile Logger loggerInstance;

    public static Logger getInstance(LogStrategy logStrategy){
        if(loggerInstance == null){
            synchronized (Logger.class){
                if(loggerInstance == null){
                    loggerInstance = new Logger(logStrategy);
                }
            }
        }
        return loggerInstance;
    }

    private Logger(LogStrategy logProcessor) {
        this.logProcessor = logProcessor;
    }

    public void info(String message) throws InterruptedException {
        logProcessor.publish(Level.INFO, message);
    }

    public void debug(String message) throws InterruptedException {
        logProcessor.publish(Level.DEBUG, message);
    }

    public void error(String message) throws InterruptedException {
        logProcessor.publish(Level.ERROR, message);

    }

    public void fatal(String message) throws InterruptedException {
        logProcessor.publish(Level.FATAL, message);
    }

    public void warn(String message) throws InterruptedException {
        logProcessor.publish(Level.WARN, message);
    }



}

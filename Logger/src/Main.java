import logger.AsyncLogStrategy;
import logger.LogStrategy;
import models.Level;
import observers.KafkaObserver;
import observers.Observer;
import observers.S3Observer;
import processor.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        LogProcessor debugLogProcessor = new DebugLogProcessor(null, Level.DEBUG);
        LogProcessor infoLogProcessor = new InfoLogProcessor(debugLogProcessor, Level.INFO);
        LogProcessor warnLogProcessor = new WarnLogProcessor(infoLogProcessor, Level.WARN);
        LogProcessor errorLogProcessor = new ErrorLogProcessor(warnLogProcessor, Level.ERROR);
        LogProcessor fatalLogProcessor = new FatalLogProcessor(errorLogProcessor, Level.FATAL);

        LogStrategy asyncLogStrategy = new AsyncLogStrategy(5, fatalLogProcessor);

        Observer s3Observer = new S3Observer();
        Observer kafkaObserver = new KafkaObserver();

        s3Observer.setMinLevel(fatalLogProcessor);
        kafkaObserver.setMinLevel(debugLogProcessor);

        Logger logger = Logger.getInstance(asyncLogStrategy);
        Thread thread = new Thread(()->{
            try {
                asyncLogStrategy.sendLogs();
            }catch (InterruptedException e){
                System.out.println("Unable to send logs");
            }
        });
        thread.start();




        logger.fatal("This is fatal message");
        logger.info("This is info message");


    }
}
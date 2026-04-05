import logger.AsyncLogStrategy;
import logger.LogStrategy;
import models.Level;
import observers.KafkaObserver;
import observers.Observer;
import observers.S3Observer;
import processor.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {


        LogProcessor fatalLogProcessor = new FatalLogProcessor(null, Level.FATAL);
        LogProcessor errorLogProcessor = new ErrorLogProcessor(fatalLogProcessor, Level.ERROR);
        LogProcessor warnLogProcessor = new WarnLogProcessor(errorLogProcessor, Level.WARN);
        LogProcessor infoLogProcessor = new InfoLogProcessor(warnLogProcessor, Level.INFO);
        LogProcessor debugLogProcessor = new DebugLogProcessor(infoLogProcessor, Level.DEBUG);


        LogStrategy asyncLogStrategy = new AsyncLogStrategy(5, debugLogProcessor);

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
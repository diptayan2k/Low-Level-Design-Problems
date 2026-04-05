package logger;

import blockingQueue.BLockingQueue;
import models.Level;
import models.LogEvent;
import processor.LogProcessor;


public class AsyncLogStrategy implements LogStrategy {

    public BLockingQueue<LogEvent> blockingQueue;
    public LogProcessor logProcessor;
    public AsyncLogStrategy(int capacity, LogProcessor baseLogProcessor) {
        blockingQueue = new BLockingQueue<>(capacity);
        logProcessor = baseLogProcessor;
    }

    @Override
    public void publish(Level level, String message) throws InterruptedException {
        LogEvent logEvent = new LogEvent(message, level);
        blockingQueue.produce(logEvent);
    }


    @Override
    public void sendLogs() throws InterruptedException {
        while (true){
            LogEvent event = blockingQueue.consume();
            logProcessor.sendLogs(event);
        }
    }

}

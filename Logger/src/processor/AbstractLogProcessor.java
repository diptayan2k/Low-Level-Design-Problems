package processor;

import models.Level;
import models.LogEvent;
import observers.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLogProcessor implements LogProcessor {

    public LogProcessor nextLogProcessor;
    public List<Observer> observers;
    public Level level;

    public AbstractLogProcessor(LogProcessor nextLogProcessor, Level level) {
        this.nextLogProcessor = nextLogProcessor;
        this.level = level;
        this.observers = new ArrayList<>();
    }

    @Override
    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void setNextLogProcessor(LogProcessor nextLogProcessor) {
        this.nextLogProcessor = nextLogProcessor;
    }


    @Override
    public void sendLogs(LogEvent event){
        //System.out.println("Inside  " + this.getClass().getName() + " " + event.toString());
        for (Observer observer : observers){
            observer.publish(event);
        }
        if(nextLogProcessor!= null && level.getPriority() >= event.getLevel().getPriority()){
            nextLogProcessor.sendLogs(event);
        }
    }

}

package blockingQueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BLockingQueue<T> {

    public Queue<T> queue;
    public int capacity;
    public ReentrantLock lock;
    public Condition empty;
    public Condition full;


    public BLockingQueue(int capacity) {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
        this.lock = new ReentrantLock();
        this.empty = lock.newCondition();
        this.full = lock.newCondition();
    }

    public void produce(T event) throws InterruptedException{
        lock.lock();
        try {
            while(capacity == queue.size()) full.await();
            queue.add(event);
            if(queue.size()==1) empty.signal();
        }finally {
            lock.unlock();
        }
    }

    public T consume() throws InterruptedException {
        T event = null;
        lock.lock();
        try {
            while(queue.isEmpty()) empty.await();
            event = queue.poll();
            if(queue.size() == capacity - 1) full.signal();
        }finally {
            lock.unlock();
        }
        return event;
    }
}

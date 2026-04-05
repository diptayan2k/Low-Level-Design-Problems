package provider.lock.impl;

import exceptions.SlockUnlockException;
import exceptions.SlotAlreadyLocked;
import provider.lock.SlotLockProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class SlotLockProviderImpl implements SlotLockProvider {

    public Map<String, String> slotLocks;
    public Map<String, ReentrantLock> locks;

    public SlotLockProviderImpl() {
        slotLocks = new ConcurrentHashMap<>();
        locks = new ConcurrentHashMap<>();
    }

    @Override
    public void getSLotLock(String id, String lockedBy) throws SlotAlreadyLocked {

        ReentrantLock lock = new ReentrantLock();
        locks.putIfAbsent(id, lock);
        lock = locks.get(id);
        lock.lock();
        try{
            if(slotLocks.containsKey(id)){
                throw  new SlotAlreadyLocked("Slot already locked "+ id);
            }
            slotLocks.put(id, lockedBy);
        } finally {
            lock.unlock();
        }
    }



    @Override
    public void releaseSlotLock(String id, String lockedBy) {
        ReentrantLock lock = locks.get(id);
        if(lock == null){
            throw  new IllegalArgumentException("Slot not locked "+ id);
        }
        lock.lock();
        try {
            if (!slotLocks.containsKey(id)){
                return;
            }
            if(slotLocks.get(id).equals(lockedBy)){
                slotLocks.remove(id);
            } else{
                throw new SlockUnlockException("Access denied");
            }
        } catch (SlockUnlockException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }







}

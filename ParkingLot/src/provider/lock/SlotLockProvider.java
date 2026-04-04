package provider.lock;

import exceptions.SlotAlreadyLocked;

public interface SlotLockProvider {

    void getSLotLock(String id, String lockedBy) throws SlotAlreadyLocked;

    void releaseSlotLock(String id, String lockedBy);
}

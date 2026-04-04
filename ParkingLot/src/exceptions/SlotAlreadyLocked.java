package exceptions;

public class SlotAlreadyLocked extends Exception{

    public SlotAlreadyLocked(String message) {
        super(message);
    }
}

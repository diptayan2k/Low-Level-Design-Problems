package strategy;

import models.Parking;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class BusPaymentStrategy implements PaymentStrategy {

    @Override
    public float getAmount(Parking parking, LocalDateTime tillTime){
        LocalDateTime startTime = parking.getEntryTime();
        long diffSeconds = ChronoUnit.SECONDS.between(startTime, tillTime);
        return  6 * diffSeconds;
    }
}

package strategy;

import models.Parking;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CarPaymentStrategy implements  PaymentStrategy {

    @Override
    public float getAmount(Parking parking, LocalDateTime tillTime){
        LocalDateTime startTime = parking.getEntryTime();
        long diffSeconds = ChronoUnit.SECONDS.between(startTime, tillTime);
        return  3 * diffSeconds;
    }

}

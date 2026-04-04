package strategy;

import models.Parking;

import java.time.LocalDateTime;

public interface PaymentStrategy {
    float getAmount(Parking parking, LocalDateTime tillTime);
}

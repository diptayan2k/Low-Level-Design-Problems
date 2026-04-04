package services;

import models.Parking;
import models.Slot;

public interface PaymentService {
    float getAmount(Parking parking, Slot slot);
}

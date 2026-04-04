package services.impl;

import models.Parking;
import models.Slot;
import services.PaymentService;
import strategy.BikePaymentStrategy;
import strategy.BusPaymentStrategy;
import strategy.CarPaymentStrategy;
import strategy.PaymentStrategy;

import java.time.LocalDateTime;

public class PaymentServiceImpl implements PaymentService {

    public PaymentServiceImpl() {
    }

    @Override
    public  float getAmount(Parking parking, Slot slot){

        PaymentStrategy paymentStrategy = null;

        switch (slot.getSlotType()) {
            case BIKE -> paymentStrategy = new BikePaymentStrategy();
            case CAR ->  paymentStrategy = new CarPaymentStrategy();
            case BUS -> paymentStrategy = new BusPaymentStrategy();
        }
        return paymentStrategy.getAmount(parking, LocalDateTime.now());
    }

}

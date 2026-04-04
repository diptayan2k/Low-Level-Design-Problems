import dao.BookingDao;
import dao.ParkingDao;
import dao.inMemory.BookingDaoImpl;
import dao.inMemory.ParkingDaoImpl;
import exceptions.SlotAlreadyLocked;
import exceptions.SlotNotFound;
import models.Parking;
import models.Slot;
import models.VehicleType;
import provider.lock.SlotLockProvider;
import provider.lock.impl.SlotLockProviderImpl;
import provider.slot.SlotProvider;
import provider.slot.impl.BikeSlotProvider;
import provider.slot.impl.BusSlotProvider;
import provider.slot.impl.CarSlotProvider;
import services.AdminService;
import services.BookingService;
import services.PaymentService;
import services.impl.AdminServiceImpl;
import services.impl.BookingServiceImpl;
import services.impl.PaymentServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) throws SlotNotFound, SlotAlreadyLocked, InterruptedException {
        ParkingDao parkingDao = new ParkingDaoImpl();
        BookingDao bookingDao = new BookingDaoImpl();
        SlotLockProvider slotLockProvider = new SlotLockProviderImpl();
        SlotProvider busSlotProvider = new BusSlotProvider(null, parkingDao);
        SlotProvider carSlotProvider = new CarSlotProvider(busSlotProvider, parkingDao);
        SlotProvider bikeSlotProvider = new BikeSlotProvider(carSlotProvider, parkingDao);

        AdminService adminService = new AdminServiceImpl(parkingDao, bookingDao);
        PaymentService paymentService = new PaymentServiceImpl();
        BookingService bookingService = new BookingServiceImpl(bikeSlotProvider, slotLockProvider, bookingDao, parkingDao, paymentService);

        adminService.addLevel("1", 10, 20, 5);
        List<String> status = adminService.viewStatus();
        for(String statis : status){
            System.out.println(statis);
        }
        Parking parking = bookingService.parkVehicle(VehicleType.CAR, "KA-01-HH-1234");

        System.out.println(parking.toString());
        status = adminService.viewStatus();
        for(String statis : status){
            System.out.println(statis);
        }
        Thread.sleep(5000);
        float price = bookingService.exitVehicle("KA-01-HH-1234");
        System.out.println(price);
        status = adminService.viewStatus();
        for(String statis : status){
            System.out.println(statis);
        }
    }
}
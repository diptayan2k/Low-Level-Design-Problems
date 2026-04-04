package services;

import models.VehicleType;

import java.util.List;

public interface AdminService {
    void addLevel(String levelId, int bikeSLots, int carSlots, int busSlots);

    void addSlots(String levelId, VehicleType type, int num);

    void removeSlots(String levelId, VehicleType type, int num);

    List<String> viewStatus();
}

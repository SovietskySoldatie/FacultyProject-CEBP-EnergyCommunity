package EnergyCommunity.core.service;

import EnergyCommunity.core.model.Battery;
import EnergyCommunity.core.model.House;
import EnergyCommunity.core.model.SolarPanel;

import java.util.List;

public interface HouseService {
    List<House> findAllHouses();
    House findHouseById(Long id);
    List<House> findHousesByUserId(Long userId);
    House saveHouse(House house);
    House saveHouseForUser(Long userId, House house);
    House updateHouse(Long id, House house);
    void deleteHouse(Long id);

    List<Battery> findBatteriesByHouseId(Long houseId);
    Battery addBatteryToHouse(Long houseId, Battery battery);
    Battery updateBattery(Long batteryId, Battery battery);
    void deleteBattery(Long batteryId);

    List<SolarPanel> findSolarPanelsByHouseId(Long houseId);
    SolarPanel addSolarPanelToHouse(Long houseId, SolarPanel solarPanel);
    SolarPanel updateSolarPanel(Long panelId, SolarPanel solarPanel);
    void deleteSolarPanel(Long panelId);
}

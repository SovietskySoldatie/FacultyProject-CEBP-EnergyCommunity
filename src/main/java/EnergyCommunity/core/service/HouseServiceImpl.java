package EnergyCommunity.core.service;

import EnergyCommunity.core.model.Battery;
import EnergyCommunity.core.model.House;
import EnergyCommunity.core.model.SolarPanel;
import EnergyCommunity.core.model.User;
import EnergyCommunity.core.repository.BatteryRepository;
import EnergyCommunity.core.repository.HouseRepository;
import EnergyCommunity.core.repository.SolarPanelRepository;
import EnergyCommunity.core.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final UserRepository userRepository;
    private final BatteryRepository batteryRepository;
    private final SolarPanelRepository solarPanelRepository;

    public HouseServiceImpl(HouseRepository houseRepository,
                            UserRepository userRepository,
                            BatteryRepository batteryRepository,
                            SolarPanelRepository solarPanelRepository) {
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
        this.batteryRepository = batteryRepository;
        this.solarPanelRepository = solarPanelRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<House> findAllHouses() {
        return houseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public House findHouseById(Long id) {
        return houseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "House not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<House> findHousesByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found with id: " + userId);
        }
        return houseRepository.findByUserId(userId);
    }

    @Override
    public House saveHouse(House house) {
        if (house.getUser() != null && house.getUser().getId() != null) {
            User owner = userRepository.findById(house.getUser().getId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "User not found with id: " + house.getUser().getId()));
            house.setUser(owner);
        }
        linkHardwareToHouse(house);
        return houseRepository.save(house);
    }

    @Override
    public House saveHouseForUser(Long userId, House house) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id: " + userId));
        house.setUser(owner);
        linkHardwareToHouse(house);
        return houseRepository.save(house);
    }

    @Override
    public House updateHouse(Long id, House updatedHouse) {
        House existing = findHouseById(id);
        if (updatedHouse.getName() != null) {
            existing.setName(updatedHouse.getName());
        }
        if (updatedHouse.getAddress() != null) {
            existing.setAddress(updatedHouse.getAddress());
        }
        if (updatedHouse.getLatitude() != null) {
            existing.setLatitude(updatedHouse.getLatitude());
        }
        if (updatedHouse.getLongitude() != null) {
            existing.setLongitude(updatedHouse.getLongitude());
        }
        if (updatedHouse.getCurrentConsumptionKw() != null) {
            existing.setCurrentConsumptionKw(updatedHouse.getCurrentConsumptionKw());
        }
        if (updatedHouse.getUser() != null && updatedHouse.getUser().getId() != null) {
            User owner = userRepository.findById(updatedHouse.getUser().getId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "User not found with id: " + updatedHouse.getUser().getId()));
            existing.setUser(owner);
        }
        return houseRepository.save(existing);
    }

    @Override
    public void deleteHouse(Long id) {
        House existing = findHouseById(id);
        houseRepository.delete(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Battery> findBatteriesByHouseId(Long houseId) {
        findHouseById(houseId);
        return batteryRepository.findByHouseId(houseId);
    }

    @Override
    public Battery addBatteryToHouse(Long houseId, Battery battery) {
        House house = findHouseById(houseId);
        house.addBattery(battery);
        return batteryRepository.save(battery);
    }

    @Override
    public Battery updateBattery(Long batteryId, Battery updatedBattery) {
        Battery existing = batteryRepository.findById(batteryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Battery not found with id: " + batteryId));
        if (updatedBattery.getModel() != null) {
            existing.setModel(updatedBattery.getModel());
        }
        if (updatedBattery.getCapacityKwh() != null) {
            existing.setCapacityKwh(updatedBattery.getCapacityKwh());
        }
        if (updatedBattery.getCurrentChargePercentage() != null) {
            existing.setCurrentChargePercentage(updatedBattery.getCurrentChargePercentage());
        }
        if (updatedBattery.getMaxPowerKw() != null) {
            existing.setMaxPowerKw(updatedBattery.getMaxPowerKw());
        }
        if (updatedBattery.getStatus() != null) {
            existing.setStatus(updatedBattery.getStatus());
        }
        return batteryRepository.save(existing);
    }

    @Override
    public void deleteBattery(Long batteryId) {
        Battery existing = batteryRepository.findById(batteryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Battery not found with id: " + batteryId));
        batteryRepository.delete(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolarPanel> findSolarPanelsByHouseId(Long houseId) {
        findHouseById(houseId);
        return solarPanelRepository.findByHouseId(houseId);
    }

    @Override
    public SolarPanel addSolarPanelToHouse(Long houseId, SolarPanel solarPanel) {
        House house = findHouseById(houseId);
        house.addSolarPanel(solarPanel);
        return solarPanelRepository.save(solarPanel);
    }

    @Override
    public SolarPanel updateSolarPanel(Long panelId, SolarPanel updatedPanel) {
        SolarPanel existing = solarPanelRepository.findById(panelId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Solar panel not found with id: " + panelId));
        if (updatedPanel.getModel() != null) {
            existing.setModel(updatedPanel.getModel());
        }
        if (updatedPanel.getMaxCapacityKw() != null) {
            existing.setMaxCapacityKw(updatedPanel.getMaxCapacityKw());
        }
        if (updatedPanel.getEfficiency() != null) {
            existing.setEfficiency(updatedPanel.getEfficiency());
        }
        if (updatedPanel.getOrientation() != null) {
            existing.setOrientation(updatedPanel.getOrientation());
        }
        return solarPanelRepository.save(existing);
    }

    @Override
    public void deleteSolarPanel(Long panelId) {
        SolarPanel existing = solarPanelRepository.findById(panelId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Solar panel not found with id: " + panelId));
        solarPanelRepository.delete(existing);
    }

    private void linkHardwareToHouse(House house) {
        if (house.getBatteries() != null) {
            for (Battery battery : house.getBatteries()) {
                battery.setHouse(house);
            }
        }
        if (house.getSolarPanels() != null) {
            for (SolarPanel panel : house.getSolarPanels()) {
                panel.setHouse(house);
            }
        }
    }
}

package EnergyCommunity.core.controller;

import EnergyCommunity.core.model.Battery;
import EnergyCommunity.core.model.House;
import EnergyCommunity.core.model.SolarPanel;
import EnergyCommunity.core.service.HouseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/houses")
public class HouseController implements HouseAPI {

    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<House>> getAllHouses() {
        return ResponseEntity.ok(houseService.findAllHouses());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<House> getHouseById(@PathVariable Long id) {
        return ResponseEntity.ok(houseService.findHouseById(id));
    }

    @Override
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<House>> getHousesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(houseService.findHousesByUserId(userId));
    }

    @Override
    @PostMapping
    public ResponseEntity<House> createHouse(@Valid @RequestBody House house) {
        House created = houseService.saveHouse(house);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Override
    @PostMapping("/user/{userId}")
    public ResponseEntity<House> createHouseForUser(@PathVariable Long userId, @Valid @RequestBody House house) {
        House created = houseService.saveHouseForUser(userId, house);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<House> updateHouse(@PathVariable Long id, @RequestBody House house) {
        return ResponseEntity.ok(houseService.updateHouse(id, house));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHouse(@PathVariable Long id) {
        houseService.deleteHouse(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{houseId}/batteries")
    public ResponseEntity<List<Battery>> getBatteriesByHouseId(@PathVariable Long houseId) {
        return ResponseEntity.ok(houseService.findBatteriesByHouseId(houseId));
    }

    @Override
    @PostMapping("/{houseId}/batteries")
    public ResponseEntity<Battery> addBatteryToHouse(@PathVariable Long houseId, @Valid @RequestBody Battery battery) {
        Battery created = houseService.addBatteryToHouse(houseId, battery);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Override
    @PutMapping("/batteries/{batteryId}")
    public ResponseEntity<Battery> updateBattery(@PathVariable Long batteryId, @RequestBody Battery battery) {
        return ResponseEntity.ok(houseService.updateBattery(batteryId, battery));
    }

    @Override
    @DeleteMapping("/batteries/{batteryId}")
    public ResponseEntity<Void> deleteBattery(@PathVariable Long batteryId) {
        houseService.deleteBattery(batteryId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{houseId}/solar-panels")
    public ResponseEntity<List<SolarPanel>> getSolarPanelsByHouseId(@PathVariable Long houseId) {
        return ResponseEntity.ok(houseService.findSolarPanelsByHouseId(houseId));
    }

    @Override
    @PostMapping("/{houseId}/solar-panels")
    public ResponseEntity<SolarPanel> addSolarPanelToHouse(@PathVariable Long houseId, @Valid @RequestBody SolarPanel solarPanel) {
        SolarPanel created = houseService.addSolarPanelToHouse(houseId, solarPanel);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Override
    @PutMapping("/solar-panels/{panelId}")
    public ResponseEntity<SolarPanel> updateSolarPanel(@PathVariable Long panelId, @RequestBody SolarPanel solarPanel) {
        return ResponseEntity.ok(houseService.updateSolarPanel(panelId, solarPanel));
    }

    @Override
    @DeleteMapping("/solar-panels/{panelId}")
    public ResponseEntity<Void> deleteSolarPanel(@PathVariable Long panelId) {
        houseService.deleteSolarPanel(panelId);
        return ResponseEntity.noContent().build();
    }
}

package EnergyCommunity.core.controller;

import EnergyCommunity.core.model.Battery;
import EnergyCommunity.core.model.House;
import EnergyCommunity.core.model.SolarPanel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "House & Hardware API", description = "Endpoints for managing houses, batteries, and solar panels in the energy community")
@RequestMapping("/api/v1/houses")
public interface HouseAPI {

    @Operation(summary = "Get all houses", description = "Retrieves a list of all houses and their hardware configuration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of houses")
    })
    @GetMapping
    ResponseEntity<List<House>> getAllHouses();

    @Operation(summary = "Get house by ID", description = "Retrieves a specific house along with its batteries and solar panels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "House found"),
            @ApiResponse(responseCode = "404", description = "House not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<House> getHouseById(
            @Parameter(description = "ID of the house", required = true)
            @PathVariable Long id);

    @Operation(summary = "Get houses by User ID", description = "Retrieves all houses owned by a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user's houses"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{userId}")
    ResponseEntity<List<House>> getHousesByUserId(
            @Parameter(description = "ID of the owner user", required = true)
            @PathVariable Long userId);

    @Operation(summary = "Create a new house", description = "Registers a new house (and optional initial batteries/solar panels) in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "House successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Referenced owner user not found")
    })
    @PostMapping
    ResponseEntity<House> createHouse(@Valid @RequestBody House house);

    @Operation(summary = "Create a new house for a specific user", description = "Registers a new house and assigns it to the given user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "House successfully created for user"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/user/{userId}")
    ResponseEntity<House> createHouseForUser(
            @Parameter(description = "ID of the owner user", required = true)
            @PathVariable Long userId,
            @Valid @RequestBody House house);

    @Operation(summary = "Update an existing house", description = "Updates house details such as name, address, coordinates, or current consumption")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "House successfully updated"),
            @ApiResponse(responseCode = "404", description = "House not found")
    })
    @PutMapping("/{id}")
    ResponseEntity<House> updateHouse(
            @Parameter(description = "ID of the house to update", required = true)
            @PathVariable Long id,
            @RequestBody House house);

    @Operation(summary = "Delete a house", description = "Deletes a house and its associated batteries and solar panels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "House successfully deleted"),
            @ApiResponse(responseCode = "404", description = "House not found")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteHouse(
            @Parameter(description = "ID of the house to delete", required = true)
            @PathVariable Long id);

    // --- Battery Endpoints ---

    @Operation(summary = "Get batteries for a house", description = "Retrieves all batteries installed in a specific house")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved batteries"),
            @ApiResponse(responseCode = "404", description = "House not found")
    })
    @GetMapping("/{houseId}/batteries")
    ResponseEntity<List<Battery>> getBatteriesByHouseId(
            @Parameter(description = "ID of the house", required = true)
            @PathVariable Long houseId);

    @Operation(summary = "Add a battery to a house", description = "Installs and registers a new battery unit for a specific house")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Battery successfully added"),
            @ApiResponse(responseCode = "400", description = "Invalid battery configuration"),
            @ApiResponse(responseCode = "404", description = "House not found")
    })
    @PostMapping("/{houseId}/batteries")
    ResponseEntity<Battery> addBatteryToHouse(
            @Parameter(description = "ID of the house", required = true)
            @PathVariable Long houseId,
            @Valid @RequestBody Battery battery);

    @Operation(summary = "Update a battery", description = "Updates battery parameters (capacity, charge percentage, status, model)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Battery successfully updated"),
            @ApiResponse(responseCode = "404", description = "Battery not found")
    })
    @PutMapping("/batteries/{batteryId}")
    ResponseEntity<Battery> updateBattery(
            @Parameter(description = "ID of the battery", required = true)
            @PathVariable Long batteryId,
            @RequestBody Battery battery);

    @Operation(summary = "Delete a battery", description = "Removes a battery unit from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Battery successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Battery not found")
    })
    @DeleteMapping("/batteries/{batteryId}")
    ResponseEntity<Void> deleteBattery(
            @Parameter(description = "ID of the battery to delete", required = true)
            @PathVariable Long batteryId);

    // --- Solar Panel Endpoints ---

    @Operation(summary = "Get solar panels for a house", description = "Retrieves all solar panels installed in a specific house")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved solar panels"),
            @ApiResponse(responseCode = "404", description = "House not found")
    })
    @GetMapping("/{houseId}/solar-panels")
    ResponseEntity<List<SolarPanel>> getSolarPanelsByHouseId(
            @Parameter(description = "ID of the house", required = true)
            @PathVariable Long houseId);

    @Operation(summary = "Add a solar panel to a house", description = "Installs and registers a new solar panel array for a specific house")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Solar panel successfully added"),
            @ApiResponse(responseCode = "400", description = "Invalid solar panel configuration"),
            @ApiResponse(responseCode = "404", description = "House not found")
    })
    @PostMapping("/{houseId}/solar-panels")
    ResponseEntity<SolarPanel> addSolarPanelToHouse(
            @Parameter(description = "ID of the house", required = true)
            @PathVariable Long houseId,
            @Valid @RequestBody SolarPanel solarPanel);

    @Operation(summary = "Update a solar panel", description = "Updates solar panel parameters (maxCapacityKw, efficiency, orientation, model)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solar panel successfully updated"),
            @ApiResponse(responseCode = "404", description = "Solar panel not found")
    })
    @PutMapping("/solar-panels/{panelId}")
    ResponseEntity<SolarPanel> updateSolarPanel(
            @Parameter(description = "ID of the solar panel", required = true)
            @PathVariable Long panelId,
            @RequestBody SolarPanel solarPanel);

    @Operation(summary = "Delete a solar panel", description = "Removes a solar panel unit from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Solar panel successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Solar panel not found")
    })
    @DeleteMapping("/solar-panels/{panelId}")
    ResponseEntity<Void> deleteSolarPanel(
            @Parameter(description = "ID of the solar panel to delete", required = true)
            @PathVariable Long panelId);
}

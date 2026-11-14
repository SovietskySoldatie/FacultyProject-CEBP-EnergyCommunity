package EnergyCommunity.core.telemetry.simulation;

import EnergyCommunity.core.telemetry.model.SimulationStatusResponse;

import java.time.Instant;

/**
 * Service contract for the sensor data stream simulation engine.
 * <p>
 * Simulates real-time rooftop solar generation (based on sun position, weather, and panel specs),
 * household electrical loads (morning/evening peaks), battery state-of-charge changes,
 * and dynamic electricity market price ticks, publishing all generated events
 * directly to the message broker (Kafka / RabbitMQ).
 */
public interface TelemetrySimulationService {

    /**
     * Starts the automated background simulation loop.
     *
     * @param tickIntervalSeconds Frequency of telemetry event generation ticks in seconds
     */
    void startSimulation(int tickIntervalSeconds);

    /**
     * Stops the automated background simulation loop.
     */
    void stopSimulation();

    /**
     * Checks if the telemetry simulator is currently running.
     *
     * @return True if active
     */
    boolean isRunning();

    /**
     * Returns current operational status and parameters of the simulator.
     *
     * @return Simulation status descriptor
     */
    SimulationStatusResponse getStatus();

    /**
     * Manually triggers a single simulation tick across all houses.
     * Useful for on-demand demonstrations and automated integration tests.
     */
    void triggerSimulationTick();

    /**
     * Computes the simulated instantaneous solar power output in kW for a given house.
     *
     * @param houseId Identifier of the house
     * @param atTime  Target timestamp
     * @return Simulated solar generation in kW
     */
    double calculateSimulatedSolarOutput(Long houseId, Instant atTime);

    /**
     * Computes the simulated instantaneous power demand in kW for a given house.
     *
     * @param houseId Identifier of the house
     * @param atTime  Target timestamp
     * @return Simulated household load in kW
     */
    double calculateSimulatedConsumption(Long houseId, Instant atTime);
}

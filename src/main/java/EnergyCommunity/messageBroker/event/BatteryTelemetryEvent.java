package EnergyCommunity.messageBroker.event;

import EnergyCommunity.core.model.BatteryStatus;
import java.time.Instant;

/**
 * Event representing real-time battery storage status and telemetry.
 *
 * @param houseId                 Identifier of the house where the battery is installed
 * @param batteryId               Unique identifier of the battery unit
 * @param currentChargePercentage Current state of charge in percentage (0 - 100)
 * @param powerKw                 Instantaneous charge (> 0) or discharge (< 0) rate in kW
 * @param status                  Operational status of the battery
 * @param timestamp               Timestamp of the measurement
 */
public record BatteryTelemetryEvent(
        Long houseId,
        Long batteryId,
        double currentChargePercentage,
        double powerKw,
        BatteryStatus status,
        Instant timestamp
) {
}

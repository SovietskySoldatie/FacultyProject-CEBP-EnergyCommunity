package EnergyCommunity.messageBroker.event;

import java.time.Instant;

/**
 * Event representing an instantaneous power consumption reading from a smart meter.
 * Published to the message broker and consumed by Telemetry &amp; Optimization Service.
 *
 * @param houseId               Identifier of the house
 * @param currentConsumptionKw  Instantaneous household power demand in kW
 * @param timestamp             Timestamp of the measurement
 */
public record MeterReadingEvent(
        Long houseId,
        double currentConsumptionKw,
        Instant timestamp
) {
}

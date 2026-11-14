package EnergyCommunity.messageBroker.event;

import java.time.Instant;

/**
 * Event representing an instantaneous solar power generation reading from a rooftop inverter.
 *
 * @param houseId             Identifier of the house
 * @param solarProductionKw   Instantaneous power output of the solar panel array in kW
 * @param timestamp           Timestamp of the measurement
 */
public record SolarGenerationEvent(
        Long houseId,
        double solarProductionKw,
        Instant timestamp
) {
}

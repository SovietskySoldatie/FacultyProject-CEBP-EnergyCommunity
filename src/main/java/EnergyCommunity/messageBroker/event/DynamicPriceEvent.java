package EnergyCommunity.messageBroker.event;

import java.time.Instant;

/**
 * Event representing real-time or updated dynamic market tariff fluctuations.
 *
 * @param priceEurPerKwh Dynamic market energy price in EUR/kWh
 * @param timestamp      Effective timestamp of the tariff tick
 */
public record DynamicPriceEvent(
        double priceEurPerKwh,
        Instant timestamp
) {
}

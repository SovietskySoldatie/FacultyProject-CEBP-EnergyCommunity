package EnergyCommunity.messageBroker.producer;

import EnergyCommunity.messageBroker.event.BatteryTelemetryEvent;
import EnergyCommunity.messageBroker.event.DynamicPriceEvent;
import EnergyCommunity.messageBroker.event.EvProximityEvent;
import EnergyCommunity.messageBroker.event.MeterReadingEvent;
import EnergyCommunity.messageBroker.event.SolarGenerationEvent;

/**
 * Message broker producer interface for publishing high-frequency telemetry events.
 * Decouples simulated sensors, smart meters, and mobile geolocation events
 * from downstream consumers using Kafka or RabbitMQ.
 */
public interface TelemetryEventPublisher {

    /**
     * Publishes an instantaneous smart-meter power consumption reading.
     *
     * @param event The meter reading event
     */
    void publishMeterReading(MeterReadingEvent event);

    /**
     * Publishes an instantaneous solar power generation reading.
     *
     * @param event The solar generation event
     */
    void publishSolarGeneration(SolarGenerationEvent event);

    /**
     * Publishes an instantaneous battery state-of-charge and power telemetry event.
     *
     * @param event The battery telemetry event
     */
    void publishBatteryTelemetry(BatteryTelemetryEvent event);

    /**
     * Publishes a dynamic market tariff tick event.
     *
     * @param event The dynamic price event
     */
    void publishDynamicPrice(DynamicPriceEvent event);

    /**
     * Publishes an EV proximity transition event received from the mobile client.
     *
     * @param event The EV proximity event
     */
    void publishEvProximity(EvProximityEvent event);
}

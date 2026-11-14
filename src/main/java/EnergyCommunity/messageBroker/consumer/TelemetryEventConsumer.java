package EnergyCommunity.messageBroker.consumer;

import EnergyCommunity.messageBroker.event.BatteryTelemetryEvent;
import EnergyCommunity.messageBroker.event.DynamicPriceEvent;
import EnergyCommunity.messageBroker.event.EvProximityEvent;
import EnergyCommunity.messageBroker.event.MeterReadingEvent;
import EnergyCommunity.messageBroker.event.ProposalAcceptedEvent;
import EnergyCommunity.messageBroker.event.SolarGenerationEvent;

/**
 * Message broker consumer interface for receiving high-frequency telemetry events.
 * Consumed by {@code Telemetry & Optimization Service} to update fast read models
 * without writing to the primary relational database.
 */
public interface TelemetryEventConsumer {

    /**
     * Handles incoming smart-meter power consumption readings.
     *
     * @param event The meter reading event
     */
    void onMeterReading(MeterReadingEvent event);

    /**
     * Handles incoming solar generation output readings.
     *
     * @param event The solar generation event
     */
    void onSolarGeneration(SolarGenerationEvent event);

    /**
     * Handles incoming battery telemetry updates.
     *
     * @param event The battery telemetry event
     */
    void onBatteryTelemetry(BatteryTelemetryEvent event);

    /**
     * Handles incoming dynamic electricity tariff updates.
     *
     * @param event The dynamic price event
     */
    void onDynamicPrice(DynamicPriceEvent event);

    /**
     * Handles incoming mobile EV proximity events.
     *
     * @param event The EV proximity event
     */
    void onEvProximity(EvProximityEvent event);

    /**
     * Handles incoming proposal acceptance events to register approved battery schedules.
     *
     * @param event The proposal acceptance event
     */
    void onProposalAccepted(ProposalAcceptedEvent event);
}

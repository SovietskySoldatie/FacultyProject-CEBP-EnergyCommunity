package EnergyCommunity.core.telemetry.provider;

import EnergyCommunity.core.telemetry.model.ForecastPoint;
import java.time.Instant;
import java.util.List;

/**
 * Provider interface for acquiring dynamic electricity market tariffs.
 * Decouples day-ahead spot market price simulation or external exchange APIs from the forecast service.
 */
public interface EnergyTariffProvider {

    /**
     * Retrieves predicted dynamic electricity prices in EUR/kWh over a 24-hour horizon.
     *
     * @param fromTime     Start timestamp
     * @param horizonHours Number of hours ahead to forecast (typically 24)
     * @return Chronological list of hourly dynamic tariff forecast points
     */
    List<ForecastPoint> getTariffForecast(Instant fromTime, int horizonHours);

    /**
     * Retrieves the current dynamic market spot price in EUR/kWh.
     *
     * @return Current electricity tariff in EUR/kWh
     */
    double getCurrentTariff();
}

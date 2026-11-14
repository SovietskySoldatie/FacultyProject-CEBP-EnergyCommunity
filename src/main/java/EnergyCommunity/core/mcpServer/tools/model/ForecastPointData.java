package EnergyCommunity.core.mcpServer.tools.model;

import java.time.Instant;

/**
 * Hourly forecast data point combining solar irradiance and dynamic energy market tariff.
 * Aligned with the Mobile Client's {@code ForecastPoint} model.
 *
 * @param time              Forecast timestamp (hourly slot)
 * @param solarRadiationWm2 Predicted solar irradiance in W/m^2
 * @param pricePerKwh       Predicted dynamic grid price in EUR/kWh
 */
public record ForecastPointData(
        Instant time,
        double solarRadiationWm2,
        double pricePerKwh
) {
}

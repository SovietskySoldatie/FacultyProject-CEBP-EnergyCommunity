package EnergyCommunity.core.telemetry.provider;

import EnergyCommunity.core.telemetry.model.ForecastPoint;
import java.time.Instant;
import java.util.List;

/**
 * Provider interface for acquiring 24-hour solar radiation (irradiance) forecasts.
 * Decouples mathematical solar simulation models or external weather APIs from the forecast service.
 */
public interface SolarRadiationProvider {

    /**
     * Retrieves predicted solar irradiance in W/m^2 for the given geographical coordinates
     * over a 24-hour horizon starting from the specified timestamp.
     *
     * @param latitude     Geographical latitude
     * @param longitude    Geographical longitude
     * @param fromTime     Start timestamp
     * @param horizonHours Number of hours ahead to forecast (typically 24)
     * @return Chronological list of hourly solar forecast points
     */
    List<ForecastPoint> getSolarRadiationForecast(Double latitude, Double longitude, Instant fromTime, int horizonHours);

    /**
     * Computes or fetches instantaneous solar irradiance in W/m^2 for coordinates at a specific timestamp.
     *
     * @param latitude  Geographical latitude
     * @param longitude Geographical longitude
     * @param atTime    Target timestamp
     * @return Instantaneous irradiance in W/m^2
     */
    double getInstantaneousRadiation(Double latitude, Double longitude, Instant atTime);
}

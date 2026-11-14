package EnergyCommunity.core.telemetry.service;

import EnergyCommunity.core.telemetry.model.ForecastResponse;

/**
 * Service contract for computing, retrieving, and caching 24-hour solar radiation
 * ($\text{W/m}^2$) and dynamic electricity price ($\text{EUR/kWh}$) forecasts.
 */
public interface ForecastService {

    /**
     * Retrieves the 24-hour solar radiation and dynamic energy tariff forecast for the given coordinates.
     *
     * @param latitude  Latitude in decimal degrees
     * @param longitude Longitude in decimal degrees
     * @return 24-hour forecast data
     */
    ForecastResponse get24HourForecast(Double latitude, Double longitude);

    /**
     * Retrieves the 24-hour forecast for the location of the specified house.
     *
     * @param houseId Identifier of the house
     * @return 24-hour forecast data for the house's location
     */
    ForecastResponse get24HourForecastForHouse(Long houseId);
}

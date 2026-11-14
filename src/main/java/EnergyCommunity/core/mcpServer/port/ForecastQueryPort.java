package EnergyCommunity.core.mcpServer.port;

import EnergyCommunity.core.mcpServer.tools.model.ForecastReadResult;
import EnergyCommunity.core.mcpServer.tools.model.GeoLocationInput;

/**
 * Outbound port used by the MCP Server to retrieve 24-hour solar radiation and dynamic
 * electricity price forecasts from the Telemetry &amp; Optimization Service.
 */
public interface ForecastQueryPort {

    /**
     * Fetches the 24-hour solar irradiance and dynamic energy price forecast for a location.
     *
     * @param location Geographical coordinates of the target household
     * @return 24-hour forecast read result
     */
    ForecastReadResult fetchForecastForLocation(GeoLocationInput location);
}

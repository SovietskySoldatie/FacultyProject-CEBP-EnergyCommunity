package EnergyCommunity.core.mcpServer.tools;

import EnergyCommunity.core.mcpServer.protocol.McpTool;
import EnergyCommunity.core.mcpServer.tools.model.ForecastReadResult;
import EnergyCommunity.core.mcpServer.tools.model.GeoLocationInput;

/**
 * Contract for <b>MCP Tool 2 (Read)</b>: {@code get_forecasts(location)}.
 * <p>
 * Extracts the solar radiation forecast and dynamic electricity prices
 * for the next 24 hours at the specified geographical location.
 */
public interface GetForecastsToolContract extends McpTool {

    String TOOL_NAME = "get_forecasts";

    /**
     * Strongly-typed invocation of the {@code get_forecasts(location)} MCP tool.
     *
     * @param location Geographical coordinates / zone of the household
     * @return 24-hour solar radiation and dynamic tariff forecast
     */
    ForecastReadResult getForecasts(GeoLocationInput location);
}

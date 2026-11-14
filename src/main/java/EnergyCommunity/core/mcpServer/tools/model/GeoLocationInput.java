package EnergyCommunity.core.mcpServer.tools.model;

/**
 * Geographical coordinate or named region used as input to the {@code get_forecasts(location)} MCP tool.
 *
 * @param latitude   Latitude in decimal degrees
 * @param longitude  Longitude in decimal degrees
 * @param regionName Optional human-readable location or grid zone name (e.g. "Timisoara-West")
 */
public record GeoLocationInput(
        double latitude,
        double longitude,
        String regionName
) {
}

package EnergyCommunity.core.mcpServer.protocol;

import java.util.Map;

/**
 * Represents an incoming MCP {@code tools/call} request from an AI agent or MCP client.
 *
 * @param requestId Unique JSON-RPC request correlation identifier
 * @param toolName  Name of the tool to invoke (e.g. "get_house_status", "get_forecasts", "propose_battery_schedule")
 * @param arguments Key-value map of arguments passed to the tool
 */
public record McpToolCallRequest(
        String requestId,
        String toolName,
        Map<String, Object> arguments
) {
}

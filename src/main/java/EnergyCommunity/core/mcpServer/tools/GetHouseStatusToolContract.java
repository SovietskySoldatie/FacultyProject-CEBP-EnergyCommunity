package EnergyCommunity.core.mcpServer.tools;

import EnergyCommunity.core.mcpServer.protocol.McpTool;
import EnergyCommunity.core.mcpServer.tools.model.HouseStatusReadResult;

/**
 * Contract for <b>MCP Tool 1 (Read)</b>: {@code get_house_status(user_id)}.
 * <p>
 * Extracts the current battery state of charge, household power consumption,
 * solar generation, and hardware capacity for a given user's home.
 */
public interface GetHouseStatusToolContract extends McpTool {

    String TOOL_NAME = "get_house_status";

    /**
     * Strongly-typed invocation of the {@code get_house_status(user_id)} MCP tool.
     *
     * @param userId Unique identifier of the homeowner
     * @return Current house telemetry and battery status snapshot
     */
    HouseStatusReadResult getHouseStatus(String userId);
}

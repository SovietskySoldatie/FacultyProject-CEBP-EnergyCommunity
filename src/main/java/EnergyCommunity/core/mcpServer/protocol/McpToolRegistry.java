package EnergyCommunity.core.mcpServer.protocol;

import java.util.List;
import java.util.Optional;

/**
 * Registry contract for managing the lifecycle and lookup of MCP tools exposed by the server.
 */
public interface McpToolRegistry {

    /**
     * Registers a tool so it becomes discoverable via {@code tools/list} and callable via {@code tools/call}.
     *
     * @param tool Tool implementation to register
     */
    void registerTool(McpTool tool);

    /**
     * Removes a tool by name from the active registry.
     *
     * @param toolName Name of the tool to remove
     * @return True if the tool was registered and removed
     */
    boolean unregisterTool(String toolName);

    /**
     * Looks up a registered tool by its exact name.
     *
     * @param toolName Name of the tool
     * @return Optional containing the tool if found
     */
    Optional<McpTool> getTool(String toolName);

    /**
     * Returns all currently registered MCP tools.
     *
     * @return Immutable list of registered tools
     */
    List<McpTool> getAllTools();
}

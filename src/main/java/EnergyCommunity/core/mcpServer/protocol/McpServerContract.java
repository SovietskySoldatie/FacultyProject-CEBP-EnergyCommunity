package EnergyCommunity.core.mcpServer.protocol;

import java.util.List;

/**
 * Primary contract for the remotely accessible Model Context Protocol (MCP) Server.
 * <p>
 * Exposes standard MCP operations ({@code initialize}, {@code tools/list}, {@code tools/call})
 * over remote transport (HTTP / SSE) so that internal and external AI agents can inspect
 * energy community data and propose battery optimization schedules.
 */
public interface McpServerContract {

    /**
     * Starts the remote MCP server transport listener.
     */
    void start();

    /**
     * Gracefully stops the MCP server and releases network resources.
     */
    void stop();

    /**
     * Indicates whether the MCP server is actively accepting remote connections.
     *
     * @return True if running
     */
    boolean isRunning();

    /**
     * Returns server metadata and protocol capabilities.
     *
     * @return Server info descriptor
     */
    McpServerInfo getServerInfo();

    /**
     * Handles the MCP {@code tools/list} operation, returning all tools available for agentic interaction.
     * Must expose at least 3 tools (minimum 1 read tool and 1 change-proposal tool).
     *
     * @return List of registered MCP tools
     */
    List<McpTool> listTools();

    /**
     * Handles the MCP {@code tools/call} operation, dispatching the request to the matching tool.
     *
     * @param request Tool invocation request containing tool name and arguments
     * @return Standardized tool execution result
     */
    McpToolResult callTool(McpToolCallRequest request);
}

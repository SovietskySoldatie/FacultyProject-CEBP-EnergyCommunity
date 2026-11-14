package EnergyCommunity.core.mcpServer.protocol;

import java.util.Map;

/**
 * Base contract for any tool exposed by the Model Context Protocol (MCP) server.
 * Each tool provides self-describing metadata (name, description, input schema)
 * and a deterministic execution entry point.
 */
public interface McpTool {

    /**
     * Unique MCP tool identifier exposed to the LLM (e.g. {@code "get_house_status"}).
     *
     * @return Tool name in snake_case
     */
    String getName();

    /**
     * Natural-language description explaining when and how the AI agent should invoke this tool.
     *
     * @return Human/LLM-readable description
     */
    String getDescription();

    /**
     * JSON Schema definition describing the input arguments accepted by this tool.
     *
     * @return Input parameter schema
     */
    McpToolSchema getInputSchema();

    /**
     * Executes the tool with the supplied arguments map and returns a structured MCP result.
     *
     * @param arguments Input arguments parsed from the MCP {@code tools/call} request
     * @return Execution outcome wrapped in {@link McpToolResult}
     */
    McpToolResult execute(Map<String, Object> arguments);
}

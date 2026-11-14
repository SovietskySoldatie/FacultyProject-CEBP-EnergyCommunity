package EnergyCommunity.core.mcpServer.protocol;

/**
 * Standardized result returned by an MCP tool invocation.
 *
 * @param requestId         Correlation identifier matching the {@link McpToolCallRequest}
 * @param toolName          Name of the executed tool
 * @param isError           True if the tool execution failed or validation rejected the input
 * @param contentJson       Serialized JSON payload returned to the LLM context
 * @param structuredPayload Optional typed domain object produced by the tool
 */
public record McpToolResult(
        String requestId,
        String toolName,
        boolean isError,
        String contentJson,
        Object structuredPayload
) {
    public static McpToolResult success(String requestId, String toolName, String contentJson, Object payload) {
        return new McpToolResult(requestId, toolName, false, contentJson, payload);
    }

    public static McpToolResult error(String requestId, String toolName, String errorMessage) {
        return new McpToolResult(requestId, toolName, true, errorMessage, null);
    }
}

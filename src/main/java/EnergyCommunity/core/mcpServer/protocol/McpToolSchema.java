package EnergyCommunity.core.mcpServer.protocol;

import java.util.List;
import java.util.Map;

/**
 * JSON-Schema-compatible descriptor for an MCP tool's input parameters.
 * Allows LLMs and MCP clients to inspect parameter names, types, descriptions,
 * and required fields before invoking a tool.
 *
 * @param type       Root schema type (typically "object")
 * @param properties Map of parameter names to their schema metadata (type, description, etc.)
 * @param required   List of mandatory parameter names
 */
public record McpToolSchema(
        String type,
        Map<String, Map<String, Object>> properties,
        List<String> required
) {
}

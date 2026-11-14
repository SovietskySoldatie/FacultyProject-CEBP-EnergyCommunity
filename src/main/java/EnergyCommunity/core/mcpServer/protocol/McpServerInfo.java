package EnergyCommunity.core.mcpServer.protocol;

/**
 * Metadata describing the remotely exposed Model Context Protocol (MCP) server.
 *
 * @param name            Server identifier (e.g. "energy-community-mcp-server")
 * @param version         Semantic version of the MCP server implementation
 * @param protocolVersion Supported MCP specification version (e.g. "2025-03-26")
 * @param endpointUrl     Remote HTTP/SSE endpoint URL where agents connect
 */
public record McpServerInfo(
        String name,
        String version,
        String protocolVersion,
        String endpointUrl
) {
}

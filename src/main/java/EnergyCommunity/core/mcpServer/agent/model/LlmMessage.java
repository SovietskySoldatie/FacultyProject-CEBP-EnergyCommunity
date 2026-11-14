package EnergyCommunity.core.mcpServer.agent.model;

/**
 * Represents a single conversational or tool-result message exchanged with the LLM during
 * an agentic reasoning loop.
 *
 * @param role       Message role ("system", "user", "assistant", or "tool")
 * @param content    Textual content or serialized JSON output from a tool call
 * @param toolCallId Optional correlation ID when role is "tool"
 */
public record LlmMessage(
        String role,
        String content,
        String toolCallId
) {
    public static LlmMessage system(String content) {
        return new LlmMessage("system", content, null);
    }

    public static LlmMessage user(String content) {
        return new LlmMessage("user", content, null);
    }

    public static LlmMessage assistant(String content) {
        return new LlmMessage("assistant", content, null);
    }

    public static LlmMessage toolResult(String toolCallId, String contentJson) {
        return new LlmMessage("tool", contentJson, toolCallId);
    }
}

package EnergyCommunity.core.mcpServer.agent;

import EnergyCommunity.core.mcpServer.agent.model.LlmMessage;
import EnergyCommunity.core.mcpServer.agent.model.LlmResponse;
import EnergyCommunity.core.mcpServer.protocol.McpTool;
import java.util.List;

/**
 * Abstraction over the external Large Language Model (LLM) service (e.g. Google Gemini Free Tier,
 * Groq, or OpenAI-compatible endpoint).
 * <p>
 * Decouples the agent orchestration logic from any specific AI SDK or HTTP provider.
 */
public interface LlmClientContract {

    /**
     * Sends the current conversation history and the available MCP tool definitions to the LLM,
     * returning either the next requested tool calls or the final reasoning response.
     *
     * @param messages       Conversation history (system prompt, user goal, prior tool calls &amp; results)
     * @param availableTools MCP tools exposed by the server that the LLM is allowed to call
     * @return LLM response containing requested tool calls or final completion text
     */
    LlmResponse generateWithTools(List<LlmMessage> messages, List<McpTool> availableTools);
}

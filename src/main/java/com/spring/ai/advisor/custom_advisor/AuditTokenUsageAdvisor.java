package com.spring.ai.advisor.custom_advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;

public class AuditTokenUsageAdvisor implements CallAdvisor {

    Logger logger = LoggerFactory.getLogger(AuditTokenUsageAdvisor.class);

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

        // Call the next advisor / LLM
        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);
        ChatResponse chatResponse = chatClientResponse.chatResponse();

        // Audit token usage here
        Usage usage = chatResponse.getMetadata().getUsage();
        if (null != usage) {

            // Extract (i/p token, o/p token, total token)
            int inputTokens = usage.getPromptTokens();
            int outputTokens = usage.getCompletionTokens();
            int totalTokens = usage.getTotalTokens();

            // Log the details
            logger.info("Token usage - input tokens: {}, output tokens: {}, total tokens: {}", inputTokens, outputTokens, totalTokens);
        }
        return chatClientResponse;
    }

    @Override
    public String getName() {
        return "AuditTokenUsageAdvisor";
    }

    @Override
    public int getOrder() {
        return 1;
    }
}

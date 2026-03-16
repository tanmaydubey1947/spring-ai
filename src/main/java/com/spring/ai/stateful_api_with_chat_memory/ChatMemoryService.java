package com.spring.ai.stateful_api_with_chat_memory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

import java.util.List;

@Service
public class ChatMemoryService {

    private final ChatClient chatClient;

    public ChatMemoryService(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {

        Advisor loggerAdvisor = new SimpleLoggerAdvisor();

        Advisor memoryAdvisor = MessageChatMemoryAdvisor
                .builder(chatMemory)
                .build();

        this.chatClient = chatClientBuilder
                .defaultAdvisors(List.of(memoryAdvisor, loggerAdvisor))
                .build();
    }

    public String talkWithMemory(String userMessage) {
        return chatClient.prompt()
                .user(userMessage)
                .call()
                .content();
    }

    public String talkWithMemoryHavingKey(String userMessage, String username) {
        return chatClient.prompt()
                .user(userMessage)
                .advisors(adviceSpec -> adviceSpec.param(CONVERSATION_ID, username))
                .call()
                .content();
    }


}

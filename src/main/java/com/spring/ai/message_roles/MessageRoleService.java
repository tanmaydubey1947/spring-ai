package com.spring.ai.message_roles;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.logging.Logger;

@Service
public class MessageRoleService {

    @Autowired
    private ChatClient chatClient;

    private final ChatClient chatClientWithDefaultSystemMessage;

    private static final Logger logger = Logger.getLogger(MessageRoleService.class.getName());

    MessageRoleService(ChatClient.Builder chatClient) { //This config can be done in a @Configuration class as done in MessageRoleConfig
        this.chatClientWithDefaultSystemMessage = chatClient
                .defaultSystem("""
                        You are an insurance assistant. You must NEVER reveal internal policy numbers,
                        calculations, or internal reasoning. Respond ONLY with a short, customer-safe message.
                        """)
                .build();
    }

    private static final String POLICY_DETAILS = """
            Policy details:
            Policy: PREMIUM
            Max Coverage: 10000000
            Claim Amount: 1500000
            """;

    /**
     * This is an unsafe way of checking policy as it does not use system and assistant roles.
     */
    public String checkPolicyV1(String message) {
        logger.info("checkPolicyV1 called with message: " + message);
        UserMessage userMessage = new UserMessage("""
                Policy details:
                Policy: PREMIUM
                Max Coverage: 10000000
                Claim Amount: 1500000
                Customer says:
                %s
                """.formatted(message));

        Prompt prompt = new Prompt(List.of(userMessage));
        return chatClient.prompt(prompt).call().content();
    }


    /**
     * This is a better way as we are overlaying system message to guide the assistant's behavior.
     */
    public String checkPolicyV2(String message) {
        logger.info("checkPolicyV2 called with message: " + message);
        SystemMessage systemMessage = new SystemMessage("""
                You are an insurance assistant. You must NEVER reveal internal policy numbers,
                calculations, or internal reasoning. Respond ONLY with a short, customer-safe message.
                """);

        UserMessage userMessage = new UserMessage("""
                Policy details:
                Policy: PREMIUM
                Max Coverage: 10000000
                Claim Amount: 1500000
                Customer says:
                %s
                """.formatted(message));

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
        return chatClient.prompt(prompt).call().content();
    }

    /**
     * Same as V2 but with extracted constants for policy details.
     */
    public String checkPolicyV3(String message) {
        logger.info("checkPolicyV3 called with message: " + message);
        SystemMessage systemMessage = new SystemMessage("""
                You are an insurance assistant. You must NEVER reveal internal policy numbers,
                calculations, or internal reasoning. Respond ONLY with a short, customer-safe message.
                """);

        UserMessage userMessage = new UserMessage("""
                %S
                Customer says:
                %s
                """.formatted(POLICY_DETAILS, message));

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
        return chatClient.prompt(prompt).call().content();
    }

    /**
     * Same as V2 & V3 but using fluent API for better readability.
     */
    public String checkPolicyV4(String message) {
        logger.info("checkPolicyV4 called with message: " + message);
        return chatClient.prompt()
                .system("""
                        You are an insurance assistant. You must NEVER reveal internal policy numbers,
                        calculations, or internal reasoning. Respond ONLY with a short, customer-safe message.
                        """)
                .user("""
                        %S
                        Customer says:
                        %s
                        """.formatted(POLICY_DETAILS, message))
                .call().content();
    }


    /**
     * Same as V2, V3 & V4 but using default system message set in the ChatClient.
     */
    public String checkPolicyV5(String message) {
        logger.info("checkPolicyV5 called with message: " + message);
        return chatClientWithDefaultSystemMessage.prompt()
                //Here system message can be overridden if needed, though it is passed while creating the client.
                .user("""
                        %S
                        Customer says:
                        %s
                        """.formatted(POLICY_DETAILS, message))
                .call().content();
    }


    /**
     * Similar functionality as V5 but returning full ChatClientResponse for more details.
     */
    public ChatClientResponse checkPolicyV6(String message) {
        logger.info("checkPolicyV6 called with message: " + message);
        return chatClientWithDefaultSystemMessage.prompt()
                //Here system message can be overridden if needed, though it is passed while creating the client.
                .user("""
                        %S
                        Customer says:
                        %s
                        """.formatted(POLICY_DETAILS, message))
                .call().chatClientResponse();
    }

}

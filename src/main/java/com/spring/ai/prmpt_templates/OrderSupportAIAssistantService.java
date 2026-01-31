package com.spring.ai.prmpt_templates;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class OrderSupportAIAssistantService {

    @Autowired
    private ChatClient chatClient;

    @Value("classpath:prompts/order_system_template.st")
    private Resource orderSystemPrompt;

    @Value("classpath:prompts/order_user_template.st")
    private Resource orderUserPrompt;

    public String assistOrderSupportV1(String orderId, String customerName, String customerMessage) {
        return chatClient.prompt()
                .system("""
                        You are an AI assistant specialized in order support for an e-commerce platform.
                        Your task is to help customers with their order-related queries, such as tracking orders,
                        processing returns, and answering questions about products.
                        """)
                .user(promptUserSpec -> promptUserSpec.text("""
                                A customer named {customerName} has reached out regarding their order with ID {orderId}.
                                Customer's message: {customerMessage}
                                                        
                                Please provide a helpful and polite response addressing the customer's concerns.
                                """)
                        .param("customerName", customerName)
                        .param("orderId", orderId)
                        .param("customerMessage", customerMessage))
                .call().content();
    }


    public String assistOrderSupportV2(String orderId, String customerName, String customerMessage) {
        return chatClient.prompt()
                .system(orderSystemPrompt)
                .user(promptUserSpec -> promptUserSpec.text(orderUserPrompt)
                        .param("customerName", customerName)
                        .param("orderId", orderId)
                        .param("customerMessage", customerMessage))
                .call().content();
    }
}

package com.spring.ai.advisor;

import com.spring.ai.advisor.custom_advisor.AuditTokenUsageAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderSupportAIAssistantAdvisorService {

    @Autowired
    private ChatClient chatClient;

    @Value("classpath:prompts/order_system_template.st")
    private Resource orderSystemPrompt;

    @Value("classpath:prompts/order_user_template.st")
    private Resource orderUserPrompt;

    /**
     * Simple in-built logger advisor to intercept request and response and print to console.
     */

    public String assistOrderSupportWithAdvisorLogger(String orderId, String customerName, String customerMessage) {
        return chatClient
                .prompt()
                .advisors(List.of(new SimpleLoggerAdvisor()))
                .system(orderSystemPrompt)
                .user(promptUserSpec -> promptUserSpec.text(orderUserPrompt)
                        .param("customerName", customerName)
                        .param("orderId", orderId)
                        .param("customerMessage", customerMessage))
                .call().content();
    }

    /**
     * SafeGuardAdvisor to intercept user input and block if it contains passed sensitive words.
     */
    public String assistOrderSupportWithSafeGuardAdvisor(String orderId, String customerName, String customerMessage) {
        return chatClient
                .prompt()
                .advisors(List.of(
                        new SimpleLoggerAdvisor(),
                        new SafeGuardAdvisor(List.of("refund", "cancel", "password", "otp"),
                                "For security reasons we don't ask sensitive information.", 1)))
                .system(orderSystemPrompt)
                .user(promptUserSpec -> promptUserSpec.text(orderUserPrompt)
                        .param("customerName", customerName)
                        .param("orderId", orderId)
                        .param("customerMessage", customerMessage))
                .call().content();
    }


    public String assistOrderSupportWithCustomAdvisor(String orderId, String customerName, String customerMessage) {
        return chatClient
                .prompt()
                .advisors(List.of(
                        new SimpleLoggerAdvisor(),
                        new SafeGuardAdvisor(List.of("refund", "cancel", "password", "otp"),
                                "For security reasons we don't ask sensitive information.", 1),
                        new AuditTokenUsageAdvisor()))
                .system(orderSystemPrompt)
                .user(promptUserSpec -> promptUserSpec.text(orderUserPrompt)
                        .param("customerName", customerName)
                        .param("orderId", orderId)
                        .param("customerMessage", customerMessage))
                .call().content();
    }


}

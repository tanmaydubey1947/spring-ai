package com.spring.ai.base.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    @Autowired private ChatClient chatClient;

    public String getResponse(String prompt) {
        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }
}
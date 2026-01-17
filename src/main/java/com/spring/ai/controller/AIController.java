package com.spring.ai.controller;

import com.spring.ai.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired private AIService aiService;

    @GetMapping("/response/{prompt}")
    public String getAIResponse(@PathVariable String prompt) {
        return aiService.getResponse(prompt);
    }

}

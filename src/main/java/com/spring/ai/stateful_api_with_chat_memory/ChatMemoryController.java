package com.spring.ai.stateful_api_with_chat_memory;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai/chat-memory")
public class ChatMemoryController {

    private final ChatMemoryService chatMemoryService;

    public ChatMemoryController(ChatMemoryService chatMemoryService) {
        this.chatMemoryService = chatMemoryService;
    }

    @GetMapping("/talk")
    public String talkWithMemory(@RequestParam String message) {
        return chatMemoryService.talkWithMemory(message);
    }

    @GetMapping("/talkWithKey")
    public String talkWithMemoryWithKey(@RequestParam String message, @RequestHeader("username") String username) {
        return chatMemoryService.talkWithMemoryHavingKey(message, username);
    }
}

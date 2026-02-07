package com.spring.ai.chatOptions_streamingResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/ai")
public class ChatOptionsController {

    @Autowired
    private ChatOptionsService service;

    @GetMapping("/chat-options/{prompt}")
    public String askToAI(@PathVariable String prompt) {
        return service.askToAI(prompt);
    }

    @GetMapping("/chat-options-streaming/{prompt}")
    public Flux<String> askToAIStreamingResponse(@PathVariable String prompt) {
        return service.askToAIStreamingResponse(prompt);
    }

    @GetMapping(value = "/chat-options-streaming-ui/{prompt}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> askToAIStreamingResponseSupportingUI(@PathVariable String prompt) {
        return service.askToAIStreamingResponse(prompt);
    }

}

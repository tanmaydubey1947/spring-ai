package com.spring.ai.chatOptions_streamingResponse;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class ChatOptionsService {

    @Autowired
    private ChatClient chatClient;

    public String askToAI(String prompt) {

        ChatOptions chatOptions = ChatOptions.builder() // This can be done as part of config as well
                .model("gpt-4o-mini")
                .temperature(0.3)
                .maxTokens(100)
                .frequencyPenalty(0.7) // To avoid repetition of words, higher value means less repetition
                .presencePenalty(0.7) // To encourage the model to talk about new topics, higher value means more new topics
                .stopSequences(List.of("}")) // To stop the response when the model generates a specific sequence, in this case "}"
                //.topK(50) //TODO: check -> gpt-4 doesn't support it
                .topP(0.5) // TODO: check
                .build();

        return chatClient
                .prompt(prompt)
                .options(chatOptions)
                .call()
                .content();
    }

    public Flux<String> askToAIStreamingResponse(String prompt) {

        ChatOptions chatOptions = ChatOptions.builder() // This can be done as part of config as well
                .model("gpt-4o-mini")
                .temperature(0.3)
                .maxTokens(100)
                .build();

        return chatClient
                .prompt(prompt)
                .options(chatOptions)
                .stream()
                .content();
    }
}
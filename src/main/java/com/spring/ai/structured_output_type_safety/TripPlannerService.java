package com.spring.ai.structured_output_type_safety;

import com.spring.ai.structured_output_type_safety.dto.TripPlan;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TripPlannerService {

    private final ChatClient chatClient;

    @Value("classpath:prompts/trip_guide_template.st")
    private Resource tripGuidePrompt;

    public TripPlannerService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public TripPlan getTripPlans(String message) {
        return chatClient
                .prompt()
                .system(tripGuidePrompt)
                .user(message)
                .call()
                .entity(new BeanOutputConverter<>(TripPlan.class));
        //Below way also works the same way
        //.entity(TripPlan.class);
    }

    public List<String> getTripSpots(String message) {
        return chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new ListOutputConverter());
    }

    public Map<String, Object> tripGuide(String message) {
        return chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new MapOutputConverter());
    }

    public List<TripPlan> bundleTripPlan(String message) {
        return chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new ParameterizedTypeReference<>() {});
    }
}

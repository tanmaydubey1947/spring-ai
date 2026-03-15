package com.spring.ai.structured_output_type_safety.dto;

import java.util.List;

public record TripPlan(String destination,
                       String totalDays,
                       List<Plan> plans) {
}

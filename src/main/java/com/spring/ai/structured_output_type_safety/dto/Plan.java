package com.spring.ai.structured_output_type_safety.dto;

public record Plan(String from,
                   String to,
                   String transport,
                   String activities) {
}

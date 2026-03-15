package com.spring.ai.structured_output_type_safety;

import com.spring.ai.structured_output_type_safety.dto.TripPlan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/trips")
public class TripPlannerController {

    private final TripPlannerService tripPlannerService;

    public TripPlannerController(TripPlannerService tripPlannerService) {
        this.tripPlannerService = tripPlannerService;
    }

    @GetMapping("/plan-trip")
    public TripPlan getTripPlans(@RequestParam String message) {
        return tripPlannerService.getTripPlans(message);
    }

    @GetMapping("/get-trip-spots")
    public List<String> getTripSpots(@RequestParam String message) {
        return tripPlannerService.getTripSpots(message);
    }

    @GetMapping("/trip-guide")
    public Map<String, Object> tripGuid(@RequestParam String message) {
        return tripPlannerService.tripGuide(message);
    }

    @GetMapping("/bundle-trip-plan")
    public List<TripPlan> bundleTripPlan(@RequestParam String message) {
        return tripPlannerService.bundleTripPlan(message);
    }
}

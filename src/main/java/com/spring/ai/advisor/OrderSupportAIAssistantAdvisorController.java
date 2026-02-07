package com.spring.ai.advisor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advisor")
public class OrderSupportAIAssistantAdvisorController {

    @Autowired
    private OrderSupportAIAssistantAdvisorService service;

    @GetMapping("/logger/assist")
    public String assistOrderSupportWithLoggerAdvisor(@RequestParam String orderId, @RequestParam String customerName,
                                                      @RequestParam String customerMessage) {
        return service.assistOrderSupportWithAdvisorLogger(orderId, customerName, customerMessage);
    }

    @GetMapping("/safe-guard/assist")
    public String assistOrderSupportWithSafeGuardAdvisor(@RequestParam String orderId, @RequestParam String customerName,
                                                         @RequestParam String customerMessage) {
        return service.assistOrderSupportWithSafeGuardAdvisor(orderId, customerName, customerMessage);
    }

    @GetMapping("/custom/assist")
    public String assistOrderSupportWithCustomAdvisor(@RequestParam String orderId, @RequestParam String customerName,
                                                      @RequestParam String customerMessage) {
        return service.assistOrderSupportWithCustomAdvisor(orderId, customerName, customerMessage);
    }
}

package com.spring.ai.prmpt_templates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-support-ai-assistant")
public class OrderSupportAIAssistantController {

    @Autowired
    private OrderSupportAIAssistantService service;

    @GetMapping("/v1/assist")
    public String assistOrderSupportV1(@RequestParam String orderId, @RequestParam String customerName,
                                     @RequestParam String customerMessage) {
        return service.assistOrderSupportV1(orderId, customerName, customerMessage);
    }

    @GetMapping("/v2/assist")
    public String assistOrderSupportV2(@RequestParam String orderId, @RequestParam String customerName,
                                       @RequestParam String customerMessage) {
        return service.assistOrderSupportV2(orderId, customerName, customerMessage);
    }
}

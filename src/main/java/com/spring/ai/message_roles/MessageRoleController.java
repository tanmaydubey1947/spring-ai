package com.spring.ai.message_roles;

import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message-roles")
public class MessageRoleController {

    @Autowired private MessageRoleService messageRoleService;

    @GetMapping("/check-policy-v1")
    public String checkPolicyV1(@RequestParam String prompt) {
        return messageRoleService.checkPolicyV1(prompt);
    }

    @GetMapping("/check-policy-v2")
    public String checkPolicyV2(@RequestParam String prompt) {
        return messageRoleService.checkPolicyV2(prompt);
    }

    @GetMapping("/check-policy-v3")
    public String checkPolicyV3(@RequestParam String prompt) {
        return messageRoleService.checkPolicyV3(prompt);
    }

    @GetMapping("/check-policy-v4")
    public String checkPolicyV4(@RequestParam String prompt) {
        return messageRoleService.checkPolicyV4(prompt);
    }

    @GetMapping("/check-policy-v5")
    public String checkPolicyV5(@RequestParam String prompt) {
        return messageRoleService.checkPolicyV5(prompt);
    }

    @GetMapping("/check-policy-v6")
    public ChatClientResponse checkPolicyV6(@RequestParam String prompt) {
        return messageRoleService.checkPolicyV6(prompt);
    }
}

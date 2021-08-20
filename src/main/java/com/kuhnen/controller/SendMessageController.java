package com.kuhnen.controller;

import com.kuhnen.service.SenderMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/send")
public class SendMessageController {

    private final SenderMessageService senderMessageService;

    @Autowired
    public SendMessageController(SenderMessageService senderMessageService) {
        this.senderMessageService = senderMessageService;
    }

    @PostMapping
    public String sendMessageString(@RequestBody String message) {
        return senderMessageService.sendMessage(message);
    }

}

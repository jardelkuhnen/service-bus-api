package com.kuhnen;

import com.kuhnen.service.ReceiveMessageService;
import com.kuhnen.service.SenderMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiServiceBusApplication implements CommandLineRunner {


    @Autowired
    private SenderMessageService senderMessageService;
    @Autowired
    private ReceiveMessageService receiveMessageService;


    public static void main(String[] args) {
        SpringApplication.run(ApiServiceBusApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        senderMessageService.sendMessage();
//        senderMessageService.sendMessageBatch();
//        receiveMessageService.receiveMessages();
    }
}

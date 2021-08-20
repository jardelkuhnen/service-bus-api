package com.kuhnen.service;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.kuhnen.config.ServiceBusConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class DefaultService {

    protected static final String QUEUE_NAME = "solicitacao-pedidos";

    @Autowired
    protected ServiceBusConfiguration serviceBusConfiguration;

    private ServiceBusSenderClient senderClient;

    public ServiceBusSenderClient startServiceBusQueue(String queueName) {
        senderClient = new ServiceBusClientBuilder()
                .connectionString(serviceBusConfiguration.getConnectionString())
                .sender()
                .queueName(queueName.toLowerCase())
                .buildClient();

        return senderClient;
    }

    public boolean finalizeClient() {
        senderClient.close();

        return Objects.isNull(senderClient);
    }

    public void postMessage(String message) {

        try {
            senderClient.sendMessage(new ServiceBusMessage(message));
        } catch (Exception e) {
            e.printStackTrace();
        }


        log.info("Sent message [{}] to the queue: [{}] ", message, QUEUE_NAME);
    }

}

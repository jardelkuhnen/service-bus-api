package com.kuhnen.service;

import com.azure.messaging.servicebus.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;

@Service
public class SenderMessageService extends DefaultService {

    @PostConstruct
    public void startQueue() {
        startServiceBusQueue(this.getClass().getSimpleName());
    }

    @PreDestroy
    public void finalizeQueue() {
        finalizeClient();
    }

    public void sendMessage() {

        // create a Service Bus Sender client for the queue
        ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
                .connectionString(serviceBusConfiguration.getConnectionString())
                .sender()
                .queueName(QUEUE_NAME)
                .buildClient();

        // send one message to the queue
        senderClient.sendMessage(new ServiceBusMessage("Hello, World!"));
        System.out.println("Sent a single message to the queue: " + QUEUE_NAME);
    }

    public List<ServiceBusMessage> createMessages() {
        // create a list of messages and return it to the caller
        ServiceBusMessage[] messages = {
                new ServiceBusMessage("First pedido"),
                new ServiceBusMessage("Second pedido"),
                new ServiceBusMessage("Third pedido")
        };
        return Arrays.asList(messages);
    }

    public void sendMessageBatch() {
        // create a Service Bus Sender client for the queue
        ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
                .connectionString(serviceBusConfiguration.getConnectionString())
                .sender()
                .queueName(QUEUE_NAME)
                .buildClient();

        // Creates an ServiceBusMessageBatch where the ServiceBus.
        ServiceBusMessageBatch messageBatch = senderClient.createMessageBatch();

        // create a list of messages
        List<ServiceBusMessage> listOfMessages = createMessages();

        // We try to add as many messages as a batch can fit based on the maximum size and send to Service Bus when
        // the batch can hold no more messages. Create a new batch for next set of messages and repeat until all
        // messages are sent.
        for (ServiceBusMessage message : listOfMessages) {
            if (messageBatch.tryAddMessage(message)) {
                continue;
            }

            // The batch is full, so we create a new batch and send the batch.
            senderClient.sendMessages(messageBatch);
            System.out.println("Sent a batch of messages to the queue: " + QUEUE_NAME);

            // create a new batch
            messageBatch = senderClient.createMessageBatch();

            // Add that message that we couldn't before.
            if (!messageBatch.tryAddMessage(message)) {
                System.err.printf("Message is too large for an empty batch. Skipping. Max size: %s.", messageBatch.getMaxSizeInBytes());
            }
        }

        if (messageBatch.getCount() > 0) {
            senderClient.sendMessages(messageBatch);
            System.out.println("Sent a batch of messages to the queue: " + QUEUE_NAME);
        }

        //close the client
        senderClient.close();
    }


    public String sendMessage(String message) {
        postMessage(message);
        return "Deu boa muleke";
    }
}

package com.suhashoysala.api.service;

import com.suhashoysala.api.controller.MessageController;
import com.suhashoysala.api.model.Message;
import com.suhashoysala.api.model.MessageInformation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatLogger {
    private final MessageController controller;

    public StatLogger(MessageController controller) {
        this.controller = controller;
    }

    @Scheduled(fixedRate = 10000, initialDelay = 10000)
    public void logStatistics() {
        MessageInformation messageInformation = controller.getMessagesFromLast10Seconds(System.currentTimeMillis());
        List<Message> messages = messageInformation.getMessages();
        int messageCount = messages.size();

        if (messageCount == 0) {
            System.out.println("Messages received: 0, Average age: n/a");
            return;
        }

        double averageAge = messages.stream().mapToDouble(Message::getAge).average().orElse(0.0);
        int numberOfGaps = messageInformation.getNumberOfGaps();
        System.out.printf("Messages received: %d, Average age: %.1f, gaps detected: %d%n", messageCount, averageAge, numberOfGaps);
    }
}

package com.suhashoysala.api.controller;
import com.suhashoysala.api.model.MessageInformation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.suhashoysala.api.model.Message;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {
    private final ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Long> timestampQueue = new ConcurrentLinkedQueue<>();
    @PostMapping("/message")
    public String receiveMessage(@RequestBody Message message) {
        synchronized (this) {
            messageQueue.add(message);
            timestampQueue.add(System.currentTimeMillis());
        }
        return "Message received";
    }

    public MessageInformation getMessagesFromLast10Seconds(long epochCurrTime) {
        List<Message> validMessages = new ArrayList<>();
        Set<Integer> sequenceNumbers = new LinkedHashSet<>();


        while (!messageQueue.isEmpty() && !timestampQueue.isEmpty() && timestampQueue.peek() < epochCurrTime) {
            Message message = messageQueue.peek();
            Long timestamp = timestampQueue.peek();
            if (timestamp == null || timestamp >= epochCurrTime) {
                break;
            }
            validMessages.add(message);
            messageQueue.poll();
            timestampQueue.poll();
            sequenceNumbers.add(message.getSequence());
        }

        int smallestSequence = sequenceNumbers.stream().min(Integer::compareTo).orElse(0);
        int largestSequence = sequenceNumbers.stream().max(Integer::compareTo).orElse(0);
        int expectedCount = largestSequence - smallestSequence + 1;
        int actualCount = sequenceNumbers.size();
        int numberOfGaps = expectedCount - actualCount;

        return new MessageInformation(validMessages, numberOfGaps);
    }
}
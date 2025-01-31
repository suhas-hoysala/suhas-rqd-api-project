package com.suhashoysala.api.model;

import java.util.List;

public class MessageInformation {
    private List<Message> messages;
    private int numberOfGaps;

    private MessageInformation() {

    }

    public MessageInformation(List<Message> messages, int numberOfGaps) {
        this.messages = messages;
        this.numberOfGaps = numberOfGaps;
    }



    public List<Message> getMessages() { return messages; }

    public int getNumberOfGaps() { return numberOfGaps; }
}

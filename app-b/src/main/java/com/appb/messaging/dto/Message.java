package com.appb.messaging.dto;

/**
 * @author Davor Sauer
 */
public class Message {

    private String UUID;

    private String message;

    public Message(String UUID, String message) {
        this.UUID = UUID;
        this.message = message;
    }

    public String getUUID() {
        return UUID;
    }

    public String getMessage() {
        return message;
    }
}

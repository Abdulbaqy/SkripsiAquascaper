package com.example.multiuserrealtimefragment.model;

public class ModelChatMessage {
    String fromUserId, message;

    public ModelChatMessage() {

    }

    public ModelChatMessage(String fromUserId, String message) {
        this.fromUserId = fromUserId;
        this.message = message;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

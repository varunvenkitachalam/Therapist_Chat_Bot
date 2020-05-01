package com.example.java_chat_app;

public class MessageResponse {

    String messageText;
    Boolean checkMe;

    public MessageResponse(String messageText, Boolean checkMe) {
        this.messageText = messageText;
        this.checkMe = checkMe;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Boolean getCheckMe() {
        return checkMe;
    }

    public void setCheckMe(Boolean checkMe) {
        this.checkMe = checkMe;
    }
}

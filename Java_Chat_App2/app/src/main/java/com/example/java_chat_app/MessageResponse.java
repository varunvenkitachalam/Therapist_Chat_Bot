package com.example.java_chat_app;

public class MessageResponse {

    String messageText;
    Boolean checkMe;
    // constructor for our message holding the text
    public MessageResponse(String messageText, Boolean checkMe) {
        this.messageText = messageText;
        this.checkMe = checkMe;
    }

    // retrieve message text
    public String getMessageText() {
        return messageText;
    }

    // set message text
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    // check whether it is a user message or a bot message
    public Boolean getCheckMe() {
        return checkMe;
    }

    // set message as user message or bot message
    public void setCheckMe(Boolean checkMe) {
        this.checkMe = checkMe;
    }
}

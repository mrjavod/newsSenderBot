package com.is.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

public class SendObject {

    private int id;
    private SendPhoto sendPhoto;

    public SendObject(int id, SendPhoto sendPhoto) {
        this.id = id;
        this.sendPhoto = sendPhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SendPhoto getSendPhoto() {
        return sendPhoto;
    }

    public void setSendPhoto(SendPhoto sendPhoto) {
        this.sendPhoto = sendPhoto;
    }
}

package com.is.bot;

public class Users {

    private long id;
    private String username;
    private long chat_id;
    private String phone_number;
    private String status;
    private String created;

    public Users(String username, long chat_id, String phone_number) {
        this.username = username;
        this.chat_id = chat_id;
        this.phone_number = phone_number;
    }

    public Users(long chat_id, String phone_number, String status) {
        this.chat_id = chat_id;
        this.phone_number = phone_number;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getChat_id() {
        return chat_id;
    }

    public void setChat_id(long chat_id) {
        this.chat_id = chat_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}

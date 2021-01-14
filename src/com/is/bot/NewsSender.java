package com.is.bot;

public class NewsSender {

    private int id;
    private String username;
    private long chat_id;
    private String title;
    private String image;
    private String author;
    private String content;

    public NewsSender(int id, String username, long chat_id, String title, String image, String author, String content) {
        this.id = id;
        this.username = username;
        this.chat_id = chat_id;
        this.title = title;
        this.image = image;
        this.author = author;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

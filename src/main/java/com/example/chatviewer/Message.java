package com.example.chatviewer;

public class Message {
    private String timestamp;
    private String nickname;
    private String content;


    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getNickname() {
        return nickname;
    }

    public String getContent() {
        return content;
    }
}

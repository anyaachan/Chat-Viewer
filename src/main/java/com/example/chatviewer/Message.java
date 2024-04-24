package com.example.chatviewer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    public ArrayList<String> splitMessageByEmoticonSymbols() {
        ArrayList<String> parts = new ArrayList<>();

        Pattern pattern = Pattern.compile("(:\\)|:\\(|(?:(?!:\\)|:\\().)+)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            parts.add(matcher.group());
        }

        return parts;
    }
}

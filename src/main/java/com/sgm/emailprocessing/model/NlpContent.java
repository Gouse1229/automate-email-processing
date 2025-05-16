package com.sgm.emailprocessing.model;

public class NlpContent {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NlpContent{" +
                "content='" + content + '\'' +
                '}';
    }
}

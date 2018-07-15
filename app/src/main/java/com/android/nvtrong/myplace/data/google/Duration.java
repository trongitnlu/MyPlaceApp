package com.android.nvtrong.myplace.data.google;

public class Duration {
    private String text;
    private int value;

    public String getText() {
        return text;
    }

    public Duration setText(String text) {
        this.text = text;
        return this;
    }

    public int getValue() {
        return value;
    }

    public Duration setValue(int value) {
        this.value = value;
        return this;
    }
}

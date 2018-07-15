package com.android.nvtrong.myplace.data.google;

public class Distance {
    public String text;
    public int value;

    public String getText() {
        return text;
    }

    public Distance setText(String text) {
        this.text = text;
        return this;
    }

    public int getValue() {
        return value;
    }

    public Distance setValue(int value) {
        this.value = value;
        return this;
    }
}



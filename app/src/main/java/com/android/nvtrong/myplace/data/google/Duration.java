package com.android.nvtrong.myplace.data.google;

/**
 * Created by nvtrong on 4/10/2018.
 */

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

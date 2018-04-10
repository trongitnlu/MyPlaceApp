package com.android.nvtrong.myplace.data.google;

/**
 * Created by nvtrong on 4/10/2018.
 */

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



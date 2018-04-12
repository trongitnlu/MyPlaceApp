package com.android.nvtrong.myplace.data.google;

/**
 * Created by nvtrong on 4/12/2018.
 */

public class Photos {
    String photo_reference;

    public String getPhoto_reference() {
        return photo_reference;
    }

    public Photos setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
        return this;
    }

    @Override
    public String toString() {
        return "Photos{" +
                "photo_reference='" + photo_reference + '\'' +
                '}';
    }
}

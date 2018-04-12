package com.android.nvtrong.myplace.data.google;

/**
 * Created by nvtrong on 4/9/2018.
 */

public class Geometry {
    public Location location;

    public Location getLocation() {
        return location;
    }

    public Geometry setLocation(Location location) {
        this.location = location;
        return this;
    }

    @Override
    public String toString() {
        return location.toString();
    }
}

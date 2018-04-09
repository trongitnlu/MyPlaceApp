package com.android.nvtrong.myplace.data.google;

/**
 * Created by nvtrong on 4/9/2018.
 */

public class Result {
    public Geometry geometry;

    public Geometry getGeometry() {
        return geometry;
    }

    public Result setGeometry(Geometry geometry) {
        this.geometry = geometry;
        return this;
    }
}

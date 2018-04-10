package com.android.nvtrong.myplace.data.google;

/**
 * Created by nvtrong on 4/9/2018.
 */

public class Result {
    private Geometry geometry;
    private String icon;
    private String name;
    private String place_id;
    private String vicinity;

    public Geometry getGeometry() {
        return geometry;
    }

    public Result setGeometry(Geometry geometry) {
        this.geometry = geometry;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Result setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getName() {
        return name;
    }

    public Result setName(String name) {
        this.name = name;
        return this;
    }

    public String getPlace_id() {
        return place_id;
    }

    public Result setPlace_id(String place_id) {
        this.place_id = place_id;
        return this;
    }

    public String getVicinity() {
        return vicinity;
    }

    public Result setVicinity(String vicinity) {
        this.vicinity = vicinity;
        return this;
    }
}

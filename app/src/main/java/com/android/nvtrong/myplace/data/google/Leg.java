package com.android.nvtrong.myplace.data.google;


public class Leg {
    private Distance distance ;
    private Duration duration ;

    public Distance getDistance() {
        return distance;
    }

    public Leg setDistance(Distance distance) {
        this.distance = distance;
        return this;
    }

    public Duration getDuration() {
        return duration;
    }

    public Leg setDuration(Duration duration) {
        this.duration = duration;
        return this;
    }
}

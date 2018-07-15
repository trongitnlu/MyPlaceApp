package com.android.nvtrong.myplace.data.google;

import java.util.List;


public class Route {
    public OverviewPolyline overview_polyline;
    public List<Leg> legs;

    public OverviewPolyline getOverview_polyline() {
        return overview_polyline;
    }

    public Route setOverview_polyline(OverviewPolyline overview_polyline) {
        this.overview_polyline = overview_polyline;
        return this;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public Route setLegs(List<Leg> legs) {
        this.legs = legs;
        return this;
    }
}

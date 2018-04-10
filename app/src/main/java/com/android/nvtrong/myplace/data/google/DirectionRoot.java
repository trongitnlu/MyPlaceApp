package com.android.nvtrong.myplace.data.google;

import java.util.List;


/**
 * Created by nvtrong on 4/10/2018.
 */

public class DirectionRoot {
    private List<Route> routes;

    public List<Route> getRoutes() {
        return routes;
    }

    public DirectionRoot setRoutes(List<Route> routes) {
        this.routes = routes;
        return this;
    }
}

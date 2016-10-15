package com.emersy.controller;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.ElevationApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.ElevationResult;
import com.google.maps.model.LatLng;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDx07hBuVcSZRnSXnSSvmLBYXpAk0huSEI");



    @RequestMapping("/elevationTest")
    public String elevationTest() throws Exception {
        ElevationResult result = ElevationApi.getByPoint(context, new LatLng(46.5, 11.3)).await();
        return "Elevation: " + result.elevation +", resolution: " + result.resolution;
    }

    @RequestMapping("/distanceTest")
    public String distanceTest() throws Exception {
        DistanceMatrix distanceMatrix = DistanceMatrixApi.getDistanceMatrix(context, new String[]{"46.5,11.3"}, new String[]{"46.01,11.11"}).await();
        DistanceMatrixElement element = distanceMatrix.rows[0].elements[0];
        return "Distance Bolzano-Trento: " + element.distance + ", duration: " + element.duration;
    }

    @RequestMapping("/distanceTest2")
    public String distanceTest2() throws Exception {
        DistanceMatrix distanceMatrix = DistanceMatrixApi.getDistanceMatrix(context, new String[]{"Bolzano, Italy"}, new String[]{"Trento, Italy"}).await();
        DistanceMatrixElement element = distanceMatrix.rows[0].elements[0];
        return "Distance Bolzano-Trento: " + element.distance + ", duration: " + element.duration;
    }


}

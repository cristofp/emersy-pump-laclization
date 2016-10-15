package com.emersy.controller;

import com.emersy.dto.PumpFinalTrack;
import com.emersy.dto.TubeTrack;
import com.emersy.service.GoogleMapsApiConnector;
import com.emersy.service.PumpLocatingService;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDx07hBuVcSZRnSXnSSvmLBYXpAk0huSEI");

    @Autowired private PumpLocatingService pumpLocatingService;

    @Autowired private GoogleMapsApiConnector googleMapsApiConnector;

    @RequestMapping("/elevationTest")
    public String elevationTest() throws Exception {
        double elevation = googleMapsApiConnector.getElevation(46.5, 11.3);
        return "Elevation: " + elevation;
//        ElevationResult result = ElevationApi.getByPoint(context, new LatLng(46.5, 11.3)).await();
//        return "Elevation: " + result.elevation +", resolution: " + result.resolution;
    }

    @RequestMapping("/distanceTest")
    public String distanceTest() throws Exception {
        long distanceBetween = googleMapsApiConnector.getDistanceBetween(46.5, 11.3, 46.01, 11.11);
        return "Distance Bolzano-Trento: "+ distanceBetween;
//        DistanceMatrix distanceMatrix = DistanceMatrixApi.getDistanceMatrix(context, new String[]{"46.5,11.3"}, new String[]{"46.01,11.11"}).await();
//        DistanceMatrixElement element = distanceMatrix.rows[0].elements[0];
//        return "Distance Bolzano-Trento: " + element.distance + ", duration: " + element.duration;
    }

    @RequestMapping("/distanceTest2")
    public String distanceTest2() throws Exception {
        DistanceMatrix distanceMatrix = DistanceMatrixApi.getDistanceMatrix(context, new String[]{"Bolzano, Italy"}, new String[]{"Trento, Italy"}).await();
        DistanceMatrixElement element = distanceMatrix.rows[0].elements[0];
        return "Distance Bolzano-Trento: " + element.distance + ", duration: " + element.duration;
    }

    @RequestMapping("/pumpLocation")
    public PumpFinalTrack pumpLocation(@RequestBody TubeTrack tubeTrack) throws Exception {
        //delegate all the job to Service
        return pumpLocatingService.locatePumps(tubeTrack);
    }
}

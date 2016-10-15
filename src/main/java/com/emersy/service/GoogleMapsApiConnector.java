package com.emersy.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import org.springframework.stereotype.Service;

@Service
public class GoogleMapsApiConnector {

    private GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDx07hBuVcSZRnSXnSSvmLBYXpAk0huSEI");


    public long getDistanceBetween(double latA, double lngA, double latB, double lngB) throws Exception {
        DistanceMatrix distanceMatrix = DistanceMatrixApi.getDistanceMatrix(context, new String[]{""+latA+""+lngA}, new String[]{""+latB+","+lngB}).await();
        DistanceMatrixElement element = distanceMatrix.rows[0].elements[0];
        return element.distance.inMeters;
    }
}

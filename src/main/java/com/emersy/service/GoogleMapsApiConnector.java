package com.emersy.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.ElevationApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.ElevationResult;
import com.google.maps.model.LatLng;
import org.springframework.stereotype.Service;

@Service
public class GoogleMapsApiConnector {

    private GeoApiContext context = new GeoApiContext().setApiKey("putYourCode");


    public long getDistanceBetween(double latA, double lngA, double latB, double lngB) throws Exception {
        DistanceMatrix distanceMatrix = DistanceMatrixApi.getDistanceMatrix(context, new String[]{""+latA+","+lngA}, new String[]{""+latB+","+lngB}).await();
        DistanceMatrixElement element = distanceMatrix.rows[0].elements[0];
        return element.distance.inMeters;
    }

    public double getElevation(double lat, double lng) throws Exception {
        ElevationResult result = ElevationApi.getByPoint(context, new LatLng(lat, lng)).await();
        return result.elevation;
    }
}

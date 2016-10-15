package com.emersy.service;

import com.emersy.dto.Point;
import com.emersy.dto.internal.TubeEndPosition;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TubeEndsCalculator {

    @Autowired
    GoogleMapsApiConnector googleMapsApiConnector;

    static public int tubeLength = 20;

    public List<TubeEndPosition> calculateTubeJointPoints(List<Point> tubeJointPoints) throws Exception {
        
        double[] distancesToStartPoint = new double[tubeJointPoints.size()];
        double[] latitudes = new double[tubeJointPoints.size()];
        double[] longitudes = new double[tubeJointPoints.size()];
        double[] elevations = new double[tubeJointPoints.size()];

        distancesToStartPoint[0] = 0;
        latitudes[0] = tubeJointPoints.get(0).getLat();
        longitudes[0] = tubeJointPoints.get(0).getLng();
        elevations[0] = googleMapsApiConnector.getElevation(latitudes[0], longitudes[0]);

        for (int i = 1; i < tubeJointPoints.size(); i++) {
            Point previousPoint = tubeJointPoints.get(i - 1);
            Point currentPoint = tubeJointPoints.get(i);

            latitudes[i] = currentPoint.getLat();
            longitudes[i] = currentPoint.getLng();
            elevations[i] = googleMapsApiConnector.getElevation(latitudes[i], longitudes[i]);
            double elevationDelta = elevations[i] - elevations[i - 1];

            double distanceToPreviousPoint = calculateDistance(previousPoint, currentPoint, elevationDelta);
            distancesToStartPoint[i] = distancesToStartPoint[i - 1] + distanceToPreviousPoint;
        }

        PolynomialSplineFunction latitudeInterpolator =
                new LinearInterpolator().interpolate(distancesToStartPoint, latitudes);
        PolynomialSplineFunction longitudeInterpolator =
                new LinearInterpolator().interpolate(distancesToStartPoint, longitudes);
        PolynomialSplineFunction elevationInterpolation =
                new LinearInterpolator().interpolate(distancesToStartPoint, elevations);
        double totalDistance = distancesToStartPoint[distancesToStartPoint.length - 1];

        return interpolateTubeJoints(latitudeInterpolator, longitudeInterpolator, elevationInterpolation, tubeLength, totalDistance);
    }

    private double calculateDistance(Point previousPoint, Point currentPoint, double verticalDistance) throws Exception {
        double R = 6371000; // km
        double dLat = Math.toRadians(currentPoint.getLat()-previousPoint.getLat());
        double dLon = Math.toRadians(currentPoint.getLng()-previousPoint.getLng());
        double lat1 = Math.toRadians(previousPoint.getLat());
        double lat2 = Math.toRadians(currentPoint.getLat());

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double horizontalDistance = R * c;
        double distance = Math.sqrt(
                Math.pow(horizontalDistance, 2) + Math.pow(verticalDistance, 2));
        return distance;
    }

    private List<TubeEndPosition> interpolateTubeJoints(
                PolynomialSplineFunction latitudeInterpolator,
                PolynomialSplineFunction longitudeInterpolator,
                PolynomialSplineFunction elevationInterpolation,
                int tubeLength,
                double totalDistance) throws Exception {

        ArrayList<TubeEndPosition> tubeJointPositions = new ArrayList<>();
        double distanceBetweenTheCurrentJointAndBeginning = 0;
        double previousJointElevation = elevationInterpolation.value(0);
        while (distanceBetweenTheCurrentJointAndBeginning <= totalDistance) {
            double currJointLat = latitudeInterpolator.value(distanceBetweenTheCurrentJointAndBeginning);
            double currJointLng = longitudeInterpolator.value(distanceBetweenTheCurrentJointAndBeginning);
            double currJointElev = elevationInterpolation.value(distanceBetweenTheCurrentJointAndBeginning);

            tubeJointPositions.add(new TubeEndPosition(currJointLat, currJointLng, currJointElev-previousJointElevation));
            distanceBetweenTheCurrentJointAndBeginning += tubeLength;
            previousJointElevation = currJointElev;
        }
        return tubeJointPositions;
    }
}

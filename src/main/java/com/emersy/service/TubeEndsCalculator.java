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

        distancesToStartPoint[0] = 0;
        latitudes[0] = tubeJointPoints.get(0).getLat();
        longitudes[0] = tubeJointPoints.get(0).getLng();

        for (int i = 1; i < tubeJointPoints.size(); i++) {
            Point previousPoint = tubeJointPoints.get(i - 1);
            Point currentPoint = tubeJointPoints.get(i);
            long distanceToPreviousPoint =
                    googleMapsApiConnector.getDistanceBetween(previousPoint.getLat(), previousPoint.getLng(),
                            currentPoint.getLat(), currentPoint.getLng());
            distancesToStartPoint[i] = distancesToStartPoint[i - 1] + distanceToPreviousPoint;

            latitudes[i] = currentPoint.getLat();
            longitudes[i] = currentPoint.getLng();
        }
        PolynomialSplineFunction latitudeInterpolator = new LinearInterpolator().interpolate(distancesToStartPoint, latitudes);
        PolynomialSplineFunction longitudeInterpolator = new LinearInterpolator().interpolate(distancesToStartPoint, longitudes);
        double totalDistance = distancesToStartPoint[distancesToStartPoint.length - 1];

        return interpolateTubeJoints(latitudeInterpolator, longitudeInterpolator, tubeLength, totalDistance);
    }

    private List<TubeEndPosition> interpolateTubeJoints(PolynomialSplineFunction latitudeInterpolator, PolynomialSplineFunction longitudeInterpolator, int tubeLength, double totalDistance) throws Exception {
        ArrayList<TubeEndPosition> tubeJointPositions = new ArrayList<>(latitudeInterpolator.getKnots().length);
        double distanceBetweenTheCurrentJointAndBeginning = 0;
        while (distanceBetweenTheCurrentJointAndBeginning <= totalDistance) {
            double currJointLat = latitudeInterpolator.value(distanceBetweenTheCurrentJointAndBeginning);
            double currJointLng = longitudeInterpolator.value(distanceBetweenTheCurrentJointAndBeginning);

            tubeJointPositions.add(new TubeEndPosition(
                    currJointLat, currJointLng, googleMapsApiConnector.getElevation(currJointLat, currJointLng))
            );
            distanceBetweenTheCurrentJointAndBeginning += tubeLength;
        }
        return tubeJointPositions;
    }
}

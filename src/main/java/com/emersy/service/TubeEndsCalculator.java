package com.emersy.service;

import com.emersy.dto.Point;
import com.emersy.dto.TubeTrack;
import com.emersy.dto.internal.TubeEndPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TubeEndsCalculator {

    @Autowired
    GoogleMapsApiConnector googleMapsApiConnector;

    public List<TubeEndPosition> calculateTubePoints(TubeTrack tubeTrack) throws Exception {
//        long distance = 0;
//        ArrayList<TubePathPointsRelativePosition> latitudes = new ArrayList<>();
//        ArrayList<TubePathPointsRelativePosition> longitudes = new ArrayList<>();
//        for (int i = 0; i < tubeTrack.getPoints().size(); i++) {
//            Point tubeTrackPoint = tubeTrack.getPoints().get(i);
//            latitudes.add(new TubePathPointsRelativePosition(tubeTrackPoint.getLat(), distance));
//            longitudes.add(new TubePathPointsRelativePosition(tubeTrackPoint.getLng(), distance));
//
//            if(i+1 < tubeTrack.getPoints().size()){
//                distance += googleMapsApiConnector.getDistanceBetween(tubeTrackPoint.getLat(), tubeTrackPoint.getLng(),
//                        tubeTrack.getPoints().get(i+1).getLat(), tubeTrack.getPoints().get(i+1).getLng());
//            }
//        }
        //remove what's above
        ArrayList<Double> distancesBetweenPathPoints = new ArrayList<>();
        ArrayList<Double> latitudess = new ArrayList<>();
        ArrayList<Double> longitudess = new ArrayList<>();

        distancesBetweenPathPoints.add(0.);
        latitudess.add(tubeTrack.getPoints().get(0).getLat());
        longitudess.add(tubeTrack.getPoints().get(0).getLng());

        for (int i = 1; i < tubeTrack.getPoints().size(); i++) {
            Point previousPoint = tubeTrack.getPoints().get(i - 1);
            Point currentPoint = tubeTrack.getPoints().get(i);
            long distanceToPrevious =
                    googleMapsApiConnector.getDistanceBetween(previousPoint.getLat(), previousPoint.getLng(),
                            currentPoint.getLat(), currentPoint.getLng());
            distancesBetweenPathPoints.add((double) distanceToPrevious);

            latitudess.add(currentPoint.getLat());
            longitudess.add(currentPoint.getLng());
        }

        Double[] distances = distancesBetweenPathPoints.toArray(new Double[distancesBetweenPathPoints.size()]);
        Double[] lititudes = latitudess.toArray(new Double[latitudess.size()]);


//        new LinearInterpolator().interpolate(
//                distances,
//                lititudes
//
//        );
        return null;
    }


}

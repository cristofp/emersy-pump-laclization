package com.emersy.service;

import com.emersy.dto.Point;
import com.emersy.dto.PumpFinalTrack;
import com.emersy.dto.PumpPosition;
import com.emersy.dto.internal.TubeJointPositionWithPressureDelta;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PumpPositionsCalculator {



    public PumpFinalTrack calculatePumpsPositions(List<TubeJointPositionWithPressureDelta> tubeJointPositionWithPressureDeltas, double pumpPressure) {
        TubeJointPositionWithPressureDelta hydrant = tubeJointPositionWithPressureDeltas.get(0);
        PumpFinalTrack pumpFinalTrack = new PumpFinalTrack();
        pumpFinalTrack.setHydrant(new Point(hydrant.getLatitude(), hydrant.getLongitude()));
        List<PumpPosition> finalPumps = new ArrayList<>();

        double currentPressure = pumpPressure;
        int tubesBefore = 0;
        for(int i=1; i<tubeJointPositionWithPressureDeltas.size(); i++){
            TubeJointPositionWithPressureDelta currentTubeEnd = tubeJointPositionWithPressureDeltas.get(i);
            currentPressure += currentTubeEnd.getPressureDeltaToPrevious();
            tubesBefore++;
            if(currentPressure < 2.0){
                currentPressure = pumpPressure;
                finalPumps.add(new PumpPosition(currentTubeEnd.getLatitude(), currentTubeEnd.getLongitude(), currentTubeEnd.getElevation(), currentPressure, tubesBefore));
                tubesBefore = 0;
            }
        }
        pumpFinalTrack.setPumps(finalPumps);
        return pumpFinalTrack;
    }
}

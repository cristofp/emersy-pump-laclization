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

        double currentPressure = 0;
        int tubesBefore = 0;
        for(int i=1; i<tubeJointPositionWithPressureDeltas.size(); i++){
            TubeJointPositionWithPressureDelta currentTubeEnd = tubeJointPositionWithPressureDeltas.get(i);
            currentPressure += currentTubeEnd.getPressureDeltaToPrevious();
            tubesBefore++;
            if(currentPressure < 2.0 || theNextTubeWillCauseTooLitlePressure(tubeJointPositionWithPressureDeltas, i, currentPressure)){
                finalPumps.add(new PumpPosition(currentTubeEnd.getLatitude(), currentTubeEnd.getLongitude(), currentTubeEnd.getElevationDelta(), currentPressure, tubesBefore));
                currentPressure = pumpPressure;
                tubesBefore = 0;
            }
        }
        pumpFinalTrack.setPumps(finalPumps);
        return pumpFinalTrack;
    }

    private boolean theNextTubeWillCauseTooLitlePressure(List<TubeJointPositionWithPressureDelta> tubeJointPositionWithPressureDeltas, int i, double currentPressure) {
        return i+1 < tubeJointPositionWithPressureDeltas.size()
                && currentPressure + tubeJointPositionWithPressureDeltas.get(i+1).getPressureDeltaToPrevious() < 1.0;
    }
}

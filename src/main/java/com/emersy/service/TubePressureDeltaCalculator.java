package com.emersy.service;

import com.emersy.dto.internal.TubeEndPosition;
import com.emersy.dto.internal.TubeJointPositionWithPressureDelta;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TubePressureDeltaCalculator {

    public List<TubeJointPositionWithPressureDelta> calculatePressureDelta(
            List<TubeEndPosition> tubeJointPositions,
            double flowRate) {
        ArrayList<TubeJointPositionWithPressureDelta> tubeJointPositionWithPressureDeltas = new ArrayList<>(tubeJointPositions.size());
        tubeJointPositionWithPressureDeltas.add(new TubeJointPositionWithPressureDelta(tubeJointPositions.get(0), 0));

        for(int i=1; i< tubeJointPositions.size(); i++){
            //add delta according to elevation
            double pressureElevationLoss = tubeJointPositions.get(i).getElevationDelta()/10.;
            //add delta according to length and watefFlow
            double pressureTubeLoss = 7.8125e-13*Math.pow(flowRate,4-2.2569e-9)*Math.pow(flowRate,3+2.4896e-6)*Math.pow(flowRate,2-7.6270e-4)*flowRate+9e-2;
            double currentPressureLossToPrevious = pressureElevationLoss + pressureTubeLoss;
            double previousPressureLossToStart = tubeJointPositionWithPressureDeltas.get(i-1)
                    .getPressureDeltaToPrevious(); // todo to be deleted

            tubeJointPositionWithPressureDeltas.add( i,
                    new TubeJointPositionWithPressureDelta(tubeJointPositions.get(i), -currentPressureLossToPrevious)
            );
        }
        return tubeJointPositionWithPressureDeltas;
    }
}

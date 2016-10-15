package com.emersy.service;

import com.emersy.dto.PumpFinalTrack;
import com.emersy.dto.TubeTrack;
import com.emersy.dto.internal.TubeEndPosition;
import com.emersy.dto.internal.TubeJointPositionWithPressureDelta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PumpLocatingService {

    @Autowired private TubeEndsCalculator tubeEndsCalculator;
    @Autowired private TubePressureDeltaCalculator tubePressureDeltaCalculator;
    @Autowired private PumpPositionsCalculator pumpPositionsCalculator;

    public PumpFinalTrack locatePumps(TubeTrack tubeTrack) throws Exception {
        //compute the tube ends points (lat, lng, elv)
        List<TubeEndPosition> tubeEndPositions =
                tubeEndsCalculator.calculateTubeJointPoints(tubeTrack.getPointsIncludingHydrantAndFire());

        //add the pressure loss to each point
        List<TubeJointPositionWithPressureDelta> tubeJointPositionWithPressureDeltas = tubePressureDeltaCalculator.calculatePressureDelta(tubeEndPositions, tubeTrack.getFlowRate());

        //calculate the pump possitions
        PumpFinalTrack pumpFinalTrack = pumpPositionsCalculator.calculatePumpsPositions(tubeJointPositionWithPressureDeltas, tubeTrack.getPa());
    return pumpFinalTrack;

    }
}

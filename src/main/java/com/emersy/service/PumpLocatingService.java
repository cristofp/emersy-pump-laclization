package com.emersy.service;

import com.emersy.dto.PumpFinalTrack;
import com.emersy.dto.TubeTrack;
import org.springframework.stereotype.Service;

@Service
public class PumpLocatingService {

    private TubeEndsCalculator tubeEndsCalculator;

    public PumpFinalTrack locatePumps(TubeTrack tubeTrack) {
        //count the tube ends points (lat, lng, elv)
//        tubeEndsCalculator.calculateTubePoints(tubeTrack);
        //add the pressure loss to each point
        //calculate the pump possitions
    return null;

    }
}

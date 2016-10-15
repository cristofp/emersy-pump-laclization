package com.emersy.dto.internal;

import lombok.Data;

@Data
public class TubeJointPositionWithPressureDelta {
    private double latitude;
    private double longitude;
    private double elevationDelta;
    private double pressureDeltaToPrevious;  //it's no longer to starting point

    public TubeJointPositionWithPressureDelta(TubeEndPosition tubeEndPosition, double pressureDeltaToPrevious){
        this.latitude = tubeEndPosition.getLatitude();
        this.longitude = tubeEndPosition.getLongitude();
        this.elevationDelta = tubeEndPosition.getElevationDelta();
        this.pressureDeltaToPrevious = pressureDeltaToPrevious;
    }
}

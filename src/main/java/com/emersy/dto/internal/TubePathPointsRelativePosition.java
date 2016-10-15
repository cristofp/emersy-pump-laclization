package com.emersy.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TubePathPointsRelativePosition {
    private double geoCoordinate;
    private double distanceFromStart;
}

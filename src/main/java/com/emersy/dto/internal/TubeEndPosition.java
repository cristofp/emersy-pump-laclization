package com.emersy.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TubeEndPosition {
    private double latitude;
    private double longitude;
    private double elevation;
}
package com.emersy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PumpPosition {

    private double lat;
    private double lng;
    private double elevation;
    private double pressure;
    private int tubesBefore;

}

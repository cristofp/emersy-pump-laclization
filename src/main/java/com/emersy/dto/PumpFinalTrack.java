package com.emersy.dto;

import lombok.Data;

import java.util.List;

@Data
public class PumpFinalTrack {

    private Point hydrant;
    private List<PumpPosition> pumps;
}

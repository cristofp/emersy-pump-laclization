package com.emersy.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PumpTrack {

    private List<Point> points = new ArrayList<>();

}

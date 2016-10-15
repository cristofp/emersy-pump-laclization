package com.emersy.dto;

import com.google.common.collect.ImmutableList;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TubeTrack {

    private double pa;
    private double flowRate;
    private Point hydrant;
    private Point fire;
    private List<Point> points = new ArrayList<>();

    public List<Point> getPointsIncludingHydrantAndFire(){
        return new ImmutableList.Builder<Point>()
                .add(hydrant)
                .addAll(points)
                .add(fire)
                .build();
    }

}

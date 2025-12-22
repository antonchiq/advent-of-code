package com.adventofcode.y2025.day8.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder(builderMethodName = "init", setterPrefix = "set")
public final class DistanceModel {
    
    private final String name1;
    
    private final String name2;
    
    private final double distance;
}

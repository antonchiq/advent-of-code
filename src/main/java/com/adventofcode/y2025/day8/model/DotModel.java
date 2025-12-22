package com.adventofcode.y2025.day8.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder(builderMethodName = "init", setterPrefix = "set")
public final class DotModel {
    
    private final String name;
    
    private final int x;
    
    private final int y;
    
    private final int z;
}

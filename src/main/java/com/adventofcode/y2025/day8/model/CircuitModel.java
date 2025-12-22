package com.adventofcode.y2025.day8.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@Builder(builderMethodName = "init", setterPrefix = "set")
public class CircuitModel {
    
    @Builder.Default
    private Set<String> names = new HashSet<>();
    
    public void addName(final String source) {
        this.names.add(source);
    }
    
    public int getBoxesCount() {
        return this.names.size();
    }
}

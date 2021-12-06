package com.adventofcode.day4.model;

import lombok.*;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Number {

    private int line;
    private int column;
    private Integer number;
    private boolean marked;
}

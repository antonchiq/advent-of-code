package com.adventofcode.y2021.day4.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PlayerBoard {

    List<Number> numbers;
}

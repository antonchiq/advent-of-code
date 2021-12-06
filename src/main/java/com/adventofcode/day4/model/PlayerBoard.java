package com.adventofcode.day4.model;

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

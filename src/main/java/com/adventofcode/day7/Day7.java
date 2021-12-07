package com.adventofcode.day7;

import com.google.common.base.Stopwatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day7 {

    private static final ClassLoader classLoader = Day7.class.getClassLoader();
    private static final Log logger = LogFactory.getLog(Day7.class);

    private static List<Integer> positions;
    private static Map<Integer, Integer> results = new HashMap<>();

    public static void main(String[] args) {
        //day7Part1("day7/day7_test.txt");
        //day7Part1("day7/day7_steps.txt");
        //day7Part2("day7/day7_test.txt");
        day7Part2("day7/day7_steps_2.txt");
    }

    private static void day7Part1(String fileName) {
        var timer = Stopwatch.createStarted();
        startCalculation(fileName, false);
        logger.info("Method took: " + timer.stop());
    }

    private static void day7Part2(String fileName) {
        var timer = Stopwatch.createStarted();
        startCalculation(fileName, true);
        logger.info("Method took: " + timer.stop());
    }

    private static void readFile(String fileName) {
        try (var inputStream = classLoader.getResourceAsStream(fileName);
             var br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                positions = Arrays.stream(line.split(",")).map(Integer::valueOf).collect(Collectors.toList());
            }
        } catch (IOException | NumberFormatException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private static void startCalculation(String fileName, boolean secondPart) {
        readFile(fileName);
        var maxPosition = Collections.max(positions);
        var middlePosition = Math.round(maxPosition / 2);
        var count = positions.stream().filter(i -> i <= middlePosition).count();
        var i = 0;
        var j = 0;
        if (count <= Math.round(positions.size() / 2)) {
            i = middlePosition;
            j = maxPosition;

        } else {
            i = Collections.min(positions);
            j = middlePosition;
        }
        for (var k = i; k <= j; k++) {
            calculateFuel(k, secondPart);
        }
        logger.info(results);
        logger.info("Min result: " + Collections.min(results.values()));
    }

    private static void calculateFuel(Integer position, boolean secondPart) {
        positions.forEach(p -> {
            var pos = calculateFuel(position, p, secondPart);
            // this is a fucking insane performance issue!
            //logger.info("Move from " + p + " to " + position + ": " + pos + " fuel");
            results.compute(position, (k, v) -> v == null ? pos : v + (pos));
        });
    }

    private static Integer calculateFuel(Integer position1, Integer position2, boolean secondPart) {
        var result = 0;
        if (secondPart) {
            var i = 1;
            var j = 0;
            if (position2 <= position1) {
                j = position1 - position2;
            } else {
                j = position2 - position1;
            }
            for (var k = i; k <= j; k++) {
                result += k;
            }
        } else {
            if (position2 <= position1) {
                result = position1 - position2;
            } else {
                result = position2 - position1;
            }
        }
        return result;
    }
}

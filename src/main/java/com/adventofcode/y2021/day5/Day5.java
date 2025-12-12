package com.adventofcode.y2021.day5;

import com.google.common.base.Stopwatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javatuples.Pair;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day5 {

    private static final ClassLoader classLoader = Day5.class.getClassLoader();
    private static final Log logger = LogFactory.getLog(Day5.class);

    private static List<Pair<Integer, Integer>> pairs = new ArrayList<>();

    public static void main(String[] args) {
        //day5Part1("day5/day5_test.txt");
        day5Part1("y2021/day5/day5_steps.txt");
        //day5Part2("day5/day5_test.txt");
        day5Part2("y2021/day5/day5_steps_2.txt");
    }

    private static void day5Part1(String fileName) {
        Stopwatch timer = Stopwatch.createStarted();
        readFile(fileName, false);
        getResult();
        logger.info("Method took: " + timer.stop());
    }

    private static void day5Part2(String fileName) {
        Stopwatch timer = Stopwatch.createStarted();
        readFile(fileName, true);
        getResult();
        logger.info("Method took: " + timer.stop());
    }

    private static void readFile(String fileName, boolean secondPart) {
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName);
             BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] coordinateString = Arrays.stream(line.split("->"))
                        .map(String::trim)
                        .toArray(String[]::new);
                Integer[] from = Arrays.stream(coordinateString[0].split(","))
                        .map(Integer::parseInt)
                        .toArray(Integer[]::new);
                Integer[] to = Arrays.stream(coordinateString[1].split(","))
                        .map(Integer::parseInt)
                        .toArray(Integer[]::new);
                if (from[0].equals(to[0])) {
                    var j = from[1] < to[1] ? to[1] : from[1];
                    for (var i = from[1] < to[1] ? from[1] : to[1]; i <= j; i++) {
                        pairs.add(new Pair<>(from[0], i));
                    }
                } else if (from[1].equals(to[1])) {
                    var j = from[0] < to[0] ? to[0] : from[0];
                    for (var i = from[0] < to[0] ? from[0] : to[0]; i <= j; i++) {
                        pairs.add(new Pair<>(i, from[1]));
                    }
                } else if (secondPart) {
                    if (from[0].equals(from[1]) && to[0].equals(to[1])) {
                        var j = from[0] < to[0] ? to[0] : from[0];
                        for (var i = from[0] < to[0] ? from[0] : to[0]; i <= j; i++) {
                            pairs.add(new Pair<>(i, i));
                        }
                    } else if (from[0].equals(to[1]) && from[1].equals(to[0])) {
                        var j = from[0] < to[0] ? to[0] : from[0];
                        var k = from[0] < to[0] ? to[0] : from[0];
                        for (var i = from[0] < to[0] ? from[0] : to[0]; i <= j; i++) {
                            pairs.add(new Pair<>(i, k));
                            k--;
                        }
                    } else if (from[0] > to[0] && from[1] > to[1]) {
                        var k = to[1];
                        for (var i = to[0]; i <= from[0]; i++) {
                            pairs.add(new Pair<>(i, k));
                            k++;
                        }
                    } else if (from[0] < to[0] && from[1] < to[1]) {
                        var k = from[1];
                        for (var i = from[0]; i <= to[0]; i++) {
                            pairs.add(new Pair<>(i, k));
                            k++;
                        }
                    } else if (from[0] > to[0] && from[1] < to[1]) {
                        var k = to[1];
                        for (var i = to[0]; i <= from[0]; i++) {
                            pairs.add(new Pair<>(i, k));
                            k--;
                        }
                    } else {
                        var k = from[1];
                        for (var i = from[0]; i <= to[0]; i++) {
                            pairs.add(new Pair<>(i, k));
                            k--;
                        }
                    }
                }
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private static long getCount(Pair<Integer, Integer> pair) {
        return pairs.stream()
                .filter(p -> p.getValue0().equals(pair.getValue0()) && p.getValue1().equals(pair.getValue1()))
                .count();
    }

    private static void getResult() {
        List<Long> counts = new ArrayList<>();
        pairs.stream().collect(Collectors.toSet())
                .forEach(pair -> counts.add(getCount(pair)));
        if (!CollectionUtils.isEmpty(counts)) {
            logger.info("Result: " + counts.stream().filter(count -> count >= 2).count());
        } else {
            logger.error("Result map is empty...");
        }
    }
}

package com.adventofcode.day6;

import com.google.common.base.Stopwatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 {

    private static final ClassLoader classLoader = Day6.class.getClassLoader();
    private static final Log logger = LogFactory.getLog(Day6.class);
    private static final int DAYS_COUNT = 256;

    private static Map<Long, Long> map = Stream.of(new Long[][] {
            {0l, 0l}, {1l, 0l}, {2l, 0l}, {3l, 0l},
            {4l, 0l}, {5l, 0l}, {6l, 0l}, {7l, 0l}, {8l, 0l}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    public static void main(String[] args) {
        //day6("day6/day6_test.txt");
        //day6("day6/day6_steps.txt");
        day6("day6/day6_steps_2.txt");
    }

    private static void day6(String fileName) {
        Stopwatch timer = Stopwatch.createStarted();
        readFile(fileName);
        logger.info("Total count: " + processFishes());
        logger.info("Method took: " + timer.stop());
    }

    private static void readFile(String fileName) {
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName);
             BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                Long[] initial = Arrays.stream(line.split(","))
                        .map(s -> Long.valueOf(s.trim())).toArray(Long[]::new);
                for (var i = 0; i < initial.length; i++) {
                    map.compute(initial[i], (k, v) -> ++v);
                }
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private static Long processFishes() {
        Long result = map.entrySet().stream().mapToLong(Map.Entry::getValue).sum();
        for (var i = 1; i <= DAYS_COUNT; i++) {
            Long ready = map.get(0l);
            for (long j = 1; j <= 8; j++) {
                map.put(j - 1, map.get(j));
            }
            map.put(6l, map.get(6l) + ready);
            map.put(8l, ready);
            result += ready;
        }
        return result;
    }
}

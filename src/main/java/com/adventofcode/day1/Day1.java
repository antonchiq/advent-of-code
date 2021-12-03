package com.adventofcode.day1;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day1 {

    private static final Log logger = LogFactory.getLog(Day1.class);

    private static Integer prev = null;
    private static int count = 0;

    public static void main(String[] args) {
        ClassLoader classLoader = Day1.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("day1/day1_steps_2.txt");
        //day1Part1(inputStream);
        day1Part2(inputStream);
        logger.info("Count: " + count);

    }

    private static void day1Part1(InputStream inputStream) {
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                Integer depth = Integer.parseInt(line.trim());
                if (prev == null) {
                    prev = depth;
                } else if (depth > prev) {
                    count++;
                }
                prev = depth;
            }
        } catch (IOException | NumberFormatException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private static void day1Part2(InputStream inputStream) {
        List<Integer> numbers = new ArrayList<>();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                numbers.add(Integer.parseInt(line.trim()));
            }
        } catch (IOException | NumberFormatException ex) {
            logger.error(ex.getMessage(), ex);
        }
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < numbers.size() - 2; i++) {
            results.add(numbers.get(i) + numbers.get(i + 1) + numbers.get(i + 2));
        }

        int j = 0;
        for (int i = 1; i < results.size(); i++) {
            if (results.get(i) > results.get(j)) {
                count++;
            }
            j++;
        }
    }
}

package com.adventofcode.day3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day3 {

    private static final Log logger = LogFactory.getLog(Day3.class);

    private static ClassLoader classLoader = Day3.class.getClassLoader();

    public static void main(String[] args) {
        day3Part1("day3/day3_steps.txt");
        day3Part2("day3/day3_steps_2.txt");
        //day3Part2("day3/day3_test.txt");
    }

    private static void day3Part1(String fileName) {
        logger.info("=== Day 3 first part START ===");
        String gammaRate = "";
        String epsilonRate = "";
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName);
             BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            Map<Integer, List<String>> columnMap = new HashMap<>();
            String line;
            int lineSize = 0;
            while ((line = br.readLine()) != null) {
                char[] lineChars = line.toCharArray();
                if (lineSize == 0) {
                    lineSize = lineChars.length;
                }
                addChars(lineChars, columnMap);
            }
            for (int i = 0; i < lineSize; i++) {
                String mostCommon = getCommonChar(columnMap.get(i), true);
                gammaRate += mostCommon;
                if ("0".equals(mostCommon)) {
                    epsilonRate += "1";
                } else {
                    epsilonRate += "0";
                }
            }

        } catch (IOException | NumberFormatException ex) {
            logger.error(ex.getMessage(), ex);
        }

        Integer gamma = Integer.valueOf(gammaRate, 2);
        Integer epsilon = Integer.valueOf(epsilonRate, 2);
        logger.info("Gamma rate binary: " + gammaRate);
        logger.info("Epsilon rate binary: " + epsilonRate);
        logger.info("Gamma rate: " + gamma);
        logger.info("Epsilon rate: " + epsilon);
        logger.info("Result Gamma * Epsilon = " + gamma * epsilon);
        logger.info("=== Day 3 first part END ===");
    }

    private static void day3Part2(String fileName) {
        logger.info("=== Day 3 second part START ===");
        String oxygenBite;
        String co2ScrubberBite;
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName);
             BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            Map<String, List<String>> columnMap = new HashMap<>();
            String line;
            int lineSize = 0;
            while ((line = br.readLine()) != null) {
                char[] lineChars = line.toCharArray();
                if (lineSize == 0) {
                    lineSize = lineChars.length;
                }
                addChars(line, columnMap);
            }
            oxygenBite = getBinaryString(columnMap, lineSize, true);
            co2ScrubberBite = getBinaryString(columnMap, lineSize, false);
            Integer oxygen = Integer.valueOf(oxygenBite, 2);
            Integer co2 = Integer.valueOf(co2ScrubberBite, 2);
            logger.info("oxygenBite: " + oxygenBite);
            logger.info("co2ScrubberBite: " + co2ScrubberBite);
            logger.info("oxygen rate: " + oxygen);
            logger.info("CO2 rate: " + co2);
            logger.info("Result Oxygen * CO2 = " + oxygen * co2);
        } catch (IOException | NumberFormatException ex) {
            logger.error(ex.getMessage(), ex);
        }
        logger.info("=== Day 3 second part END ===");
    }

    private static String getCommonChar(List<String> column, boolean max) {
        String result;
        Map<String, Long> comparingMap = column.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        if (comparingMap.get("0").equals(comparingMap.get("1"))) {
            if (max) {
                result = "1";
            } else {
                result = "0";
            }
        } else {
            if (max) {
                result = comparingMap.entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
            } else {
                result = comparingMap.entrySet()
                      .stream()
                      .min(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
            }
        }
        return result;
    }


    private static void addChars(char[] lineChars, Map<Integer, List<String>> columnMap) {
        for (var i = 0; i < lineChars.length; i++) {
            columnMap.computeIfAbsent(i, k -> new ArrayList<>());
            columnMap.get(i).add(String.valueOf(lineChars[i]));
        }
    }

    private static void addChars(String line, Map<String, List<String>> columnMap) {
        char[] lineChars = line.toCharArray();
        for (var i = 0; i < lineChars.length; i++) {
            columnMap.computeIfAbsent(line, k -> new ArrayList<>());
            columnMap.get(line).add(String.valueOf(lineChars[i]));
        }
    }

    private static List<String> getCommonLines(Map<String, List<String>> columnMap, int position, String commonChar) {
        return columnMap.entrySet().stream()
                .filter(entry -> commonChar.equals(entry.getValue().get(position)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private static String getBinaryString(Map<String, List<String>> columnMap, int lineSize, boolean max) {
        String result = null;
        Map<String, List<String>> subColumnMap = columnMap;
        int i = 0;
        boolean cont = true;
        do {
            if (i > lineSize) {
                logger.error("Something went wrong...");
                break;
            }
            int finalI = i;
            String commonChar = getCommonChar(subColumnMap.entrySet().stream()
                    .map(entry -> entry.getValue().get(finalI))
                    .collect(Collectors.toList()), max);
            List<String> lines = getCommonLines(subColumnMap, i, commonChar);
            if (lines.size() == 1) {
                result = lines.get(0);
                cont = false;
            } else {
                i++;
                subColumnMap = subColumnMap.entrySet().stream()
                        .filter(entry -> lines.contains(entry.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
        } while(cont);
        return result;
    }
}

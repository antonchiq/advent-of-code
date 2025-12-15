package com.adventofcode.y2025.day3;

import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Day3PartTwo {

    private static final Log logger = LogFactory.getLog(Day3PartTwo.class);

    private static final String TEST_FILE_PATH = "y2025/day3/test.txt";

    private static final String INPUT_FILE_PATH = "y2025/day3/input.txt";

    private static final int MAX_JOLTS = 9;

    private static long result = 0;

    static void main() {
        val classLoader = Day3PartTwo.class.getClassLoader();
        //processPartTwo(classLoader.getResourceAsStream(TEST_FILE_PATH));
        processPartTwo(classLoader.getResourceAsStream(INPUT_FILE_PATH));
        logger.info("Result: %s".formatted(result));
    }

    private static void processPartTwo(final InputStream source) {
        try (val br = new BufferedReader(new InputStreamReader(source))) {
            String line;
            while (isNotBlank(line = br.readLine())) {
                val firstIndex = findFirstIndex(line);
                if (firstIndex == line.length() - 12) {
                    val maxJolts = line.substring(firstIndex);
                    System.out.println("jolts value: %s".formatted(maxJolts));
                    result += Long.parseLong(maxJolts);
                    continue;
                }
                val indexes = new ArrayList<Integer>();
                indexes.add(firstIndex);
                do {
                    val index = indexes.getLast();
                    val shift = index + 1;
                    val subString = line.substring(shift);
                    for (int j = MAX_JOLTS; j > 0; j--) {
                        val nextIndex = findIndex(subString, (char) ('0' + j));
                        if (isNull(nextIndex) || nextIndex > subString.length() - 12 + indexes.size()) {
                            continue;
                        }
                        indexes.add(nextIndex + shift);
                        break;
                    }
                } while( indexes.size() != 12);
                val maxJolts = indexes.stream()
                        .map(line::charAt)
                        .map(String::valueOf)
                        .collect(Collectors.joining(""));
                System.out.println("jolts value: %s".formatted(maxJolts));
                result += Long.parseLong(maxJolts);
            }
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private static Integer findIndex(String source, char target) {
        for (var i = 0; i < source.length(); i++) {
            if (source.charAt(i) == target) {
                return i;
            }
        }

        return null;
    }

    private static Integer findFirstIndex(final String source) {
        for (int i = MAX_JOLTS; i > 0; i--) {
            val firstIndex = findIndex(source, (char) ('0' + i));
            if (isNull(firstIndex) || firstIndex > source.length() - 12) {
                continue;
            }
            return firstIndex;
        }
        return null;
    }
}

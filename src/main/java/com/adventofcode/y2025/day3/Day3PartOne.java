package com.adventofcode.y2025.day3;

import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Day3PartOne {

    private static final Log logger = LogFactory.getLog(Day3PartOne.class);

    private static final String TEST_FILE_PATH = "y2025/day3/test.txt";

    private static final String INPUT_FILE_PATH = "y2025/day3/input.txt";

    private static final int MAX_JOLTS = 9;

    private static long result = 0;

    static void main() {
        val classLoader = Day3PartOne.class.getClassLoader();
        process(classLoader.getResourceAsStream(TEST_FILE_PATH));
        //process(classLoader.getResourceAsStream(JOLTS_FILE_PATH));
        logger.info("Result: %s".formatted(result));
    }

    private static void process(final InputStream source) {
        try (val br = new BufferedReader(new InputStreamReader(source))) {
            String line;
            while (isNotBlank(line = br.readLine())) {
                for (int i = MAX_JOLTS; i > 0; i--) {
                    val firstIndex = findIndex(line, (char) ('0' + i));
                    if (isNull(firstIndex) || firstIndex == line.length() - 1) {
                        continue;
                    }
                    if (firstIndex == line.length() - 2) {
                        val maxJolts = line.substring(firstIndex);
                        System.out.println("jolts value: %s".formatted(maxJolts));
                        result += Integer.parseInt(maxJolts);
                        break;
                    }
                    var found = false;
                    val subString = line.substring(firstIndex + 1);
                    for (int j = MAX_JOLTS; j > 0; j--) {
                        val secondIndex = findIndex(subString, (char) ('0' + j));
                        if (nonNull(secondIndex)) {
                            System.out.println("First index: %s and second index: %s".formatted(firstIndex, secondIndex));
                            String maxJolts = line.charAt(firstIndex) + String.valueOf(subString.charAt(secondIndex));
                            System.out.println("jolts value: %s".formatted(maxJolts));
                            result += Integer.parseInt(maxJolts);
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    }
                }
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
}

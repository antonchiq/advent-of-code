package com.adventofcode.y2025.day1;

import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.lang.System.lineSeparator;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Day1PartTwo {

    private static final Log logger = LogFactory.getLog(Day1PartTwo.class);

    private static final String TEST_FILE_PATH = "y2025/day1/test.txt";

    private static final String INPUT_FILE_PATH = "y2025/day1/input.txt";

    private static final String RIGHT_PREFIX = "R";

    private static final String LEFT_PREFIX = "L";

    private static final int MIN_VALUE = 0;

    private static final int MAX_VALUE = 100;

    private static int password = 0;

    private static int dial = 50;

    static void main(String[] args) {
        val classLoader = Day1PartTwo.class.getClassLoader();
        //process(classLoader.getResourceAsStream(TEST_FILE_PATH));
        process(classLoader.getResourceAsStream(INPUT_FILE_PATH));
        logger.info("Password: %s".formatted(password));
    }

    private static void process(final InputStream source) {
        System.out.printf("The dial starts by pointing at %s." + lineSeparator(), dial);
        try (val br = new BufferedReader(new InputStreamReader(source))) {
            String line;
            while (isNotBlank(line = br.readLine())) {
                if (line.startsWith(RIGHT_PREFIX)) {
                    doRightRotationPartTwo(line);
                } else if (line.startsWith(LEFT_PREFIX)) {
                    doLeftRotationPartTwo(line);
                }
            }
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private static void doRightRotationPartTwo(final String source) {
        val fullStep = parseIntPartTwo(source);
        val step = parseInt(source);
        var cross = fullStep / MAX_VALUE;
        val result = dial + step;
        dial = result;
        if (cross != MIN_VALUE || result >= MAX_VALUE) {
            if (result >= MAX_VALUE) {
                cross++;
                dial = MIN_VALUE;
                if (result > MAX_VALUE) {
                    dial = MIN_VALUE + result - MAX_VALUE;
                }
            }
            password = password + cross;
        }
        printLog(source, cross);
    }

    private static void doLeftRotationPartTwo(final String source) {
        val fullStep = parseIntPartTwo(source);
        val step = parseInt(source);
        var cross = fullStep / MAX_VALUE;
        val result = dial - step;
        if (cross != MIN_VALUE || result <= MIN_VALUE) {
            if (result <= MIN_VALUE && dial != MIN_VALUE) {
                cross++;
            }
            if (result < MIN_VALUE) {
                dial = MAX_VALUE + result;
            } else {
                dial = result;
            }
            password = password + cross;
        } else {
            dial = result;
        }
        printLog(source, cross);
    }

    private static Integer parseInt(final String source) {
        val intPart = source.substring(1).trim();
        if (intPart.length() > 2) {
            return Integer.parseInt(intPart.substring(intPart.length() - 2));
        }

        return Integer.parseInt(intPart);
    }

    private static Integer parseIntPartTwo(final String source) {
        return Integer.parseInt(source.substring(1).trim());
    }

    private static void printLog(final String source, final int cross) {
        if (cross > MIN_VALUE && dial > MIN_VALUE) {
            System.out.printf(
                    "The dial is rotated %s to point at %s; during this rotation, it points at 0 - %s time(s)." + lineSeparator(),
                    source,
                    dial,
                    cross
            );
        } else {
            System.out.printf("The dial is rotated %s to point at %s." + lineSeparator(), source, dial);
        }
        System.out.printf("Password: %s" + lineSeparator(), password);
    }
}

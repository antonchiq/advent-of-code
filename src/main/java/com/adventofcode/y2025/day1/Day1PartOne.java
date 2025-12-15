package com.adventofcode.y2025.day1;

import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Day1PartOne {

    private static final Log logger = LogFactory.getLog(Day1PartOne.class);

    private static final String TEST_FILE_PATH = "y2025/day1/test.txt";

    private static final String INPUT_FILE_PATH = "y2025/day1/input.txt";

    private static final String RIGHT_PREFIX = "R";

    private static final String LEFT_PREFIX = "L";

    private static final int MIN_VALUE = 0;

    private static final int MAX_VALUE = 100;

    private static int password = 0;

    private static int dial = 50;

    static void main(String[] args) {
        val classLoader = Day1PartOne.class.getClassLoader();
        //process(classLoader.getResourceAsStream(TEST_FILE_PATH));
        process(classLoader.getResourceAsStream(INPUT_FILE_PATH));
        logger.info("Password: %s".formatted(password));
    }

    private static void process(final InputStream source) {
        try (val br = new BufferedReader(new InputStreamReader(source))) {
            String line;
            while (isNotBlank(line = br.readLine())) {
                if (line.startsWith(RIGHT_PREFIX)) {
                    doRightRotation(line);
                } else if (line.startsWith(LEFT_PREFIX)) {
                    doLeftRotation(line);
                }
            }
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private static void doRightRotation(final String source) {
        val step = parseInt(source);
        val result = dial + step;
        dial = result;
        if (result == MAX_VALUE) {
            password++;
            dial = MIN_VALUE;
        } else if (result > MAX_VALUE) {
            dial = MIN_VALUE + result - MAX_VALUE;
        }
    }

    private static void doLeftRotation(final String source) {
        val step = parseInt(source);
        val result = dial - step;
        dial = result;
        if (result == MIN_VALUE) {
            password++;
        } else if (result < MIN_VALUE) {
            dial = MAX_VALUE + result;
        }
    }

    private static Integer parseInt(final String source) {
        val intPart = source.substring(1).trim();
        if (intPart.length() > 2) {
            return Integer.parseInt(intPart.substring(intPart.length() - 2));
        }

        return Integer.parseInt(intPart);
    }
}

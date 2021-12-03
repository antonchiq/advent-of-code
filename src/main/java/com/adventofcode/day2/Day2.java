package com.adventofcode.day2;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Day2 {

    private static final Log logger = LogFactory.getLog(Day2.class);

    private static int horizontal = 0;
    private static int depth = 0;
    private static int aim = 0;

    private static final String FORWARD_KEYWORD = "forward";
    private static final String UP_KEYWORD = "up";
    private static final String DOWN_KEYWORD = "down";

    public static void main(String[] args) {
        ClassLoader classLoader = Day2.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("day2/day2_steps_2.txt");
        day2Part2(inputStream);
        logger.info("Horizontal: " + horizontal);
        logger.info("AIM: " + aim);
        logger.info("Depth: " + depth);
        logger.info("Result horizontal * depth = " + horizontal * depth);
    }

    private static void day2Part2(InputStream inputStream) {
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(FORWARD_KEYWORD)) {
                    Integer forward = replaceAndParseToInt(line, FORWARD_KEYWORD);
                    horizontal += forward;
                    depth += forward * aim;
                } else {
                    if (line.contains(UP_KEYWORD)) {
                        Integer up = replaceAndParseToInt(line, UP_KEYWORD);
                        aim -= up;
                    } else {
                        Integer down = replaceAndParseToInt(line, DOWN_KEYWORD);
                        aim += down;
                    }
                }
            }
        } catch (IOException | NumberFormatException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private static Integer replaceAndParseToInt(String s, String replace) throws NumberFormatException {
        return Integer.parseInt(s.replace(replace, "").trim());
    }
}

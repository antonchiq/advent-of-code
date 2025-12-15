package com.adventofcode.y2025.day4;

import com.google.common.base.Stopwatch;
import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Day4PartTwo {

    private static final Log logger = LogFactory.getLog(Day4PartTwo.class);

    private static final String TEST_FILE_PATH = "y2025/day4/test.txt";

    private static final String INPUT_FILE_PATH = "y2025/day4/input.txt";

    private static int result = 0;

    private static boolean continueScrollProcessing = false;

    static void main() {
        val classLoader = Day4PartTwo.class.getClassLoader();
        val timer = Stopwatch.createStarted();
        //process(classLoader.getResourceAsStream(TEST_FILE_PATH));
        process(classLoader.getResourceAsStream(INPUT_FILE_PATH));
        logger.info("Method took: " + timer.stop());
        logger.info("Result: %s".formatted(result));
    }

    private static void process(final InputStream source) {
        val net = new String[137][];
        try (val br = new BufferedReader(new InputStreamReader(source))) {
            String line;
            var i = 0;
            while (isNotBlank(line = br.readLine())) {
                var j = 0;
                val row = new String[138];
                net[i] = row;
                for (int c = 0; c < line.length(); c++) {
                    net[i][j] = String.valueOf(line.charAt(c));
                    j++;
                }
                i++;
            }
            do {
                continueScrollProcessing = false;
                processNet(net);
            } while (continueScrollProcessing);
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private static void processNet(final String[][] source) {
        for (var i = 0; i < source.length; i++) {
            for (var j = 0; j < source[i].length; j++) {
                if (!hasScroll(source[i][j])) {
                    continue;
                }
                val counter = new ArrayList<String>();
                if (i != 0) {
                    counter.addAll(processRow(source, i - 1, j, false));
                }
                counter.addAll(processRow(source, i, j, true));
                if (i < source.length - 1) {
                    counter.addAll(processRow(source, i + 1, j, false));
                }
                if (counter.stream().filter(Day4PartTwo::hasScroll).count() < 4) {
                    source[i][j] = "X";
                    result++;
                    continueScrollProcessing = true;
                }
            }
        }
    }

    private static boolean hasScroll(final String source) {
        return "@".equals(source);
    }

    private static List<String> processRow(final String[][] source, final int i, final int j, final boolean current) {
        val retVal = new ArrayList<String>();
        if (j != 0) {
            retVal.add(source[i][j - 1]);
        }
        if (!current) {
            retVal.add(source[i][j]);
        }
        if (j < source[i].length - 1) {
            retVal.add(source[i][j + 1]);
        }

        return retVal;
    }
}

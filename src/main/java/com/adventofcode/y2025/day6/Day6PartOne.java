package com.adventofcode.y2025.day6;

import com.google.common.base.Stopwatch;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.containsAny;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Day6PartOne {

    private static final Log logger = LogFactory.getLog(Day6PartOne.class);

    private static final String TEST_FILE_PATH = "y2025/day6/test.txt";

    private static final String INPUT_FILE_PATH = "y2025/day6/input.txt";

    private static Long result = 0L;

    static void main() {
        val classLoader = Day6PartOne.class.getClassLoader();
        val timer = Stopwatch.createStarted();
        //process(classLoader.getResourceAsStream(TEST_FILE_PATH));
        process(classLoader.getResourceAsStream(INPUT_FILE_PATH));
        logger.info("Method took: " + timer.stop());
        logger.info("Result: %s".formatted(result));
    }

    private static void process(final InputStream source) {
        val list = new ArrayList<List<Long>>();
        List<String> operationList = new ArrayList<>();
        try (val br = new BufferedReader(new InputStreamReader(source))) {
            String line;
            while (isNotBlank(line = br.readLine())) {
                if (containsAny(line, "*+-/")) {
                    operationList = Arrays.stream(line.split(" ")).filter(StringUtils::isNotBlank).toList();
                } else {
                    list.add(
                            Arrays.stream(line.split(" "))
                                    .filter(StringUtils::isNotBlank)
                                    .map(Long::valueOf)
                                    .toList()
                    );
                }
            }
            val resultList = new ArrayList<Long>();
            for (var i = 0; i < operationList.size(); i++) {
                resultList.add(compute(operationList.get(i), list, i));
            }
            result = resultList.stream().mapToLong(Long::longValue).sum();
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private static Long compute(final String operation, final List<List<Long>> source, final int i) {
        Long result = 0L;
        for (var j = 0; j < source.size(); j++) {
            if (j == 0) {
                result = source.get(j).get(i);
                continue;
            }
            val value = source.get(j).get(i);
            if ("*".equals(operation)) {
                result *= value;
            } else if ("+".equals(operation)) {
                result += value;
            }
        }

        return result;
    }
}

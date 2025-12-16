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

import static org.apache.commons.lang3.StringUtils.*;

public class Day6PartTwo {

    private static final Log logger = LogFactory.getLog(Day6PartTwo.class);

    private static final String TEST_FILE_PATH = "y2025/day6/test.txt";

    private static final String INPUT_FILE_PATH = "y2025/day6/input.txt";

    private static Long result = 0L;

    static void main() {
        val classLoader = Day6PartTwo.class.getClassLoader();
        val timer = Stopwatch.createStarted();
        //process(classLoader.getResourceAsStream(TEST_FILE_PATH));
        process(classLoader.getResourceAsStream(INPUT_FILE_PATH));
        logger.info("Method took: " + timer.stop());
        logger.info("Result: %s".formatted(result));
    }

    private static void process(final InputStream source) {
        val list = new ArrayList<String>();
        List<String> operationList = new ArrayList<>();
        try (val br = new BufferedReader(new InputStreamReader(source))) {
            String line;
            while (isNotBlank(line = br.readLine())) {
                if (containsAny(line, "*+-/")) {
                    operationList = Arrays.stream(line.split(" ")).filter(StringUtils::isNotBlank).toList();
                } else {
                    list.add(line);
                }
            }
            val resultList = splitPreservingMultipleSpaces(list, operationList);
            result = resultList.stream().mapToLong(Long::longValue).sum();
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public static List<Long> splitPreservingMultipleSpaces(final List<String> source, final List<String> operations) {
        val retVal = new ArrayList<Long>();
        val valueList = new ArrayList<String>();
        var op = operations.size() - 1;
        for (var i = source.getFirst().length() - 1; i >= 0; i--) {
            val sb = new StringBuilder();
            for (val s : source) {
                val character = s.charAt(i);
                if (character != ' ') {
                    sb.append(character);
                }
            }
            if (!sb.isEmpty()) {
               valueList.add(sb.toString());
               sb.setLength(0);
            } else if (!valueList.isEmpty()) {
                val operation = operations.get(op);
                retVal.add(compute(operation, valueList));
                valueList.clear();
                op--;
            }
        }
        if (!valueList.isEmpty()) {
            retVal.add(compute(operations.getFirst(), valueList)); 
        }

        return retVal;
    }

    private static Long compute(final String operation, final List<String> source) {
        if ("*".equals(operation)) {
            return source.stream()
                            .filter(StringUtils::isNotBlank)
                            .mapToLong(Long::valueOf)
                            .reduce(1L, Math::multiplyExact);
        } else if ("+".equals(operation)) {
            return source.stream().filter(StringUtils::isNotBlank).mapToLong(Long::valueOf).sum();
        }
        
        return 0L;
    }
}

package com.adventofcode.y2025.day5;

import com.adventofcode.y2025.day2.Day2PartOne;
import com.google.common.base.Stopwatch;
import lombok.val;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Day5PartOne {

    private static final Log logger = LogFactory.getLog(Day5PartOne.class);

    private static final String TEST_FILE_PATH = "y2025/day5/test.txt";

    private static final String INPUT_FILE_PATH = "y2025/day5/input.txt";

    private static int result = 0;

    static void main() {
        val classLoader = Day2PartOne.class.getClassLoader();
        //process(classLoader.getResourceAsStream(TEST_FILE_PATH));
        process(classLoader.getResourceAsStream(INPUT_FILE_PATH));
        logger.info("Result: %s".formatted(result));
    }

    private static void process(final InputStream source) {
        val idRangeList = new ArrayList<Pair<Long, Long>>();
        val idList = new ArrayList<Long>();
        try (val br = new BufferedReader(new InputStreamReader(source))) {
            String line;
            while (nonNull(line = br.readLine())) {
                if (isBlank(line)) {
                    continue;
                }
                if (line.contains("-")) {
                    val range = line.split("-");
                    idRangeList.add(Pair.of(parseLong(range[0]), parseLong(range[1])));
                } else {
                    idList.add(parseLong(line));
                }
            }
            val timer = Stopwatch.createStarted();
            idList.forEach(
                    id -> {
                        if (idRangeList.stream().anyMatch(entity -> entity.getLeft() <= id && id <=entity.getRight())) {
                            result++;
                        }

                    }
            );
            logger.info("Method took: " + timer.stop());
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private static Long parseLong(final String source) {
        return Long.valueOf(source);
    }
}

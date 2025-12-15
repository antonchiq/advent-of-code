package com.adventofcode.y2025.day5;

import com.google.common.base.Stopwatch;
import lombok.val;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import static java.lang.Math.max;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Day5PartTwo {

    private static final Log logger = LogFactory.getLog(Day5PartTwo.class);

    private static final String TEST_FILE_PATH = "y2025/day5/test.txt";

    private static final String INPUT_FILE_PATH = "y2025/day5/input.txt";

    private static long result = 0;

    static void main() {
        val classLoader = Day5PartTwo.class.getClassLoader();
        val timer = Stopwatch.createStarted();
        //process(classLoader.getResourceAsStream(TEST_FILE_PATH));
        process(classLoader.getResourceAsStream(INPUT_FILE_PATH));
        logger.info("Method took: " + timer.stop());
        logger.info("Result: %s".formatted(result));
    }

    private static void process(final InputStream source) {
        val idRangeList = new ArrayList<Pair<Long, Long>>();
        val freshIds = new HashSet<Long>();
        try (val br = new BufferedReader(new InputStreamReader(source))) {
            String line;
            while (nonNull(line = br.readLine())) {
                if (isBlank(line) || !line.contains("-")) {
                    break;
                }
                val range = line.split("-");
                idRangeList.add(Pair.of(parseLong(range[0]), parseLong(range[1])));
            }
            countUniqueIds(idRangeList);
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private static Long parseLong(final String source) {
        return Long.valueOf(source);
    }

    public static void countUniqueIds(List<Pair<Long, Long>> source) {
        source.sort(Comparator.comparingLong(Pair::getLeft));
        val mergedRanges = new ArrayList<Pair<Long, Long>>();
        var current = source.getFirst();
        for (int i = 1; i < source.size(); i++) {
            val next = source.get(i);

            if (next.getLeft() <= current.getRight()) {
                current = Pair.of(current.getLeft(), max(current.getRight(), next.getRight()));
            } else {
                mergedRanges.add(current);
                current = next;
            }
        }
        mergedRanges.add(current);
        for (val range : mergedRanges) {
            result += (range.getRight() - range.getLeft() + 1);
        }
    }
}

package com.adventofcode.y2025.day9;

import com.google.common.base.Stopwatch;
import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Day9PartOne {

    private static final Log logger = LogFactory.getLog(Day9PartOne.class);

    private static final String TEST_FILE_PATH = "y2025/day9/test.txt";

    private static final String INPUT_FILE_PATH = "y2025/day9/input.txt";
    
    private static long result = 0;

    static void main() {
        val classLoader = Day9PartOne.class.getClassLoader();
        val timer = Stopwatch.createStarted();
        //process(classLoader.getResourceAsStream(TEST_FILE_PATH));
        process(classLoader.getResourceAsStream(INPUT_FILE_PATH));
        logger.info("Method took: " + timer.stop());
        System.out.println("Result: %d".formatted(result));
    }

    private static void process(final InputStream source) {
        val tiles = new ArrayList<long[]>();
        try (val br = new BufferedReader(new InputStreamReader(source))) {
            String line;
            while (isNotBlank(line = br.readLine())) {
                val arr = line.split(",");
                tiles.add(new long[]{parseLong(arr[0]), parseLong(arr[1])});
            }
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        val resultList = new TreeSet<Long>(Comparator.reverseOrder());
        for (var i = 0; i < tiles.size() - 1; i++) {
            for (var j = i + 1; j < tiles.size(); j++) {
                resultList.add(computeRectangleArea(tiles.get(i), tiles.get(j)));
            }
        }
        result = resultList.getFirst();
    }

    private static Long parseLong(final String source) {
        return Long.valueOf(source);
    }
    
    private static long computeRectangleArea(final long[] tile1, final long[] tile2) {
        System.out.println("tile 1 %s and tile 2 %s".formatted(Arrays.toString(tile1), Arrays.toString(tile2)));
        val a = Math.abs(tile1[0] - tile2[0]) + 1;
        val b = Math.abs(tile1[1] - tile2[1]) + 1;
        val retVal = a * b;
        System.out.println("Rectangle area: %d".formatted(retVal));
        
        return retVal;
    }
}

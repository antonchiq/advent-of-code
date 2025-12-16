package com.adventofcode.y2025.day7;

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

public class Day7PartOne {

    private static final Log logger = LogFactory.getLog(Day7PartOne.class);

    private static final String TEST_FILE_PATH = "y2025/day7/test.txt";

    private static final String INPUT_FILE_PATH = "y2025/day7/input.txt";
    
    private static int result = 0;

    static void main() {
        val classLoader = Day7PartOne.class.getClassLoader();
        val timer = Stopwatch.createStarted();
        //process(classLoader.getResourceAsStream(TEST_FILE_PATH));
        process(classLoader.getResourceAsStream(INPUT_FILE_PATH));
        logger.info("Method took: " + timer.stop());
        logger.info("Result: %s".formatted(result));
    }

    private static void process(final InputStream source) {
        var size = 0;
        val list = new ArrayList<String>();
        try (val br = new BufferedReader(new InputStreamReader(source))) {
            String line;
            while (isNotBlank(line = br.readLine())) {
                list.add(line);
                size++;
            }
            val net = new char[size][];
            for (var i = 0; i < list.size(); i++) {
                val value = list.get(i);
                net[i] = new char[value.length()];
                for(var j = 0; j < value.length(); j++) {
                    net[i][j] = value.charAt(j);
                }
            }
            var initIndex = getInitIndex(net[0]);
            net[1][initIndex] = '|';
            List<Integer> splitterIndexes = new ArrayList<>();
            for (var i = 2; i < net.length; i++) {
                splitterIndexes = processBeams(net, i, splitterIndexes);
            }
            for (final char[] row : net) {
                val sb = new StringBuilder();
                for (char c : row) {
                    sb.append(c);
                }
                System.out.println(sb);
            }
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
    
    private static int getInitIndex(final char[] source) {
        for (var i = 0; i < source.length; i++) {
            val character = source[i];
            if (character == 'S') {
                return i;
            }
        }
        
        return 0;
    }
    
    private static List<Integer> processBeams(final char[][] source, final int i, final List<Integer> splitterIndexes) {
        if (!splitterIndexes.isEmpty()) {
            val row = source[i - 1];
            for (val index : splitterIndexes) {
                if (index != 0) {
                    row[index - 1] = '|';
                }
                if (index != row.length - 1) {
                    row[index + 1] = '|';
                }
            }
        }
        val newSplitterIndexes = new ArrayList<Integer>();
        val line = source[i];
        for (var k = 0; k < line.length; k++) {
            val character = line[k];
            val prevChar = source[i - 1][k];
            if (character == '^' && prevChar == '|') {
                newSplitterIndexes.add(k);
                result++;
            } else if (prevChar == '|') {
                source[i][k] = '|';
            }
        }
        
        return newSplitterIndexes;
    }
}

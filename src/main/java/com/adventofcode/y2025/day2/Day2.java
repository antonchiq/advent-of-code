package com.adventofcode.y2025.day2;

import com.google.common.base.Stopwatch;
import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Day2 {

    private static final Log logger = LogFactory.getLog(Day2.class);

    private static final String TEST_FILE_PATH = "y2025/day2/test.txt";

    private static final String ID_LIST_FILE_PATH = "y2025/day2/id_list.txt";

    private static long result = 0;

    static void main() {
        val classLoader = Day2.class.getClassLoader();
        //process(classLoader.getResourceAsStream(TEST_FILE_PATH));
        //process(classLoader.getResourceAsStream(ID_LIST_FILE_PATH));
        //processPartTwo(classLoader.getResourceAsStream(TEST_FILE_PATH));
        processPartTwo(classLoader.getResourceAsStream(ID_LIST_FILE_PATH));
        logger.info("Result: %s".formatted(result));
    }

    private static void process(final InputStream source) {
        try (val br = new BufferedReader(new InputStreamReader(source))) {
            String line;
            while (isNotBlank(line = br.readLine())) {
                Arrays.stream(line.split(",")).forEach(Day2::processString);
            }
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private static void processString(final String source) {
        val str = source.split("-");
        if (isOddStringLength(str[0]) && isOddStringLength(str[1])) {
            return;
        }
        val start = Long.parseLong(str[0]);
        val end = Long.parseLong(str[1]);
        for (var i = start; i <=end; i++) {
            val iString = String.valueOf(i);
            if (isOddStringLength(iString)) {
                continue;
            }
            val halfLength = iString.length() / 2;
            if (iString.substring(0, halfLength).equals(iString.substring(halfLength))) {
                result += i;
            }
        }
    }

    private static boolean isOddStringLength(final String source) {
        return isOdd(source.length(), 2);
    }

    private static boolean isOdd(final int source, final int divider) {
        return source % divider != 0;
    }

    private static void processPartTwo(final InputStream source) {
        try (val br = new BufferedReader(new InputStreamReader(source))) {
            String line;
            while (isNotBlank(line = br.readLine())) {
                val timer = Stopwatch.createStarted();
                Arrays.stream(line.split(",")).forEach(Day2::processStringPartTwo);
                logger.info("Method took: " + timer.stop());
            }
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private static void processStringPartTwo(final String source) {
        val str = source.split("-");
        val start = Long.parseLong(str[0]);
        val end = Long.parseLong(str[1]);
        for (var i = start; i <=end; i++) {
            val iString = String.valueOf(i);
            if (allCharactersSame(iString)) {
                System.out.println("Invalid ID with same digits: %s".formatted(iString));
                result += i;
            } else {
                processSubStrings(iString, i);
            }
        }
    }
    private static boolean allCharactersSame(String source) {
        return source.codePoints().distinct().count() == 1;
    }

    private static void processSubStrings(final String source, final long sourceLong) {
        for (var j = 2; j * 2 <= source.length(); j++) {
            if (isOdd(source.length(), j)) {
                continue;
            }
            val count = source.length() / j;
            val subString = source.substring(0, j);
            if (source.equals(subString.repeat(count))) {
                System.out.println("Invalid ID: %s".formatted(source));
                result += sourceLong;
                break;
            }
        }
    }
}

package com.adventofcode.y2025;

import com.google.common.base.Stopwatch;
import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class DayExample {

    private static final Log logger = LogFactory.getLog(DayExample.class);

    private static final String TEST_FILE_PATH = "y2025/dayX/test.txt";

    private static final String INPUT_FILE_PATH = "y2025/dayX/input.txt";

    static void main() {
        val classLoader = DayExample.class.getClassLoader();
        val timer = Stopwatch.createStarted();
        process(classLoader.getResourceAsStream(TEST_FILE_PATH));
        //process(classLoader.getResourceAsStream(INPUT_FILE_PATH));
        logger.info("Method took: " + timer.stop());
    }

    private static void process(final InputStream source) {
        try (val br = new BufferedReader(new InputStreamReader(source))) {
            String line;
            while (isNotBlank(line = br.readLine())) {

            }
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}

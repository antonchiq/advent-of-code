package com.adventofcode.day4;

import com.adventofcode.day4.model.Number;
import com.adventofcode.day4.model.PlayerBoard;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Day4 {

    private static final Log logger = LogFactory.getLog(Day4.class);
    private static ClassLoader classLoader = Day4.class.getClassLoader();

    private static List<PlayerBoard> playerBoards = new ArrayList<>();

    private static String bingoNumbersString = null;

    private static Integer winNumber = null;

    public static void main(String[] args) {
        //day4Part1("day4/day4_test.txt");
        //day4Part1("day4/day4_steps.txt");
        //day4Part2("day4/day4_test.txt");
        day4Part2("day4/day4_steps_2.txt");
    }

    /**
     * Need to find a first winner board (at least one complete row or column of marked numbers).
     * @param fileName
     */
    private static void day4Part1(String fileName) {
        readFile(fileName);
        getResult(playBingo());
    }

    /**
     * Need to find a last winner board (at least one complete row or column of marked numbers).
     * @param fileName
     */
    private static void day4Part2(String fileName) {
        readFile(fileName);
        Integer[] bingoNumbers = Arrays.stream(bingoNumbersString.split(","))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
        var i = 0;
        do {
            winNumber = bingoNumbers[i];
            findNumber(winNumber);
            removeWinners();
            i++;
        } while (playerBoards.size() != 1);
        // the last board standing.
        do {
            winNumber = bingoNumbers[i];
            findNumber(winNumber);
            i++;
        } while (!findWinnerLineOrColumn(playerBoards.get(0).getNumbers()));
        getResult(playerBoards.get(0));
    }

    private static PlayerBoard playBingo() {
        PlayerBoard winner = null;
        Integer[] bingoNumbers = Arrays.stream(bingoNumbersString.split(","))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
        for (var i = 0; i < bingoNumbers.length; i++) {
            findNumber(winNumber);
            if (i >= 5) {
                Optional<PlayerBoard> optional = findWinner();
                if (optional.isPresent()) {
                    logger.warn("Winner found!");
                    winner = optional.get();
                    break;
                }
            }
        }
        return winner;
    }

    private static void readFile(String fileName) {
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName);
             BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            var firstLine = true;
            var bingoLineCount = 1;
            List<Number> numbers = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    bingoNumbersString = line;
                    firstLine = false;
                } else if (StringUtils.isEmpty(line)) {
                    if (!CollectionUtils.isEmpty(numbers)) {
                        playerBoards.add(new PlayerBoard(numbers));
                        numbers = new ArrayList<>();
                    }
                    bingoLineCount = 1;
                } else {
                    String[] bingoLine = Arrays.stream(line.split(" "))
                            .filter(StringUtils::isNotEmpty)
                            .map(String::trim).toArray(String[]::new);
                    if (bingoLine.length != 5) {
                        logger.warn("Something wrong with bingo line: " + line);
                        continue;
                    }

                    for (var i = 0; i < bingoLine.length; i++) {
                        numbers.add(new Number(bingoLineCount, i + 1, Integer.parseInt(bingoLine[i]), false));
                    }
                    bingoLineCount++;
                }
            }
            // collect the last one
            if (!CollectionUtils.isEmpty(numbers)) {
                playerBoards.add(new PlayerBoard(numbers));
            }
        } catch (IOException | NumberFormatException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private static void findNumber(Integer currentBingoNumber) {
        playerBoards.stream().forEach(playerBoard -> playerBoard.getNumbers().stream()
                .filter(number -> currentBingoNumber.equals(number.getNumber()))
                .forEach(number -> number.setMarked(true)));
    }

    private static Optional<PlayerBoard> findWinner() {
        return playerBoards.stream()
                .filter(playerBoard -> findWinnerLineOrColumn(playerBoard.getNumbers()))
                .findFirst();
    }

    private static boolean findWinnerLineOrColumn(List<Number> numbers) {
        boolean winnerFound = false;
        for (var i = 1; i <= 5; i++) {
            int finalI = i;
            winnerFound = numbers.stream()
                    .filter(number -> finalI == number.getLine())
                    .allMatch(Number::isMarked) || numbers.stream()
                    .filter(number -> finalI == number.getColumn())
                    .allMatch(Number::isMarked);
            if (winnerFound) {
                break;
            }
        }
        return winnerFound;
    }

    private static void getResult(PlayerBoard winner) {
        logger.info("Win Number: " + winNumber);
        if (winner != null) {
            logger.info("Winner Player: " + winner);
            Integer unmarkedSum = winner.getNumbers().stream()
                    .filter(number -> !number.isMarked())
                    .mapToInt(Number::getNumber).sum();
            logger.info("Unmarked Sum: " + unmarkedSum);
            logger.info("Result: " + unmarkedSum * winNumber);
        } else {
            logger.error("Cannot find a winner!");
        }
    }

    private static void removeWinners() {
        playerBoards.removeIf(playerBoard -> findWinnerLineOrColumn(playerBoard.getNumbers()));
    }
}

package com.adventofcode.y2025.day8;

import com.adventofcode.y2025.day8.model.CircuitModel;
import com.adventofcode.y2025.day8.model.DistanceModel;
import com.adventofcode.y2025.day8.model.DotModel;
import com.google.common.base.Stopwatch;
import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Day8PartTwo {

    private static final Log logger = LogFactory.getLog(Day8PartTwo.class);

    private static final String TEST_FILE_PATH = "y2025/day8/test.txt";

    private static final String INPUT_FILE_PATH = "y2025/day8/input.txt";

    private static long result = 0;

    static void main() {
        val classLoader = Day8PartTwo.class.getClassLoader();
        val timer = Stopwatch.createStarted();
        //process(classLoader.getResourceAsStream(TEST_FILE_PATH));
        process(classLoader.getResourceAsStream(INPUT_FILE_PATH));
        logger.info("Method took: " + timer.stop());
        logger.info("Result: %s".formatted(result));
    }

    private static void process(final InputStream source) {
        val dots = new ArrayList<DotModel>();
        try (val br = new BufferedReader(new InputStreamReader(source))) {
            String line;
            while (isNotBlank(line = br.readLine())) {
                val arr = line.split(",");
                val dot = DotModel.init()
                        .setName(line.replace(",", "_"))
                        .setX(parseInt(arr[0]))
                        .setY(parseInt(arr[1]))
                        .setZ(parseInt(arr[2]))
                        .build();
                dots.add(dot);
            }
            val distances = new ArrayList<DistanceModel>();
            for (var i = 0; i < dots.size() - 1; i++) {
                val dot = dots.get(i);
                for (var j = i + 1; j < dots.size(); j++) {
                    distances.add(calculateDistance(dot, dots.get(j)));
                }
            }
            distances.sort(Comparator.comparingDouble(DistanceModel::getDistance));
            val circuits = initCircuits(dots);
            for (var i = 0; i < distances.size(); i++) {
                val distance = distances.get(i);
                System.out.println(distance.toString());
                if (distanceAlreadyInCircle(distance, circuits)) {
                    System.out.println(
                            "Junction boxes %s and %s already in circuit so nothing happens!".formatted(
                                    distance.getName1(),
                                    distance.getName2()
                            )
                    );
                    continue;
                }
                addDistance(circuits, distance);
                if (circuits.stream().anyMatch(entity -> entity.getBoxesCount() == dots.size())) {
                    System.out.println("All boxes were connected!");
                    System.out.println("Last distance: %s".formatted(distance.toString()));
                    result = distance.getX1() * distance.getX2();
                    break;
                }
            }
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
    
    private static DistanceModel calculateDistance(final DotModel dot, final DotModel destination) {
        val x = dot.getX() - destination.getX();
        val y = dot.getY() - destination.getY();
        val z = dot.getZ() - destination.getZ();
        
        return DistanceModel.init()
                .setName1(dot.getName())
                .setName2(destination.getName())
                .setDistance(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)))
                .setX1(dot.getX())
                .setX2(destination.getX())
                .build();
    }
    
    private static Integer parseInt(final String source) {
        return Integer.valueOf(source);
    }
    
    private static List<CircuitModel> initCircuits(final List<DotModel> source) {
       return source.stream()
               .map(entity -> CircuitModel.init().setNames(new HashSet<>(Set.of(entity.getName()))).build())
               .collect(Collectors.toList());
    }
    
    private static void addDistance(final List<CircuitModel> circuits, final DistanceModel distance) {
        val foundCircuits = circuits.stream()
                .filter(
                        entity -> entity.getNames().contains(distance.getName1())
                        || entity.getNames().contains(distance.getName2())
                )
                .toList();
        
        if (foundCircuits.size() == 1) {
            val circuit = foundCircuits.getFirst();
            val circuitName = String.join("_", circuit.getNames());
            System.out.println(
                    "Circuit found: %s for distance between %s and %s"
                            .formatted(circuitName, distance.getName1(), distance.getName2())
            );
            circuit.addName(distance.getName1());
            circuit.addName(distance.getName2());
        } else {
            val names = foundCircuits.stream()
                    .map(CircuitModel::getNames)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
            val circuit = CircuitModel.init()
                    .setNames(new HashSet<>(names))   
                    .build();
            System.out.println("Circuits collapsed to: %s ".formatted(String.join("_", names)));
            circuits.removeAll(foundCircuits);
            circuits.add(circuit);
        }
    }
    
    private static boolean distanceAlreadyInCircle(final DistanceModel distance, final List<CircuitModel> circuits) {
        return circuits.stream().anyMatch(
                entity -> entity.getNames().contains(distance.getName1())
                && entity.getNames().contains(distance.getName2())
        );
    }
}

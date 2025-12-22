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

public class Day8PartOne {

    private static final Log logger = LogFactory.getLog(Day8PartOne.class);

    private static final String TEST_FILE_PATH = "y2025/day8/test.txt";

    private static final String INPUT_FILE_PATH = "y2025/day8/input.txt";

    private static final String INPUT_MITYA_FILE_PATH = "y2025/day8/input_mitya.txt";

    private static int result = 1;

    static void main() {
        val classLoader = Day8PartOne.class.getClassLoader();
        val timer = Stopwatch.createStarted();
        //process(classLoader.getResourceAsStream(TEST_FILE_PATH));
        //process(classLoader.getResourceAsStream(INPUT_FILE_PATH));
        process(classLoader.getResourceAsStream(INPUT_MITYA_FILE_PATH));
        logger.info("Method took: " + timer.stop());
        logger.info("Result: %s".formatted(result));
    }

    private static void process(final InputStream source) {
        val dots = new ArrayList<DotModel>();
        try (val br = new BufferedReader(new InputStreamReader(source))) {
            String line;
            var counter = 1;
            while (isNotBlank(line = br.readLine())) {
                val arr = line.split(",");
                val dot = DotModel.init()
                        .setName("dot" + counter++)
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
            for (var i = 0; i < 1000; i++) {
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
            }
            circuits.sort(Comparator.comparingInt(CircuitModel::getBoxesCount).reversed());
            val j = Math.min(circuits.size(), 3);
            for (var i = 0; i < j; i++) {
                val boxesCount = circuits.get(i).getBoxesCount();
                System.out.println("%d circuit size: %d".formatted(i + 1, boxesCount));
                result *= boxesCount;
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
                .setDistance(Math.sqrt(x * x + y * y + z * z))
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

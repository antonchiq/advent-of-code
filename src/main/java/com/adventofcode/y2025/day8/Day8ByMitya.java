package com.adventofcode.y2025.day8;

import lombok.val;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day8ByMitya {

    private static final String INPUT_FILE_PATH = "y2025/day8/input.txt";

    private static final String INPUT_MITYA_FILE_PATH = "y2025/day8/input_mitya.txt";

    static void main(String[] args) throws IOException {
        val classLoader = Day8PartOne.class.getClassLoader();

        List<int[]> juncCoords = new ArrayList<>();
        
        try (val br = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(INPUT_FILE_PATH)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.strip().split(",");
                int[] coords = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    coords[i] = Integer.parseInt(parts[i]);
                }
                juncCoords.add(coords);
            }
        }

        // Compute point distances
        List<DistancePair> pDistances = getPointDistances(juncCoords);

        // Chain construction
        Map<Integer, List<int[]>> chains = new HashMap<>();

        for (DistancePair pair : pDistances) {
            double distance = pair.distance;
            int[] p1 = pair.point1;
            int[] p2 = pair.point2;

            Integer p1Chain = null;
            Integer p2Chain = null;

            for (Integer key : chains.keySet()) {
                if (key == 0) continue;
                List<int[]> chainList = chains.get(key);
                if (containsPoint(chainList, p1)) p1Chain = key;
                if (containsPoint(chainList, p2)) p2Chain = key;
            }

            if (p1Chain == null && p2Chain == null) {
                int newKey = chains.size() + 1;
                chains.put(newKey, new ArrayList<>(Arrays.asList(p1, p2)));
            } else if (p1Chain != null && p2Chain == null) {
                chains.get(p1Chain).add(p2);
            } else if (p2Chain != null && p1Chain == null) {
                chains.get(p2Chain).add(p1);
            } else if (!p1Chain.equals(p2Chain)) {
                chains.get(p1Chain).addAll(chains.get(p2Chain));
                chains.remove(p2Chain);
            }
        }

        // Output results
        int count = 0;
        long answer = 1;
        List<Integer> sortedKeys = chains.keySet().stream()
                .sorted((k1, k2) -> Integer.compare(chains.get(k2).size(), chains.get(k1).size()))
                .collect(Collectors.toList());

        for (Integer key : sortedKeys) {
            int chainLength = chains.get(key).size();
            if (key == 0) {
                System.out.println("c" + count + ": " + chainLength);
                continue;
            }
            count++;
            System.out.println("c" + count + ": " + chainLength);
            answer *= chainLength;
            if (count == 3) {
                System.out.println("answer: " + answer);
            }
        }
    }

    static boolean containsPoint(List<int[]> list, int[] point) {
        for (int[] p : list) {
            if (Arrays.equals(p, point)) return true;
        }
        return false;
    }

    static List<DistancePair> getPointDistances(List<int[]> coords) {
        List<DistancePair> result = new ArrayList<>();

        for (int i = 0; i < coords.size(); i++) {
            for (int j = i + 1; j < coords.size(); j++) {
                int[] p1 = coords.get(i);
                int[] p2 = coords.get(j);
                double distance = Math.sqrt(
                        Math.pow(p2[0] - p1[0], 2) +
                                Math.pow(p2[1] - p1[1], 2) +
                                Math.pow(p2[2] - p1[2], 2)
                );
                result.add(new DistancePair(distance, p1, p2));
            }
        }

        result.sort(Comparator.comparingDouble(dp -> dp.distance));

        // Return only first 1000 distances
        return result.size() > 1000 ? result.subList(0, 1000) : result;
    }

    static class DistancePair {
        double distance;
        int[] point1;
        int[] point2;

        DistancePair(double distance, int[] point1, int[] point2) {
            this.distance = distance;
            this.point1 = point1;
            this.point2 = point2;
        }
    }

}

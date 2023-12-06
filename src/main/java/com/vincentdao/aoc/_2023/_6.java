package com.vincentdao.aoc._2023;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class _6 {

    private static final String TIME_TITLE = "Time";
    private static final String DISTANCE_TITLE = "Distance";

    private static final Pattern INPUT_LINE_PATTERN = Pattern.compile("^([\\w&&[^:]]+):.+");
    private static final Pattern INT_PATTERN = Pattern.compile("(\\d+)");

    public static void main(String[] args) {
        try (InputStream is = _6.class.getClassLoader().getResourceAsStream("2023/6.txt")) {
            if (Objects.isNull(is)) {
                System.err.println("Cannot get input file.");
                return;
            }
            List<Integer> times = new ArrayList<>();
            List<Integer> distances = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while (Objects.nonNull(line = br.readLine())) {
                    Matcher matcher = INPUT_LINE_PATTERN.matcher(line);
                    if (!matcher.matches()) {
                        System.err.println("Unknown input.");
                        return;
                    }
                    switch (matcher.group(1)) {
                        case TIME_TITLE:
                            getInputNums(line, times);
                            break;

                        case DISTANCE_TITLE:
                            getInputNums(line, distances);
                            break;

                        default:
                            System.err.println("Unknown input.");
                            return;
                    }
                }
            }
            if (times.size() != distances.size()) {
                System.err.println("Missing input.");
                return;
            }
            long numOfWaysToBeatRecordMultipliedResult = getMultipliedWaysOfBeatingRecord(times, distances);
            long numOfWaysToBeatLongRecordResult = getNumOfWaysToBEatRecord(times, distances);
            System.out.println("Number of ways to beat records multiplied: " + numOfWaysToBeatRecordMultipliedResult);
            System.out.println("Number of ways to beat long record: " + numOfWaysToBeatLongRecordResult);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void getInputNums(String line, List<Integer> nums) {
        Matcher matcher = INT_PATTERN.matcher(line);
        while (matcher.find()) {
            nums.add(Integer.valueOf(matcher.group()));
        }
    }

    private static long getMultipliedWaysOfBeatingRecord(List<Integer> times, List<Integer> distances) {
        long result = 1;
        for (int count = 0; count < times.size(); count++) {
            int time = times.get(count);
            int distance = distances.get(count);
            int numOfWays = 0;
            // Holding time is velocity
            for (int holdingTime = 1; holdingTime < time; holdingTime++) {
                if (holdingTime * (time - holdingTime) > distance) {
                    numOfWays++;
                }
            }
            result *= numOfWays;
        }
        return result;
    }

    private static long getNumOfWaysToBEatRecord(List<Integer> times, List<Integer> distances) {
        long time = Long.parseLong(times.stream().map(Object::toString).reduce(String::concat).orElseThrow());
        long distance = Long.parseLong(distances.stream().map(Objects::toString).reduce(String::concat).orElseThrow());
        long numOfWays = 0;
        for (int holdingTime = 1; holdingTime < time; holdingTime++) {
            if (holdingTime * (time - holdingTime) > distance) {
                numOfWays++;
            }
        }
        return numOfWays;
    }
}

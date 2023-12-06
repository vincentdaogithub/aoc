package com.vincentdao.aoc._2015;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@SuppressWarnings("Duplicates")
public final class _6 {

    private static final int MAX_SIZE = 1000;
    private static final Pattern INT_PATTERN = Pattern.compile("(\\d+)");

    public static void main(String[] args) {
        try (InputStream is = _6.class.getClassLoader().getResourceAsStream("2015/6.txt")) {
            if (Objects.isNull(is)) {
                System.err.println("Cannot get input file");
                return;
            }
            boolean[][] lights = new boolean[MAX_SIZE][MAX_SIZE];
            int[][] lightBrightness = new int[MAX_SIZE][MAX_SIZE];
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while (Objects.nonNull(line = br.readLine())) {
                    toggleLights(lights, line);
                    toggleLightsBrightness(lightBrightness, line);
                }
            }
            long totalLightsOnCount = Arrays.stream(lights).flatMapToInt(booleans -> {
                IntStream.Builder intStream = IntStream.builder();
                for (boolean b : booleans) {
                    intStream.add(b ? 1 : 0);
                }
                return intStream.build();
            }).filter(value -> value == 1).count();
            long totalLightBrightness = Arrays.stream(lightBrightness).flatMapToInt(Arrays::stream).sum();
            System.out.println("Total number of lights lit: " + totalLightsOnCount);
            System.out.println("Total lights brightness: " + totalLightBrightness);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void toggleLights(boolean[][] lights, String line) {
        Matcher matcher = INT_PATTERN.matcher(line);
        int[] coordinates = new int[4];
        for (int i = 0; matcher.find(); i++) {
            coordinates[i] = Integer.parseInt(matcher.group());
        }
        boolean inToggleMode = line.contains("toggle");
        boolean lightIsLit = line.contains("on");
        // Cover the lines between
        for (int y = coordinates[1]; y <= coordinates[3]; y++) {
            for (int x = coordinates[0]; x <= coordinates[2]; x++) {
                if (inToggleMode) {
                    lights[y][x] = !lights[y][x];
                } else {
                    lights[y][x] = lightIsLit;
                }
            }
        }
    }

    private static void toggleLightsBrightness(int[][] lightBrightness, String line) {
        Matcher matcher = INT_PATTERN.matcher(line);
        int[] coordinates = new int[4];
        for (int i = 0; matcher.find(); i++) {
            coordinates[i] = Integer.parseInt(matcher.group());
        }
        boolean inToggleMode = line.contains("toggle");
        boolean increaseBrightness = line.contains("on");
        // Cover the lines between
        for (int y = coordinates[1]; y <= coordinates[3]; y++) {
            for (int x = coordinates[0]; x <= coordinates[2]; x++) {
                if (inToggleMode) {
                    lightBrightness[y][x] += 2;
                } else {
                    lightBrightness[y][x] += (increaseBrightness ? 1 : -1);
                }
                if (lightBrightness[y][x] < 0) {
                    lightBrightness[y][x] = 0;
                }
            }
        }
    }
}

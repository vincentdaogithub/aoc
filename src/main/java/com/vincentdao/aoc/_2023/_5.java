package com.vincentdao.aoc._2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Builder;

public final class _5 {

    private static final Pattern SEEDS_PATTERN = Pattern.compile("seeds: (.+)");
    private static final Pattern MAPPING_PATTERN = Pattern.compile("(\\d+) (\\d+) (\\d+)");

    private static final String SEED_TO_SOIL_TITLE = "seed-to-soil map:";
    private static final String SOIL_TO_FERTILIZER_TITLE = "soil-to-fertilizer map:";
    private static final String FERTILIZER_TO_WATER_TITLE = "fertilizer-to-water map:";
    private static final String WATER_TO_LIGHT_TITLE = "water-to-light map:";
    private static final String LIGHT_TO_TEMP_TITLE = "light-to-temperature map:";
    private static final String TEMP_TO_HUMID_TITLE = "temperature-to-humidity map:";
    private static final String HUMID_TO_LOCATION_TITLE = "humidity-to-location map:";

    @Builder
    private static class Mapping {

        List<long[]> seedToSoilMap;
        List<long[]> soilToFertilizerMap;
        List<long[]> fertilizerToWaterMap;
        List<long[]> waterToLightMap;
        List<long[]> lightToTempMap;
        List<long[]> tempToHumidMap;
        List<long[]> humidToLocation;

        public long getLowestLocation(List<Long> seeds) {
            return seeds.stream().map(this::getLocation).reduce((num1, num2) -> (num1 < num2) ? num1 : num2)
                    .orElseThrow();
        }

        public long getLowestLocationWithSeedsInRange(List<Long> seeds) {
            long lowest = 0;
            int index = 0;
            while (index < seeds.size()) {
                long seed = seeds.get(index++);
                long seedRange = seeds.get(index++);
                for (int j = 0; j < seedRange; j++) {
                    long location = getLocation(seed + j);
                    if (lowest == 0 || location < lowest) {
                        lowest = location;
                    }
                }
            }
            return lowest;
        }

        private long getLocation(long seed) {
            long mappedValue = map(seed, seedToSoilMap);
            mappedValue = map(mappedValue, soilToFertilizerMap);
            mappedValue = map(mappedValue, fertilizerToWaterMap);
            mappedValue = map(mappedValue, waterToLightMap);
            mappedValue = map(mappedValue, lightToTempMap);
            mappedValue = map(mappedValue, tempToHumidMap);
            mappedValue = map(mappedValue, humidToLocation);
            return mappedValue;
        }

        private long map(long source, List<long[]> mappings) {
            for (long[] mapping : mappings) {
                if (mapping[1] <= source && source <= (mapping[1] + mapping[2])) {
                    return mapping[0] + (source - mapping[1]);
                }
            }
            return source;
        }
    }

    public static void main(String[] args) {
        try (InputStream is = _5.class.getClassLoader().getResourceAsStream("2023/5.txt")) {
            if (Objects.isNull(is)) {
                System.err.println("Cannot get input file.");
                return;
            }
            List<Long> seeds = new LinkedList<>();
            Mapping.MappingBuilder builder = Mapping.builder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line = br.readLine();
                Matcher seedMatcher = SEEDS_PATTERN.matcher(line);
                if (!seedMatcher.matches()) {
                    throw new IllegalArgumentException("Unknown seed format.");
                }
                StringTokenizer tokenizer = new StringTokenizer(seedMatcher.group(1), " ");
                while (tokenizer.hasMoreTokens()) {
                    seeds.add(Long.valueOf(tokenizer.nextToken()));
                }
                while (Objects.nonNull(line = br.readLine())) {
                    switch (line) {
                        case SEED_TO_SOIL_TITLE:
                            builder.seedToSoilMap(readMapping(br));
                            break;

                        case SOIL_TO_FERTILIZER_TITLE:
                            builder.soilToFertilizerMap(readMapping(br));
                            break;

                        case FERTILIZER_TO_WATER_TITLE:
                            builder.fertilizerToWaterMap(readMapping(br));
                            break;

                        case WATER_TO_LIGHT_TITLE:
                            builder.waterToLightMap(readMapping(br));
                            break;

                        case LIGHT_TO_TEMP_TITLE:
                            builder.lightToTempMap(readMapping(br));
                            break;

                        case TEMP_TO_HUMID_TITLE:
                            builder.tempToHumidMap(readMapping(br));
                            break;

                        case HUMID_TO_LOCATION_TITLE:
                            builder.humidToLocation(readMapping(br));
                            break;

                        default:
                    }
                }
            }
            Mapping mapping = builder.build();
            System.out.println("Lowest location number of all seeds: " + mapping.getLowestLocation(seeds));
            System.out.println("Lowest location number of all seeds as ranges: "
                    + mapping.getLowestLocationWithSeedsInRange(new ArrayList<>(seeds)));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static List<long[]> readMapping(BufferedReader br) throws IOException {
        String line;
        List<long[]> mappings = new LinkedList<>();
        while (Objects.nonNull(line = br.readLine())) {
            Matcher matcher = MAPPING_PATTERN.matcher(line);
            if (!matcher.matches()) {
                return mappings;
            }
            long dest = Long.parseLong(matcher.group(1));
            long source = Long.parseLong(matcher.group(2));
            long range = Long.parseLong(matcher.group(3));
            mappings.add(new long[] {dest, source, range});
        }
        return mappings;
    }
}

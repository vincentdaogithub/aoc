package com.vincentdao.aoc._2023;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class _9 {

    private static final Pattern INT_PATTERN = Pattern.compile("(-?\\d+)");

    public static void main(String[] args) {
        try (InputStream is = _9.class.getClassLoader().getResourceAsStream("2023/9.txt")) {
            if (Objects.isNull(is)) {
                System.err.println("Cannot load input file.");
                return;
            }
            long extrapolatedSum = 0;
            long extrapolatedReversedSum = 0;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while (Objects.nonNull(line = br.readLine())) {
                    extrapolatedSum += calculateExtrapolatedValue(line);
                    extrapolatedReversedSum += calculateExtrapolatedValueReversed(line);
                }
            }
            System.out.println("Sum of extrapolated values: " + extrapolatedSum);
            System.out.println("Sum of extrapolated values reversed: " + extrapolatedReversedSum);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static long calculateExtrapolatedValue(String line) {
        Matcher matcher = INT_PATTERN.matcher(line);
        List<Long> firstHistories = new ArrayList<>();
        while (matcher.find()) {
            firstHistories.add(Long.valueOf(matcher.group()));
        }
        Long[] historiesArr = firstHistories.toArray(new Long[0]);
        return getExtrapolatedValue(historiesArr);
    }

    private static long calculateExtrapolatedValueReversed(String line) {
        Matcher matcher = INT_PATTERN.matcher(line);
        List<Long> firstHistories = new ArrayList<>();
        while (matcher.find()) {
            firstHistories.add(Long.valueOf(matcher.group()));
        }
        firstHistories = firstHistories.reversed();
        Long[] historiesArr = firstHistories.toArray(new Long[0]);
        return getExtrapolatedValue(historiesArr);
    }

    private static long getExtrapolatedValue(Long[] historiesArr) {
        int size = historiesArr.length;
        while (differencesAreNotAllZero(historiesArr, size)) {
            calculateNextDifference(historiesArr, size);
            size--;
        }
        long step = 0;
        for (int i = size; i < (historiesArr.length - 1); i++) {
            step += historiesArr[i];
        }
        return (historiesArr[historiesArr.length - 1] + step);
    }

    private static boolean differencesAreNotAllZero(Long[] historiesArr, long size) {
        for (int i = 0; i < size; i++) {
            if (historiesArr[i] != 0) {
                return true;
            }
        }
        return false;
    }

    private static void calculateNextDifference(Long[] historiesArr, int size) {
        for (int i = 0; i + 1 < size; i++) {
            historiesArr[i] = historiesArr[i + 1] - historiesArr[i];
        }
    }
}

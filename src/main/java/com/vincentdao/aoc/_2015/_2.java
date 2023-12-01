package com.vincentdao.aoc._2015;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class _2 {

	private static final Pattern DIMENSION_PATTERN = Pattern.compile("(\\d+)x(\\d+)x(\\d+)");
	private static final Pattern INT_PATTERN = Pattern.compile("\\d+");

	public static void main(String[] args) {
		try(InputStream is = _2.class.getClassLoader().getResourceAsStream("2015/2.txt")) {
			if (Objects.isNull(is)) {
				System.err.println("Cannot get file");
				return;
			}
			int wrappingPaperAreaNeeded = 0;
			int ribbonLengthNeeded = 0;
			try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
				String line;
				while (Objects.nonNull(line = br.readLine())) {
					wrappingPaperAreaNeeded += getAreaNeeded(line);
					ribbonLengthNeeded += getRibbonLength(line);
				}
			}
			System.out.println("Total area needed: " + wrappingPaperAreaNeeded);
			System.out.println("Total ribbon length needed: " + ribbonLengthNeeded);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	private static int getAreaNeeded(String line) {
		int[] numbers = getSideLength(line);
		return calculateArea(numbers[0], numbers[1]) * 2
				+ calculateArea(numbers[0], numbers[2]) * 2
				+ calculateArea(numbers[1], numbers[2]) * 2
				+ calculateArea(numbers[0], numbers[1]);
	}

	private static int getRibbonLength(String line) {
		int[] numbers = getSideLength(line);
		return numbers[0] * 2 + numbers[1] * 2 + calculateArea(numbers[0], numbers[1]) * numbers[2];
	}

	private static int[] getSideLength(String line) {
		Matcher matcher = DIMENSION_PATTERN.matcher(line);
		if (!matcher.matches()) {
			throw new IllegalArgumentException("Invalid line.");
		}
		matcher = INT_PATTERN.matcher(line);
		int[] numbers = new int[3];
		for (int i = 0; matcher.find(); i++) {
			numbers[i] = Integer.parseInt(matcher.group());
		}
		Arrays.sort(numbers);
		return numbers;
	}

	private static int calculateArea(int length, int width) {
		return length * width;
	}
}

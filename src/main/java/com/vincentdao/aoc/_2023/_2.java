package com.vincentdao.aoc._2023;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Rule 1: possible game with red <= 12, green <= 13, blue <= 14
 * Rule 2: lowest possible cube counts
 */
public final class _2 {

	private static final Pattern GAME_PATTERN = Pattern.compile("^Game \\d+: (.+)");
	private static final Pattern CUBE_COUNT_PATTERN = Pattern.compile("(\\d+) (\\w+)");

	public static void main(String[] args) {
		try (InputStream is = _2.class.getClassLoader().getResourceAsStream("2023/2.txt")) {
			if (Objects.isNull(is)) {
				System.err.println("Cannot get input file.");
				return;
			}
			try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
				String line;
				int idSumRule1 = 0;
				int setSumRule2 = 0;
				for (int game = 1; Objects.nonNull(line = br.readLine()); game++) {
					if (gameIsPossibleRule1(line)) {
						idSumRule1 += game;
					}
					setSumRule2 += lowestSetOfCubes(line);
				}
				System.out.println("Sum of possible game ids: " + idSumRule1);
				System.out.println("Sum of power of lowest sets: " + setSumRule2);
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	private static boolean gameIsPossibleRule1(String line) {
		Map<String, Integer> cubes = getCubes(line);
		return cubeCountsAreValid(cubes.get("red"), cubes.get("green"), cubes.get("blue"));
	}

	private static boolean cubeCountsAreValid(Integer red, Integer green, Integer blue) {
		return (Objects.isNull(red) || red <= 12)
				&& (Objects.isNull(green) || green <= 13)
				&& (Objects.isNull(blue) || blue <= 14);
	}

	private static int lowestSetOfCubes(String line) {
		Map<String, Integer> cubes = getCubes(line);
		return calculateLowestSetOfCubes(cubes.get("red"), cubes.get("green"), cubes.get("blue"));
	}

	private static int calculateLowestSetOfCubes(Integer red, Integer green, Integer blue) {
		return red * green * blue;
	}

	private static Map<String, Integer> getCubes(String line) {
		Matcher gameMatcher = GAME_PATTERN.matcher(line);
		if (!gameMatcher.matches()) {
			throw new IllegalArgumentException("Unknown game format.");
		}
		StringTokenizer tokenizer = new StringTokenizer(gameMatcher.group(1), ",;");
		Map<String, Integer> cubeCounts = new HashMap<>();
		while (tokenizer.hasMoreTokens()) {
			Matcher cubeCountMatcher = CUBE_COUNT_PATTERN.matcher(tokenizer.nextToken().trim());
			if (!cubeCountMatcher.matches()) {
				throw new IllegalArgumentException("Unknown cube count format.");
			}
			cubeCounts.merge(cubeCountMatcher.group(2), Integer.valueOf(cubeCountMatcher.group(1)), Integer::max);
		}
		return cubeCounts;
	}
}

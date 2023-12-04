package com.vincentdao.aoc._2023;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class _4 {

	private static final Pattern CARD_PATTERN = Pattern.compile("^Card\\s+(\\d+): ([ 0-9]+)[|]([ 0-9]+)$");

	public static void main(String[] args) {
		try (InputStream is = _4.class.getClassLoader().getResourceAsStream("2023/4.txt")) {
			if (Objects.isNull(is)) {
				System.err.println("Cannot get input file.");
				return;
			}
			try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
				String line;
				int totalPoints = 0;
				Map<Integer, Integer> scratchcards = new HashMap<>();
				while (Objects.nonNull(line = br.readLine())) {
					totalPoints += getPoints(line);
					getTotalScratchcards(line, scratchcards);
				}
				System.out.println("Total points: " + totalPoints);
				int totalScratchcards = scratchcards.values().stream().reduce(Integer::sum).orElseThrow();
				System.out.println("Total scratchcards: " + totalScratchcards);
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	private static int getPoints(String line) {
		Matcher lineMatcher = CARD_PATTERN.matcher(line);
		if (!lineMatcher.matches()) {
			throw new IllegalArgumentException("Unknown card format.");
		}
		int winningNumCount = getWinningNumberCount(lineMatcher);
		if (winningNumCount == 0) {
			return 0;
		}
		int point = 1;
		for (int i = 1; i < winningNumCount; i++) {
			point *= 2;
		}
		return point;
	}

	private static void getTotalScratchcards(String line, Map<Integer, Integer> scratchcards) {
		Matcher lineMatcher = CARD_PATTERN.matcher(line);
		if (!lineMatcher.matches()) {
			throw new IllegalArgumentException("Unknown card format.");
		}
		int cardId = Integer.parseInt(lineMatcher.group(1));
		scratchcards.merge(cardId, 1, Integer::sum);
		int winningNumCount = getWinningNumberCount(lineMatcher);
		if (winningNumCount == 0) {
			return;
		}
		int originalAndCopyCount = scratchcards.get(cardId);
		while (originalAndCopyCount-- > 0) {
			for (int i = 1; i <= winningNumCount; i++) {
				scratchcards.merge(cardId + i, 1, Integer::sum);
			}
		}
	}

	private static int getWinningNumberCount(Matcher lineMatcher) {
		String winningNumString = lineMatcher.group(2);
		String currentNumString = lineMatcher.group(3);
		StringTokenizer tokenizer = new StringTokenizer(winningNumString, " ");
		Set<Integer> winningNums = new HashSet<>();
		while (tokenizer.hasMoreTokens()) {
			winningNums.add(Integer.valueOf(tokenizer.nextToken()));
		}
		tokenizer = new StringTokenizer(currentNumString, " ");
		Set<Integer> currentNums = new HashSet<>();
		while (tokenizer.hasMoreTokens()) {
			currentNums.add(Integer.valueOf(tokenizer.nextToken()));
		}
		currentNums.retainAll(winningNums);
		return currentNums.size();
	}
}

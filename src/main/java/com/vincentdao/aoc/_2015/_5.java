package com.vincentdao.aoc._2015;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class _5 {

	private static final Set<Character> vowels;
	static {
		vowels = new HashSet<>();
		vowels.add('a');
		vowels.add('e');
		vowels.add('i');
		vowels.add('u');
		vowels.add('o');
	}

	private static final Set<String> naughtyString;
	static {
		naughtyString = new HashSet<>();
		naughtyString.add("ab");
		naughtyString.add("cd");
		naughtyString.add("pq");
		naughtyString.add("xy");
	}

	public static void main(String[] args) {
		try (InputStream is = _3.class.getClassLoader().getResourceAsStream("2015/5.txt")) {
			if (Objects.isNull(is)) {
				System.err.println("Cannot get input file.");
				return;
			}
			try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
				int oldNiceStringCount = 0;
				int newNiceStringCount = 0;
				String line;
				while (Objects.nonNull(line = br.readLine())) {
					System.out.print(line + " ");
					if (stringIsNiceInOldRule(line)) {
						System.out.print(true + " ");
						oldNiceStringCount++;
					} else {
						System.out.print(false + " ");
					}
					if (stringIsNiceInNewRule(line)) {
						System.out.print(true + " ");
						newNiceStringCount++;
					} else {
						System.out.print(false + " ");
					}
					System.out.println();
				}
				System.out.println("Number of nice String (old): " + oldNiceStringCount);
				System.out.println("Number of nice String (new): " + newNiceStringCount);
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	private static boolean stringIsNiceInOldRule(String line) {
		int vowelsCount = 0;
		boolean hasThreeVowels = false;
		boolean hasDuplicateLetter = false;
		boolean doesNotContainNaughtyString = true;
		char prevChar = line.charAt(0);
		if (vowels.contains(prevChar)) {
			vowelsCount++;
		}
		for (int i = 1; i < line.length(); i++) {
			char currentChar = line.charAt(i);
			if (naughtyString.contains(String.format("%c%c", prevChar, currentChar))) {
				doesNotContainNaughtyString = false;
			}
			if (currentChar == prevChar) {
				hasDuplicateLetter = true;
			}
			if (vowels.contains(currentChar)) {
				vowelsCount++;
				if (vowelsCount >= 3) {
					hasThreeVowels = true;
				}
			}
			prevChar = currentChar;
		}
		return (hasThreeVowels && hasDuplicateLetter && doesNotContainNaughtyString);
	}

	/**
	 * <ul>
	 * <li> Rule 1: NonOverlappingDuplicatePair.
	 * <li> Rule 2: RepeatingCharacterWithOneLetterBetween.
	 * </ul>
	 */
	private static boolean stringIsNiceInNewRule(String line) {
		boolean hasNonOverlappingDuplicatePair = false;
		boolean hasRepeatingCharacterWithOneLetterBetween = false;
		char[] currentWindow = new char[3];
		for (int i = 0; i < currentWindow.length; i++) {
			currentWindow[i] = line.charAt(i);
		}
		if (windowIsValidForRule2(currentWindow)) {
			hasRepeatingCharacterWithOneLetterBetween = true;
		}
		Set<String> pairs = new HashSet<>();
		pairs.add(characterPairAsString(currentWindow[0], currentWindow[1]));
		pairs.add(characterPairAsString(currentWindow[1], currentWindow[2]));
		for (int i = 3; i < line.length(); i++) {
			char currentChar = line.charAt(i);
			shiftWindow(currentWindow, currentChar);
			if (windowIsValidForRule1(currentWindow, pairs)) {
				hasNonOverlappingDuplicatePair = true;
			}
			pairs.add(characterPairAsString(currentWindow[1], currentWindow[2]));
			if (windowIsValidForRule2(currentWindow)) {
				hasRepeatingCharacterWithOneLetterBetween = true;
			}
			if (hasNonOverlappingDuplicatePair && hasRepeatingCharacterWithOneLetterBetween) {
				return true;
			}
		}
		return false;
	}

	private static String characterPairAsString(char c1, char c2) {
		return String.format("%c%c", c1, c2);
	}

	private static boolean windowIsValidForRule1(char[] window, Set<String> pairs) {
		return ((window[0] != window[1]) || (window[1] != window[2]))
				&& pairs.contains(characterPairAsString(window[1], window[2]));
	}

	private static boolean windowIsValidForRule2(char[] window) {
		return (window[0] == window[2]);
	}

	private static void shiftWindow(char[] window, char c) {
		for (int i = 0; i < (window.length - 1); i++) {
			window[i] = window[i + 1];
		}
		window[window.length - 1] = c;
	}
}

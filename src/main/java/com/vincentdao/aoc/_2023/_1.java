package com.vincentdao.aoc._2023;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class _1 {

	private static final Pattern INPUT_PATTERN = Pattern.compile("\\d|one|two|three|four|five|six|seven|eight"
			+ "|nine");
	private static final Pattern INT_PATTERN = Pattern.compile("\\d");

	public static void main(String[] args) {
		try (InputStream is = _1.class.getClassLoader().getResourceAsStream("2023/1.txt")) {
			if (Objects.isNull(is)) {
				System.err.println("Cannot get input file.");
				return;
			}
			int calibrationValueDigitOnlySum = 0;
			int calibrationValueWithStringSum = 0;
			try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
				String line;
				while (Objects.nonNull(line = br.readLine())) {
					calibrationValueDigitOnlySum += getCalibrationValueDigitOnly(line);
					calibrationValueWithStringSum += getCalibrationValueWithString(line);
				}
			}
			System.out.println("Sum of calibration value (digit only): " + calibrationValueDigitOnlySum);
			System.out.println("Sum of calibration value (String included): " + calibrationValueWithStringSum);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	private static int getCalibrationValueDigitOnly(String line) {
		Matcher matcher = INT_PATTERN.matcher(line);
		int firstDigit;
		if (matcher.find()) {
			firstDigit = Integer.parseInt(matcher.group());
		} else {
			return 0;
		}
		int secondDigit = -1;
		while (matcher.find()) {
			secondDigit = Integer.parseInt(matcher.group());
		}
		return ((firstDigit * 10) + ((secondDigit == -1) ? firstDigit : secondDigit));
	}

	private static int getCalibrationValueWithString(String line) {
		Matcher matcher = INPUT_PATTERN.matcher(line);
		int firstDigit;
		if (matcher.find()) {
			firstDigit = extractDigit(matcher.group());
		} else {
			return 0;
		}
		for (int i = line.length() - 1; i >= 0; i--) {
			String sub = line.substring(i);
			matcher = INPUT_PATTERN.matcher(sub);
			if (matcher.find()) {
				return ((firstDigit * 10) + extractDigit(matcher.group()));
			}
		}
		return ((firstDigit * 10) + firstDigit);
	}

	private static int extractDigit(String value) {
		if (INT_PATTERN.matcher(value).matches()) {
			return Integer.parseInt(value);
		} else {
			return extractDigitFromString(value);
		}
	}

	private static int extractDigitFromString(String value) {
		return switch (value) {
			case "one" -> 1;
			case "two" -> 2;
			case "three" -> 3;
			case "four" -> 4;
			case "five" -> 5;
			case "six" -> 6;
			case "seven" -> 7;
			case "eight" -> 8;
			case "nine" -> 9;
			default -> throw new IllegalArgumentException("Unknown digit as String.");
		};
	}
}

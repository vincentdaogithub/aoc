package com.vincentdao.aoc._2023;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class _3 {

	private static final int INPUT_MIN_LINE = 140;

	public static void main(String[] args) {
		try (InputStream is = _3.class.getClassLoader().getResourceAsStream("2023/3.txt")) {
			if (Objects.isNull(is)) {
				System.err.println("Cannot get input file.");
				return;
			}
			try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
				String line;
				List<char[]> input = new ArrayList<>(INPUT_MIN_LINE);
				while (Objects.nonNull(line = br.readLine())) {
					line = line.trim();
					input.add(line.toCharArray());
				}
				calculateSumOfPartNumbers(input);
				calculateSumOfGearRatios(input);
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	private static void calculateSumOfPartNumbers(List<char[]> input) {
		int sum = 0;
		for (int y = 0; y < input.size(); y++) {
			int numStart;
			int numEnd;
			char[] line = input.get(y);
			for (int x = 0; x < line.length; x++) {
				if (!isDigit(line[x])) {
					continue;
				}
				numStart = x;
				numEnd = getEndOfNum(x, line);
				int num = Integer.parseInt(new String(line, numStart, numEnd - numStart + 1));
				if ((numStart > 0) && (line[numStart - 1] != '.')) {
					sum += num;
				} else if ((numEnd < (line.length - 1)) && (line[numEnd + 1] != '.')) {
					sum += num;
				} else if ((y > 0) && Objects.nonNull(input.get(y - 1))
						&& lineHasSymbolAdjacentToNum(input.get(y - 1), numStart, numEnd)) {
					sum += num;
				} else if ((y < input.size() - 1) && Objects.nonNull(input.get(y + 1))
						&& lineHasSymbolAdjacentToNum(input.get(y + 1), numStart, numEnd)) {
					sum += num;
				}
				x = numEnd;
			}
		}
		System.out.println("Sum of part numbers: " + sum);
	}

	private static void calculateSumOfGearRatios(List<char[]> input) {
		int sum = 0;
		for (int y = 0; y < input.size(); y++) {
			char[] line = input.get(y);
			for (int x = 0; x < line.length; x++) {
				if (line[x] != '*') {
					continue;
				}
				List<Integer> nums = new LinkedList<>();
				if ((x > 0) && isDigit(line[x - 1])) {
					int start = getStartOfNum(x - 1, line);
					nums.add(Integer.valueOf(new String(line, start, x - start)));
				}
				if ((x < (line.length - 1)) && isDigit(line[x + 1])) {
					int end = getEndOfNum(x + 1, line);
					nums.add(Integer.valueOf(new String(line, x + 1, end - x)));
				}
				if (y > 0) {
					extractAdjacentNumberOnOtherLine(x, nums, input.get(y - 1));
				}
				if (y < (line.length - 1)) {
					extractAdjacentNumberOnOtherLine(x, nums, input.get(y + 1));
				}
				if (nums.size() == 2) {
					sum += nums.stream().mapToInt(Integer::intValue).reduce(Math::multiplyExact).getAsInt();
				}
			}
		}
		System.out.println("Sum of gear ratios: " + sum);
	}

	private static void extractAdjacentNumberOnOtherLine(int asteriskPosition, List<Integer> nums, char[] line) {
		if (isDigit(line[asteriskPosition])) {
			int start = getStartOfNum(asteriskPosition, line);
			int end = getEndOfNum(asteriskPosition, line);
			nums.add(Integer.valueOf(new String(line, start, end - start + 1)));
		} else {
			if (isDigit(line[asteriskPosition - 1])) {
				int start = getStartOfNum(asteriskPosition - 1, line);
				nums.add(Integer.valueOf(new String(line, start, asteriskPosition - start)));
			}
			if (isDigit(line[asteriskPosition + 1])) {
				int end = getEndOfNum(asteriskPosition + 1, line);
				nums.add(Integer.valueOf(new String(line, asteriskPosition + 1, end - asteriskPosition)));
			}
		}
	}

	private static boolean isDigit(char c) {
		return (('0' <= c) && (c <= '9'));
	}

	private static int getEndOfNum(int x, char[] line) {
		int tmp = x;
		while ((tmp < line.length) && isDigit(line[tmp])) {
			tmp++;
		}
		return tmp - 1;
	}

	private static int getStartOfNum(int x, char[] line) {
		int tmp = x;
		while ((tmp >= 0) && ('0' <= line[tmp]) && (line[tmp] <= '9')) {
			tmp--;
		}
		return tmp + 1;
	}

	private static boolean lineHasSymbolAdjacentToNum(char[] chars, int numStart, int numEnd) {
		int start = (numStart > 0) ? (numStart - 1) : 0;
		int end = (numEnd < chars.length - 1) ? (numEnd + 1) : (chars.length - 1);
		while (start <= end) {
			if (chars[start++] != '.') {
				return true;
			}
		}
		return false;
	}
}

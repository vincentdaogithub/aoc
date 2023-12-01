package com.vincentdao.aoc._2015;

import java.io.InputStream;
import java.util.Objects;

public final class _1 {

	public static void main(String[] args) {
		try (InputStream is = _1.class.getClassLoader().getResourceAsStream("2015/1.txt")) {
			if (Objects.isNull(is)) {
				System.err.println("Cannot get input file.");
				return;
			}
			int floor = 0;
			int c;
			int position = 1;
			boolean hasNotEnteredBasementBefore = true;
			while ((c = is.read()) > -1) {
				switch (c) {
					case '(':
						floor++;
						break;

					case ')':
						floor--;
						break;

					default:
						System.err.println("Unknown character.");
						return;
				}
				if (hasNotEnteredBasementBefore && (floor == -1)) {
					hasNotEnteredBasementBefore = false;
					System.out.println("First entry to basement at index: " + position);
				}
				position++;
			}
			System.out.println("Result: " + floor);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
}

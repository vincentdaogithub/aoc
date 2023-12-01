package com.vincentdao.aoc._2015;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("Duplicates")
public final class _3 {

	private record Coordinate(int x, int y) {
	}

	public static void main(String[] args) {
		try (InputStream is = _3.class.getClassLoader().getResourceAsStream("2015/3.txt")) {
			if (Objects.isNull(is)) {
				System.err.println("Cannot get input file.");
				return;
			}
			Set<Coordinate> visitedCoordinates = new HashSet<>();
			visitedCoordinates.add(new Coordinate(0, 0));
			int currentX = 0;
			int currentY = 0;
			int c;
			while ((c = is.read()) > -1) {
				switch (c) {
					case '^':
						currentY++;
						break;

					case 'v':
						currentY--;
						break;

					case '>':
						currentX++;
						break;

					case '<':
						currentX--;
						break;

					default:
						throw new IllegalArgumentException("Unknown input.");
				}
				visitedCoordinates.add(new Coordinate(currentX, currentY));
			}
			System.out.println("Number of visited houses: " + visitedCoordinates.size());
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		try (InputStream is = _3.class.getClassLoader().getResourceAsStream("2015/3.txt")) {
			if (Objects.isNull(is)) {
				System.err.println("Cannot get input file.");
				return;
			}
			Set<Coordinate> visitedCoordinates = new HashSet<>();
			visitedCoordinates.add(new Coordinate(0, 0));
			int currentSantaX = 0;
			int currentSantaY = 0;
			int currentRoboX = 0;
			int currentRoboY = 0;
			boolean santaTurn = true;
			int c;
			while ((c = is.read()) > -1) {
				switch (c) {
					case '^':
						if (santaTurn) {
							currentSantaY++;
						} else {
							currentRoboY++;
						}
						break;

					case 'v':
						if (santaTurn) {
							currentSantaY--;
						} else {
							currentRoboY--;
						}
						break;

					case '>':
						if (santaTurn) {
							currentSantaX++;
						} else {
							currentRoboX++;
						}
						break;

					case '<':
						if (santaTurn) {
							currentSantaX--;
						} else {
							currentRoboX--;
						}
						break;

					default:
						throw new IllegalArgumentException("Unknown input.");
				}
				if (santaTurn) {
					visitedCoordinates.add(new Coordinate(currentSantaX, currentSantaY));
				} else {
					visitedCoordinates.add(new Coordinate(currentRoboX, currentRoboY));
				}
				santaTurn = !santaTurn;
			}
			System.out.println("Number of visited houses with Robo: " + visitedCoordinates.size());
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
}

package com.vincentdao.aoc._2015;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class _4 {

	private static final String input = "ckczppom";

	public static void main(String[] args) throws NoSuchAlgorithmException {
		boolean startWithFiveZerosNotFound = true;
		for (int i = 0; true; i++) {
			String hashResult = getHash(input + i);
			if (startWithFiveZerosNotFound && hashResult.startsWith("00000")) {
				System.out.println("Lowest positive number (at least 5 zeros): " + i);
				startWithFiveZerosNotFound = false;
			}
			if (hashResult.startsWith("000000")) {
				System.out.println("Lowest positive number (at least 6 zeros): " + i);
				return;
			}
		}
	}

	private static String getHash(String input) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] hashResult = digest.digest(input.getBytes(StandardCharsets.UTF_8));
		StringBuilder builder = new StringBuilder();
		for (byte b : hashResult) {
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}
}

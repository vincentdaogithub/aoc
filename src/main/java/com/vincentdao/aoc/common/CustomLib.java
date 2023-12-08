package com.vincentdao.aoc.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomLib {

    public static long lcm(long a, long b) {
        return (Math.abs(a * b) / gcd(a, b));
    }

    public static long gcd(long a, long b) {
        return (a > b) ? gcdRecursive(a, b) : gcdRecursive(b, a);
    }

    private static long gcdRecursive(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcdRecursive(b, a % b);
    }
}

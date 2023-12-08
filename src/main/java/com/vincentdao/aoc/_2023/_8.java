package com.vincentdao.aoc._2023;

import com.vincentdao.aoc.common.CustomLib;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class _8 {

    private static final Pattern NODE_PATTERN = Pattern.compile("^(\\w+) = \\((\\w+), (\\w+)\\)$");

    public static void main(String[] args) {
        try (InputStream is = _8.class.getClassLoader().getResourceAsStream("2023/8.txt")) {
            if (Objects.isNull(is)) {
                System.err.println("Cannot load input file.");
                return;
            }
            char[] instruction;
            Map<String, String[]> nodes = new HashMap<>();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                instruction = br.readLine().toCharArray();
                String line;
                while (Objects.nonNull(line = br.readLine())) {
                    addNode(line, nodes);
                }
            }
            long totalStepsToReachZZZ = calculateSteps(instruction, nodes);
            long totalStepsToReachEndZFromEndA = calculateStepsFromEndAToEndZ(instruction, nodes);
            System.out.println("Total steps: " + totalStepsToReachZZZ);
            System.out.println("Total steps to reach nodes end with Z: " + totalStepsToReachEndZFromEndA);
        }  catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void addNode(String line, Map<String, String[]> nodes) {
        if (line.isBlank()) {
            return;
        }
        Matcher matcher = NODE_PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Unknown input.");
        }
        nodes.put(matcher.group(1), new String[] { matcher.group(2), matcher.group(3) });
    }

    private static long calculateSteps(char[] instruction, Map<String, String[]> nodes) {
        long steps = 0;
        boolean hasNotReachedZZZ = true;
        int index = 0;
        String traverseNode = "AAA";
        while (hasNotReachedZZZ) {
            if (index == instruction.length) {
                index = 0;
            }
            traverseNode = getNextNode(traverseNode, instruction[index], nodes);
            if ("ZZZ".equals(traverseNode)) {
                hasNotReachedZZZ = false;
            }
            steps++;
            index++;
        }
        return steps;
    }

    private static long calculateSteps(String startNode, char[] instruction, Map<String, String[]> nodes) {
        long steps = 0;
        boolean hasNotReachedZZZ = true;
        int index = 0;
        String traverseNode = startNode;
        while (hasNotReachedZZZ) {
            if (index == instruction.length) {
                index = 0;
            }
            traverseNode = getNextNode(traverseNode, instruction[index], nodes);
            if (traverseNode.endsWith("Z")) {
                hasNotReachedZZZ = false;
            }
            steps++;
            index++;
        }
        return steps;
    }

    private static long calculateStepsFromEndAToEndZ(char[] instruction, Map<String, String[]> nodes) {
        List<Long> totalStepsForNodesEndWithA = nodes.keySet().stream().parallel().filter(node -> node.endsWith("A"))
                .map(node -> calculateSteps(node, instruction, nodes)).toList();
        return totalStepsForNodesEndWithA.stream().reduce(CustomLib::lcm).orElseThrow();
    }

    private static String getNextNode(String traverseNode, char instruction, Map<String, String[]> nodes) {
        String[] nextNodes = nodes.get(traverseNode);
        return switch (instruction) {
            case 'L' -> nextNodes[0];
            case 'R' -> nextNodes[1];
            default -> throw new IllegalArgumentException("Unknown input.");
        };
    }
}

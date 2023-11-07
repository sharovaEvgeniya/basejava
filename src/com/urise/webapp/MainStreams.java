package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        int[] itemsFirst = new int[]{1, 2, 3, 3, 2, 3};
        int[] itemsSecond = new int[]{9, 8};

        List<Integer> integerListFirst = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> integerListSecond = Arrays.asList(2, 2, 4, 6, 1, 3, 8);

        System.out.println(minValue(itemsFirst));
        System.out.println(minValue(itemsSecond));
        System.out.println(oddOrEven(integerListFirst));
        System.out.println(oddOrEven(integerListSecond));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> 10 * a + b);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int del = integers
                .stream()
                .mapToInt(Integer::intValue)
                .sum() % 2;

        return integers
                .stream()
                .filter(elem -> elem % 2 != del)
                .collect(Collectors.toList());
    }
}

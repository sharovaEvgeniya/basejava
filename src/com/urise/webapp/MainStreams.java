package com.urise.webapp;

import java.util.Arrays;
import java.util.List;

public class MainStreams {
    public static void main(String[] args) {
        int[] itemsFirst = new int[]{1,2,3,3,2,3};
        int[] itemsSecond = new int[]{9,8};
        System.out.println(minValue(itemsFirst));
        System.out.println(minValue(itemsSecond));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted().reduce(0, (a, b) -> 10 * a + b);
    }
    private static List<Integer> oddOrEven(List<Integer> integers) {

    }
}

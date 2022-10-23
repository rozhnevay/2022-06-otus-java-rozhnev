package ru.otus.demo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        var len = scanner.nextInt();
        int[] arr = new int[len];
        for (var i = 0; i < len; i++) {
            arr[i] = scanner.nextInt();
        }
        int k = scanner.nextInt();
        Set<Integer> items = new HashSet<>();
        for (var i = 0; i < len; i++) {
            int neededItem = k - arr[i];
            if (items.contains(neededItem)) {
                System.out.println(arr[i] + " " + neededItem);
                return;
            }
            items.add(arr[i]);
        }
        System.out.println("None");
        int[] sortedArr = new int[5];
        sortedArr[0] = -2;
        ArrayList
    }

    private static void func() {

    }
}
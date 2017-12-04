package com.pokemon;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class utility {
    public static String getData(Scanner input, String helper, String error, String[] data) {
        HashSet<String> correctResponse = new HashSet<>(Arrays.asList(data));
        String response;

        while (true) {
            utility.delayPrint(helper);
            response = input.next();
            if (correctResponse.contains(response)) {
                return response;
            }

            utility.delayPrintln(error);
        }
    }

    public static int getRange(Scanner input, String error, String helper, int starting, int end) {
        String response;
        int selection;

        while (true) {
            utility.delayPrint(helper);
            response = input.next();

            if (response.matches("^[0-9]+$")) {
                selection = Integer.parseInt(response);
                if (selection >= starting && selection <= end) {
                    return selection;
                }
            }

            utility.delayPrintln(error);
        }
    }

    public static void delayPrintln(String toPrint) {
        delayPrint(toPrint + "\n", 10);
    }

    public static void delayPrintln(String toPrint, int time) {
        delayPrint(toPrint + "\n", time);
    }

    public static void delayPrint(String toPrint) {
        delayPrint(toPrint, 10);
    }

    public static void delayPrint(String toPrint, int time) {
        for (char c : toPrint.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(time);
            } catch (Exception e) {
            }
        }
    }

    public static void clearScreen() {
        for (int j = 0; j < 100; j++)
            System.out.println();
    }

}
package com.pokemon;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class getInput {
    public static String stringData(Scanner input, String helper, String error, String[] data) {
        HashSet<String> correctResponse = new HashSet<>(Arrays.asList(data));
        String response;

        while (true) {
            System.out.print(helper);
            response = input.next();
            if (correctResponse.contains(response)) {
                return response;
            }

            System.out.println(error);
        }
    }

    public static int range(Scanner input, String helper, String error, int starting, int end) {
        String response;
        int selection;

        while (true) {
            System.out.print(helper);
            response = input.next();

            if (!response.matches("^[0-9]+$")) {
                selection = Integer.parseInt(response);
                if (selection >= starting && selection <= end) {
                    return selection;
                }
            }

            System.out.println(error);
        }
    }


}
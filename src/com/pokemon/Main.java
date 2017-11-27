package com.pokemon;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Pokedex pokedex = new Pokedex();
        Scanner input = new Scanner(System.in);
        Random randNumber = new Random();
        int selection;

        System.out.println("╔═══╦══════════════════════╗\n" +
                String.format("║ 1 ║ %20s ║", "Multiplayer") + "\n" +
                String.format("║ 2 ║ %20s ║", "VS. AI") + "\n" +
                String.format("║ 3 ║ %20s ║", "Quit") + "\n" +
                "╚═══╩══════════════════════╝");

        while (true) {
            System.out.print(">>> ");
            selection = input.nextInt();

            if (selection == 1) {
                // Multiplayer stuff
                break;
            } else if (selection == 2) {
                System.out.println("Enter the number of battles (0 for random)");
                System.out.print(">>> ");
                selection = input.nextInt();

                if (selection == 0) {

                }
            } else if (selection == 3) {
                break;
            }
        }


    }
}

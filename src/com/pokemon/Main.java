package com.pokemon;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int selection, pokemonCounter = 0;
        String pokeSelect;
        ArrayList<Pokemon> selectedPokemons = new ArrayList<>();

        Pokedex pokedex = new Pokedex();
        Scanner input = new Scanner(System.in);


        utility.delayPrintln("╔═══╦══════════════════════╗\n" +
                String.format("║ 1 ║ %20s ║", "Multiplayer") + "\n" +
                String.format("║ 2 ║ %20s ║", "VS. AI") + "\n" +
                String.format("║ 3 ║ %20s ║", "Quit") + "\n" +
                "╚═══╩══════════════════════╝");

        selection = utility.getRange(input, "Error, please select a valid response", ">>> ", 1, 3);

        if (selection == 1) {
            // Multiplayer stuff
        } else if (selection == 2) {
            pokedex.printTable();

            while (pokemonCounter < 6) {
                pokeSelect = input.next();

                if (pokedex.contains(pokeSelect)){
                    utility.delayPrintln("");
                }
            }
        }
    }
}

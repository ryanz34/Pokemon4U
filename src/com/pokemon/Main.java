package com.pokemon;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int selection, pokemonCounter = 0;
        int pokeSelect;
        String confirmation;
        ArrayList<Integer> selectedPokemons = new ArrayList<>();

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

            while (pokemonCounter < 6) {
                pokedex.printTable();

                utility.delayPrintln("Please select a pokemon");
                pokeSelect = utility.getRange(input, "Error, please enter a valid Pokemon number.", ">>>", 1, 151);
                confirmation = utility.stringData(input, "You have chosen " + pokedex.getPokemon(pokeSelect).getName() + ">>>", "Invalid selection.", new String[]{"y", "n"});

                if (confirmation.equals("y") && !selectedPokemons.contains(pokeSelect)) {
                    selectedPokemons.add(pokeSelect);
                    pokemonCounter += 1;
                    utility.clearScreen();

                    utility.delayPrintln(pokedex.getPokemon(pokeSelect).getArt(), 1);

                    utility.delayPrintln(pokedex.getPokemon(pokeSelect).getName() + " has been added to your team.");
                }
            }

            System.out.println(selectedPokemons);
        }
    }
}

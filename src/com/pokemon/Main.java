package com.pokemon;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int selection, pokemonCounter = 0;
        int pokeSelect;
        Pokemon selectedPokemon;
        String confirmation;
        ArrayList<Integer> selectedPokemons = new ArrayList<>();
        boolean playerFirst;

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

                if (pokemonCounter > 0) {
                    utility.delayPrint("Your team currently has: ");
                    for (int n : selectedPokemons) {
                        utility.delayPrint(pokedex.getPokemon(n).getName() + " ");
                    }
                    System.out.println();
                }


                pokeSelect = utility.getRange(input, "Error, please enter a valid Pokemon number.", "Please select a pokemon >>> ", 1, 151);
                selectedPokemon = pokedex.getPokemon(pokeSelect);

                utility.clearScreen();
                System.out.println(pokedex.getPokemon(pokeSelect).getArt());

                utility.delayPrintln(String.format("║ %-20s ║ %-10s ║ %-10s ║ %-20s ║", "Attack", "EC", "DMG", "Special"), 1);
                utility.delayPrintln("╠══════════════════════╬════════════╬════════════╬══════════════════════╣");

                for (Attack a : selectedPokemon.getAttack()) {
                    utility.delayPrintln(a.toString(), 1);
                }

                confirmation = utility.getData(input, "You have chosen " + selectedPokemon.getName() + "(y/n) >>> ", "Invalid selection.", new String[]{"y", "n"});

                if (confirmation.equals("y") && !selectedPokemons.contains(pokeSelect)) {
                    selectedPokemons.add(pokeSelect);
                    pokemonCounter += 1;
                    utility.delayPrintln(selectedPokemon.getName() + " has been added to your team.");
                    utility.delayPrint("Press enter to continue...");
                    try {System.in.read();} catch (Exception e){}

                } else if (selectedPokemons.contains(pokeSelect)) {
                    utility.delayPrintln(selectedPokemon.getName() + " is already on your team.");
                    utility.delayPrint("Press enter to continue...");
                    try {System.in.read();} catch (Exception e){}
                }

                System.out.println(confirmation);
            }

            utility.delayPrintln("Battle Starting");
            utility.clearScreen();

            battle h = new battle(selectedPokemons, pokedex);
            h.startBattle();

        }
    }
}

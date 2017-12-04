package com.pokemon;

import com.pokemon.AttackPokemon;
import com.pokemon.Pokedex;
import com.pokemon.*;
import com.pokemon.utility;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class battle {
    private Random rNumber = new Random();
    private boolean playerFirst;
    private Scanner input = new Scanner(System.in);
    private ArrayList<AttackPokemon> playerPokemons = new ArrayList<>();
    private AttackPokemon pokemonSelected;
    private AttackPokemon enemyPokemon;
    private ArrayList<Integer> computerPokemon = new ArrayList<>();
    private Pokedex pokeLibrary;

    public battle (ArrayList<Integer> selectedPokemons, Pokedex library) {
        this.pokeLibrary = library;

        for (int p : selectedPokemons) {
            playerPokemons.add(new AttackPokemon(library.getPokemon(p)));
        }

        for (Map.Entry<Integer, Pokemon> entry: library.getPokeDex().entrySet()) {
            if (!selectedPokemons.contains(entry.getKey())) {
                computerPokemon.add(entry.getKey());
            }
        }
    }

    public boolean selectPokemon () {
        int counter = 0;
        int pokeNumber;

        utility.delayPrintln("Select a Pokemon");

        System.out.printf("║ %s ║ %-20s ║ %-5s ║ %-5s ║ %-20s ║ %-20s ║ %-20s ║ %-10s ║ %-10s ║\n", "#", "Name", "HP", "EC", "Type", "Resistance", "Weakness", "Stunned", "Disabled");
        System.out.println("╠═══╬══════════════════════╬═══════╬═══════╬══════════════════════╬══════════════════════╬══════════════════════╬════════════╬════════════╣");

        for (AttackPokemon p : playerPokemons) {
            if (p.isAlive()) {
                counter ++;
                System.out.println(String.format("║ %d ", counter) + p);
            }
        }
        if (pokemonSelected == null) {
           pokeNumber = utility.getRange(input, "Please select a valid pokemon", "Select your pokemon >>> ", 1, counter);
        } else {
            pokeNumber = utility.getRange(input, "Please select a valid pokemon", "Select your pokemon (0 to exit) >>> ", 0, counter);
            if (pokeNumber == 0) {
                return false;
            }
        }
        pokemonSelected = playerPokemons.get(pokeNumber-1);
        utility.clearScreen();
        utility.delayPrintln(pokemonSelected.getName() + ", I choose you");
        utility.delayPrint("Press enter to continue...");
        try {System.in.read();} catch (Exception e){}
        utility.clearScreen();
        return true;
    }

    public void computerAction () {
        enemyPokemon.Attack(pokemonSelected);
    }

    public void playerAction () {
        int option;

        System.out.println(pokemonSelected.getArt());
        System.out.println("Current status:");
        System.out.printf("EC: %5d Health: %5d Status: %s\n", pokemonSelected.getEC(), pokemonSelected.getHp(), pokemonSelected.getStatus());

        utility.delayPrintln("╔═══╦══════════════════════╗\n" +
                String.format("║ 1 ║ %20s ║", "Attack") + "\n" +
                String.format("║ 2 ║ %20s ║", "Retreat") + "\n" +
                String.format("║ 3 ║ %20s ║", "Pass") + "\n" +
                "╚═══╩══════════════════════╝");

        while (true) {
            option = utility.getRange(input, "Please select a valid option", "Select your Action >>> ", 1, 3);
            if (option == 1) {
                if (pokemonSelected.isStunned()) {
                    utility.delayPrintln("Cannot attack, pokemon is stunned.");
                } else {
                    if (pokemonSelected.Attack(enemyPokemon)) {
                        break;
                    }
                }
            } else if (option == 2) {
                if(selectPokemon()) {
                    break;
                }
            } else if (option == 3) {
                break;
            }
        }
    }

    public void startBattle (){
        int cPokemon;
        while (computerPokemon.size() != 0) {
            selectPokemon();

            cPokemon = rNumber.nextInt(computerPokemon.size());
            enemyPokemon = new AttackPokemon(pokeLibrary.getPokemon(cPokemon));
            computerPokemon.remove(cPokemon);

            while (enemyPokemon.isAlive() && playerPokemons.size() != 0) {
                utility.clearScreen();
                utility.delayPrintln("Flipping coin...");
                if (rNumber.nextInt(2) == 1) {
                    playerFirst = true;
                    utility.delayPrintln("Player goes FIRST.");
                    playerAction();
                    computerAction();
                } else {
                    playerFirst = false;
                    utility.delayPrintln("Player goes SECOND");
                    computerAction();
                    playerAction();
                }
            }
        }
    }
}

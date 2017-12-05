
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int selection, pokemonCounter = 0;
        int pokeSelect;
        Pokemon selectedPokemon;
        String confirmation;
        ArrayList<Integer> selectedPokemons = new ArrayList<>();

        Pokedex pokedex = new Pokedex();

        pokeTools.mainMenu();

        pokeTools.delayPrintln("╔═══╦══════════════════════╗\n" +
                String.format("║ 1 ║ %20s ║", "Multiplayer") + "\n" +
                String.format("║ 2 ║ %20s ║", "VS. AI") + "\n" +
                String.format("║ 3 ║ %20s ║", "Quit") + "\n" +
                "╚═══╩══════════════════════╝");

        selection = pokeTools.getRange("Error, please select a valid response", ">>> ", 1, 3);

        if (selection == 1) {
            // Multiplayer stuff
        } else if (selection == 2) {

            while (pokemonCounter < 6) {
                pokedex.printTable();

                if (pokemonCounter > 0) {
                    pokeTools.delayPrint("Your team currently has: ");
                    for (int n : selectedPokemons) {
                        pokeTools.delayPrint(pokedex.getPokemon(n).getName() + " ");
                    }
                    System.out.println();
                }


                pokeSelect = pokeTools.getRange("Error, please enter a valid Pokemon number.", "Please select a pokemon >>> ", 1, 151);
                selectedPokemon = pokedex.getPokemon(pokeSelect);

                pokeTools.clearScreen();
                pokeTools.delayPrintTable(pokedex.getPokemon(pokeSelect).getArt());

                if (selectedPokemons.contains(pokeSelect)) {
                    pokeTools.delayPrintln(selectedPokemon.getName() + " is already on your team.");
                    pokeTools.delayPrint("Press enter to continue...");
                    try {System.in.read();} catch (Exception e){}
                } else {

                    pokeTools.delayPrintTable(String.format("║ %-20s ║ %-10s ║ %-10s ║ %-20s ║", "Attack", "EC", "DMG", "Special"));
                    pokeTools.delayPrintTable("╠══════════════════════╬════════════╬════════════╬══════════════════════╣");

                    for (Attack a : selectedPokemon.getAttack()) {
                        pokeTools.delayPrintTable(a.toString());
                    }

                    confirmation = pokeTools.getData("You have chosen " + selectedPokemon.getName() + "(y/n) >>> ", "Invalid selection.", new String[]{"y", "n"});

                    if (confirmation.equals("y") && !selectedPokemons.contains(pokeSelect)) {
                        selectedPokemons.add(pokeSelect);
                        pokemonCounter += 1;
                        pokeTools.delayPrintln(selectedPokemon.getName() + " has been added to your team.");
                        pokeTools.delayPrint("Press enter to continue...");
                        try {System.in.read();} catch (Exception e) {}

                    }

                    pokeTools.delayPrintln(confirmation);
                }
            }

            pokeTools.delayPrintln("Battle Starting");
            pokeTools.clearScreen();

            battle h = new battle(selectedPokemons, pokedex);
            h.startBattle();

        }
    }
}

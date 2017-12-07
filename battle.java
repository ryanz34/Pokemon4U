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

        pokeTools.delayPrintln("Select a Pokemon");

        pokeTools.delayPrintTable(String.format("║ %s ║ %-20s ║ %-5s ║ %-5s ║ %-20s ║ %-20s ║ %-20s ║ %-10s ║ %-10s ║", "#", "Name", "HP", "EC", "Type", "Resistance", "Weakness", "Stunned", "Disabled"));
        pokeTools.delayPrintTable("╠═══╬══════════════════════╬═══════╬═══════╬══════════════════════╬══════════════════════╬══════════════════════╬════════════╬════════════╣");

        for (AttackPokemon p : playerPokemons) {
            if (p.isAlive()) {
                counter ++;
                pokeTools.delayPrintTable(String.format("║ %d ", counter) + p);
            }
        }
        if (pokemonSelected == null) {
           pokeNumber = pokeTools.getRange("Please select a valid pokemon", "Select your pokemon >>> ", 1, counter);
        } else {
            pokeNumber = pokeTools.getRange("Please select a valid pokemon", "Select your pokemon (0 to exit) >>> ", 0, counter);
            playerPokemons.add(pokemonSelected);
            if (pokeNumber == 0) {
                return false;
            }
        }
        pokemonSelected = playerPokemons.get(pokeNumber-1);
        playerPokemons.remove(pokeNumber - 1);

        pokeTools.clearScreen();
        pokeTools.delayPrintln(pokemonSelected.getName() + ", I choose you");
        pokeTools.delayPrint("Press enter to continue...");
        try {System.in.read();} catch (Exception e){}
        pokeTools.clearScreen();
        return true;
    }

    public void computerAction () {
        ArrayList<Attack> canPerform = new ArrayList<>();

        pokeTools.delayPrintTable(enemyPokemon.getArt() + enemyPokemon.getName());

        for (Attack e: enemyPokemon.getAttacks()) {
            if (e.getEc() <= enemyPokemon.getEC()) {
                canPerform.add(e);
            }
        }

        if (canPerform.size() > 0) {
            enemyPokemon.Attack(canPerform.get(pokeTools.randint(0, canPerform.size() - 1)), pokemonSelected);
        }

        try {System.in.read();} catch (Exception e){}
    }

    public void playerAction () {
        int option;

        pokeTools.delayPrintTable(pokemonSelected.getArt());
        pokeTools.delayPrintTable("Current status:");
        pokeTools.delayPrintln(String.format("EC: %9d \nHealth: %5d\nStatus: %s\n", pokemonSelected.getEC(), pokemonSelected.getHp(), pokemonSelected.getStatus()));

        pokeTools.delayPrintTable("╔═══╦══════════════════════╗\n" +
                String.format("║ 1 ║ %20s ║", "Attack") + "\n" +
                String.format("║ 2 ║ %20s ║", "Retreat") + "\n" +
                String.format("║ 3 ║ %20s ║", "Pass") + "\n" +
                "╚═══╩══════════════════════╝");

        while (true) {
            option = pokeTools.getRange("Please select a valid option", "Select your Action >>> ", 1, 3);
            if (option == 1) {
                if (pokemonSelected.isStunned()) {
                    pokeTools.delayPrintln("Cannot attack, pokemon is stunned.");
                } else {
                    if (pokemonSelected.playerAttack(enemyPokemon)) {
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

    public void roundEnd () {
        for (AttackPokemon p : playerPokemons) {
            p.roundEnd();
        }

        pokemonSelected.roundEnd();

        enemyPokemon.roundEnd();
    }

    public void startBattle (){
        int cPokemon;
        selectPokemon();

        while (computerPokemon.size() != 0 && playerPokemons.size() != 0) {
            cPokemon = rNumber.nextInt(computerPokemon.size());
            enemyPokemon = new AttackPokemon(pokeLibrary.getPokemon(cPokemon));
            computerPokemon.remove(cPokemon);

            while (enemyPokemon.isAlive() && playerPokemons.size() != 0) {
                pokeTools.clearScreen();
                pokeTools.delayPrintln("Flipping coin...");
                if (rNumber.nextInt(2) == 1) {
                    playerFirst = true;
                    pokeTools.delayPrintln("Player goes FIRST.");
                    playerAction();

                    if (!enemyPokemon.isAlive()) {
                        break;
                    }

                    for (AttackPokemon p : playerPokemons) {
                        p.turnDone();
                    }
                    pokemonSelected.turnDone();

                    computerAction();

                    if (!pokemonSelected.isAlive() && playerPokemons.size() != 0) {
                        pokemonSelected = null;
                        selectPokemon();

                    } else if (playerPokemons.size() == 0) {
                        break;
                    }

                    enemyPokemon.turnDone();

                } else {
                    playerFirst = false;
                    pokeTools.delayPrintln("Player goes SECOND");
                    computerAction();

                    if (!pokemonSelected.isAlive() && playerPokemons.size() != 0) {
                        pokemonSelected = null;
                        selectPokemon();

                    } else if (playerPokemons.size() == 0) {
                        break;
                    }

                    playerAction();

                    if (!enemyPokemon.isAlive()) {
                        break;
                    }
                }

                roundEnd();
            }
        }
    }
}

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class PokemonArena {
    private static String name;
    private static final Pokedex pokedex = new Pokedex();
    private static String enemyname;

    private Random rNumber = new Random();
    private ArrayList<AttackPokemon> playerPokemons = new ArrayList<>();
    private AttackPokemon pokemonSelected;
    private AttackPokemon enemySelected;
    private ArrayList<Integer> enemyPokemon = new ArrayList<>();


    public static void main(String[] args) {
        int selection;
        String[] userSelectedNames;
        ArrayList<Integer> selectedPokemons;

        pokeTools.mainMenu();
        userSelectedNames = pokeTools.intro(pokedex.getPokemon(33).getArt());

        name = userSelectedNames[0];
        enemyname = userSelectedNames[1];

        while (true) {
            pokeTools.delayPrintln("╔═══╦══════════════════════╗\n" +
                    String.format("║ 1 ║ %20s ║", "4 VS. 4") + "\n" +
                    String.format("║ 2 ║ %20s ║", "6 VS. 6") + "\n" +
                    String.format("║ 3 ║ %20s ║", "Quit") + "\n" +
                    "╚═══╩══════════════════════╝");

            selection = pokeTools.getRange("Error, please select a valid response", ">>> ", 1, 3);

            if (selection == 1) {
                selectedPokemons = pokeTools.selectPokemon(pokedex, 4);
                pokeTools.displaySelected(pokedex, selectedPokemons);
                PokemonArena battle = new PokemonArena(selectedPokemons);
                battle.startBattle();
            } if (selection == 2) {
                selectedPokemons = pokeTools.selectPokemon(pokedex, 6);
                pokeTools.displaySelected(pokedex, selectedPokemons);
                PokemonArena battle = new PokemonArena(selectedPokemons);
                battle.startBattle();
            } else {
                break;
            }
        }
    }

    public PokemonArena(ArrayList<Integer> selectedPokemons) {
        ArrayList<Integer> unselected_pokemon = new ArrayList<>();
        int computer_selected;


        for (int p : selectedPokemons) {
            playerPokemons.add(new AttackPokemon(pokedex.getPokemon(p)));
        }

        for (Map.Entry<Integer, Pokemon> entry : pokedex.getPokeDex().entrySet()) {
            if (!selectedPokemons.contains(entry.getKey())) {
                unselected_pokemon.add(entry.getKey());
            }
        }

        for (int i = 0; i < 6; i++) {
            computer_selected = rNumber.nextInt(unselected_pokemon.size());

            enemyPokemon.add(unselected_pokemon.get(computer_selected));
            System.out.println(enemyPokemon);
            System.out.println(unselected_pokemon.get(computer_selected));
            System.out.println(computer_selected);
            unselected_pokemon.remove(computer_selected);
        }
    }

    public boolean selectPokemon() {
        int counter = 0;
        int pokeNumber;

        pokeTools.clearScreen();
        pokeTools.delayPrintln("Select a Pokemon\n");

        pokeTools.delayPrintTable(String.format("║ %s ║ %-20s ║ %-5s ║ %-5s ║ %-20s ║ %-20s ║ %-20s ║ %-10s ║ %-10s ║", "#", "Name", "HP", "EC", "Type", "Resistance", "Weakness", "Stunned", "Disabled"));
        pokeTools.delayPrintTable("╠═══╬══════════════════════╬═══════╬═══════╬══════════════════════╬══════════════════════╬══════════════════════╬════════════╬════════════╣");

        for (AttackPokemon p : playerPokemons) {
            if (p.isAlive()) {
                counter++;
                pokeTools.delayPrintTable(String.format("║ %d ", counter) + p);
            }
        }

        System.out.println();

        if (pokemonSelected == null) {
            pokeNumber = pokeTools.getRange("Please select a valid pokemon", "Select your pokemon >>> ", 1, counter);
        } else {
            pokeNumber = pokeTools.getRange("Please select a valid pokemon", "Select your pokemon (0 to exit) >>> ", 0, counter);
            playerPokemons.add(pokemonSelected);
            if (pokeNumber == 0) {
                return false;
            }
        }
        pokemonSelected = playerPokemons.get(pokeNumber - 1);
        playerPokemons.remove(pokeNumber - 1);

        pokeTools.clearScreen();
        pokeTools.delayPrintln(name + ": " + pokemonSelected.getName() + ", I choose you");
        pokeTools.delayPrint("Press enter to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
        pokeTools.clearScreen();
        return true;
    }

    public void computerAction() {
        if (!enemySelected.isStunned()) {
            ArrayList<Attack> canPerform = new ArrayList<>();

            pokeTools.delayPrintTable(enemySelected.getArt());

            for (Attack e : enemySelected.getAttacks()) {
                if (e.getEc() <= enemySelected.getEC()) {
                    canPerform.add(e);
                }
            }

            if (canPerform.size() > 0) {
                enemySelected.Attack(canPerform.get(pokeTools.randint(0, canPerform.size() - 1)), pokemonSelected, enemyname);
            } else {
                pokeTools.pause();
            }
        } else {
            pokeTools.delayPrintTable(enemySelected.getArt());

            pokeTools.delayPrintln(enemySelected.getName() + " is stunned. Cannot attack this round");
        }

    }

    public void playerAction() {
        int option;

        if (pokemonSelected.isStunned()) {
            pokeTools.clearScreen();
            pokeTools.delayPrintTable(pokemonSelected.getArt());
            pokeTools.delayPrintln("Cannot attack, pokemon is stunned.");
            pokeTools.pause();

        } else {

            while (true) {
                pokeTools.clearScreen();
                pokeTools.delayPrintTable(pokemonSelected.getArt());
                pokeTools.delayPrintTable("Current status:");
                pokeTools.delayPrintln(String.format("EC: %9d \nHealth: %5d\nStatus: %s\n", pokemonSelected.getEC(), pokemonSelected.getHp(), pokemonSelected.getStatus()));

                pokeTools.delayPrintTable("╔═══╦══════════════════════╗\n" +
                        String.format("║ 1 ║ %20s ║", "Attack") + "\n" +
                        String.format("║ 2 ║ %20s ║", "Retreat") + "\n" +
                        String.format("║ 3 ║ %20s ║", "Pass") + "\n" +
                        "╚═══╩══════════════════════╝");

                option = pokeTools.getRange("Please select a valid option", "Select your Action >>> ", 1, 3);
                if (option == 1 && pokemonSelected.playerAttack(enemySelected, name)) {
                    break;

                } else if (option == 2) {
                    if (selectPokemon()) {
                        break;
                    }
                } else if (option == 3) {
                    break;
                }
            }
        }
    }

    public void roundEnd() {
        for (AttackPokemon p : playerPokemons) {
            p.roundEnd();
        }

        pokemonSelected.roundEnd();

        enemySelected.roundEnd();
    }

    public void startBattle() {
        int cPokemon;
        selectPokemon();

        while (enemyPokemon.size() != 0 && playerPokemons.size() != 0) {
            cPokemon = rNumber.nextInt(enemyPokemon.size());
            enemySelected = new AttackPokemon(pokedex.getPokemon(enemyPokemon.get(cPokemon)));
            enemyPokemon.remove(cPokemon);
            pokeTools.clearScreen();
            pokeTools.delayPrintln(enemyname + ": " + enemySelected.getName() + ", I choose you");
            pokeTools.pause();

            while (enemySelected.isAlive() && playerPokemons.size() != 0) {
                pokeTools.clearScreen();
                pokeTools.delayPrintln("Flipping coin...");
                if (rNumber.nextInt(2) == 1) {
                    pokeTools.delayPrintln("Player goes FIRST.");
                    playerAction();

                    if (!enemySelected.isAlive()) {
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

                    } else if (!pokemonSelected.isAlive() && playerPokemons.size() == 0) {
                        break;
                    }

                    enemySelected.turnDone();

                } else {
                    pokeTools.delayPrintln("Player goes SECOND");
                    computerAction();

                    if (!pokemonSelected.isAlive() && playerPokemons.size() != 0) {
                        pokemonSelected = null;
                        selectPokemon();

                    } else if (!pokemonSelected.isAlive() && playerPokemons.size() == 0) {
                        break;
                    }

                    enemySelected.turnDone();

                    playerAction();

                    for (AttackPokemon p : playerPokemons) {
                        p.turnDone();
                    }
                    pokemonSelected.turnDone();

                    if (!enemySelected.isAlive()) {
                        break;
                    }
                }

                roundEnd();
            }
        }

        if (enemySelected.getHp() <= 0 && enemyPokemon.size() == 0) {
            pokeTools.win();
        } else {
            pokeTools.lose();
        }

        pokeTools.clearScreen();
    }
}

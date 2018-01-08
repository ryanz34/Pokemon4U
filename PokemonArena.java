import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class PokemonArena {
    private static String name;  // The name of the player
    private static String enemyname;  // Enemy name
    private static final Pokedex pokedex = new Pokedex();  // A pokedex that contains all the Pokemons. Final does not allow it to be changed

    private Random rNumber = new Random();  // To generate random numbers easier
    private ArrayList<AttackPokemon> playerPokemons = new ArrayList<>();  // A list of the player's pokemons. Contains the AttackPokemon object
    private ArrayList<Integer> enemyPokemon = new ArrayList<>();  // The list of enemy pokemons to battle
    private AttackPokemon pokemonSelected;  // The enemy pokemon that is active
    private AttackPokemon enemySelected;  // The player's pokemon that is currently in battle


    public static void main(String[] args) {
        int selection; // The option that the user selects
        String[] userSelectedNames;  // The names returned from the introduction
        ArrayList<Integer> selectedPokemons;  // The pokemons selected by the player

        pokeTools.mainMenu();  // Display the main menu
        userSelectedNames = pokeTools.intro(pokedex.getPokemon(33).getArt());  // Displays the introduction with Oak and gets the names that the player choose

        name = userSelectedNames[0];  // Setting the name of the player
        enemyname = userSelectedNames[1];  // Setting the name of the enemy

        while (true) {  // While true loop allows for multiple battles without re running the program
            pokeTools.delayPrintln("╔═══╦══════════════════════╗\n" +
                    String.format("║ 1 ║ %20s ║", "4 VS. 4") + "\n" +
                    String.format("║ 2 ║ %20s ║", "6 VS. 6") + "\n" +
                    String.format("║ 3 ║ %20s ║", "Quit") + "\n" +
                    "╚═══╩══════════════════════╝");  // The menu

            selection = pokeTools.getRange("Error, please select a valid response", ">>> ", 1, 3);  // Getting the input using a custom method to check if its valid

            if (selection == 1) {  // Checking if the user selected 1
                selectedPokemons = pokeTools.selectPokemon(pokedex, 4);  // Making the players choose 4 pokemons
                pokeTools.displaySelected(pokedex, selectedPokemons);  // Displaying the Pokemons
                PokemonArena battle = new PokemonArena(selectedPokemons, 4);  // Creating a new battle, used for fast resetting later
                battle.startBattle();  // Starts the battle
            } if (selection == 2) {  // Same thing as above but with 6 v 6 instead of 4v4
                selectedPokemons = pokeTools.selectPokemon(pokedex, 6);
                pokeTools.displaySelected(pokedex, selectedPokemons);
                PokemonArena battle = new PokemonArena(selectedPokemons, 6);
                battle.startBattle();
            } else {  // if quit is selected
                break;  // Break while loop and quit
            }
        }
    }

    public PokemonArena(ArrayList<Integer> selectedPokemons, int enemycount) {  // constructor
        ArrayList<Integer> unselected_pokemon = new ArrayList<>();  // Arraylist to keep track of which pokemons haven't been selected
        int computer_selected;  // Pokemon selected by the enemy


        for (int p : selectedPokemons) {  // iterating through all of the player's Pokemons
            playerPokemons.add(new AttackPokemon(pokedex.getPokemon(p)));  // Making attackpokemon objects for battling
        }

        for (Map.Entry<Integer, Pokemon> entry : pokedex.getPokeDex().entrySet()) {  // Iterating through the rest of the pokemons
            if (!selectedPokemons.contains(entry.getKey())) {  // See if the user selected this pokemon
                unselected_pokemon.add(entry.getKey()); // Add to unselected_pokemon if the pokemon has not been used
            }
        }

        for (int i = 0; i < enemycount; i++) {  // Choosing the pokemon for the enemy
            computer_selected = rNumber.nextInt(unselected_pokemon.size());  // Selecting a pokemon using a random number generator

            enemyPokemon.add(unselected_pokemon.get(computer_selected));  // Add the pokemon selected to the enemy's pokemons
            unselected_pokemon.remove(computer_selected);  // Remove from the unselected pokemons
        }
    }

    /**
     * Allows the player to select pokemons
     *
     * @return if the action has been performed, the function returns true, else, false, to allow the user to select another choice
     */

    public boolean selectPokemon() {
        if (playerPokemons.size() == 0) { // the number of pokemons that the player have in queue is 0, then the player cannot retreat/ select pokemon
            pokeTools.delayPrintln("Cannot retreat, no Pokemons available to switch to.");
            return false;
        }
        int counter = 0; // a counter to keep print numbers beside the pokemon for selecting
        int pokeNumber;  // The pokemon selected

        pokeTools.clearScreen();  // clearing the screen and printing out the column headings
        pokeTools.delayPrintln("Select a Pokemon\n");

        pokeTools.delayPrintTable(String.format("║ %s ║ %-20s ║ %-5s ║ %-5s ║ %-20s ║ %-20s ║ %-20s ║ %-10s ║ %-10s ║", "#", "Name", "HP", "EC", "Type", "Resistance", "Weakness", "Stunned", "Disabled"));
        pokeTools.delayPrintTable("╠═══╬══════════════════════╬═══════╬═══════╬══════════════════════╬══════════════════════╬══════════════════════╬════════════╬════════════╣");

        for (AttackPokemon p : playerPokemons) {  // Iterating through all of the player's pokemons to be selected
            if (p.isAlive()) {  // if the pokemon is alive
                counter++;  // Increase the counter so that each pokemon has a different number
                pokeTools.delayPrintTable(String.format("║ %d ", counter) + p);  // prints the table along with the counter
            }
        }

        System.out.println();  // Empty line to make the selection look better

        if (pokemonSelected == null) {  // If the player does not have an active pokemon, then the player must select a pokemon
            pokeNumber = pokeTools.getRange("Please select a valid pokemon", "Select your pokemon >>> ", 1, counter);
        } else {  // If the player has a pokemon active, then the player can select or return to the previous screen
            pokeNumber = pokeTools.getRange("Please select a valid pokemon", "Select your pokemon (0 to exit) >>> ", 0, counter);
            if (pokeNumber == 0) {  // Quit
                return false;
            }
            playerPokemons.add(pokemonSelected);  // Add the active pokemon back into the player's queue
        }
        pokemonSelected = playerPokemons.get(pokeNumber - 1);  // Gets the pokemon
        playerPokemons.remove(pokeNumber - 1); // Remove the active pokemon from the queue

        pokeTools.clearScreen();  // clear the screen
        pokeTools.delayPrintln(name + ": " + pokemonSelected.getName() + ", I choose you");  // print the I choose you message
        pokeTools.pause();  // Pause/press any key to continue
        pokeTools.clearScreen();  // Clearing the screen again so that it is ready for the next action
        return true;  // Action performed
    }

    /**
     * Generates the action that the computer performs
     */
    public void computerAction() {
        if (!enemySelected.isStunned()) {  // Checking if its stunned
            ArrayList<Attack> canPerform = new ArrayList<>();  // A list that stores the attacks that can be performed

            pokeTools.delayPrintTable(enemySelected.getArt());  // Print the art for that pokemon

            for (Attack e : enemySelected.getAttacks()) {  // Iterating through all the attacks of the enemy pokemon, then if can be performed, then put in the list
                if (e.getEc() <= enemySelected.getEC()) {
                    canPerform.add(e);
                }
            }

            if (canPerform.size() > 0) { // If the computer can perform an attack, choose a random one and attack the user
                enemySelected.Attack(canPerform.get(pokeTools.randint(0, canPerform.size() - 1)), pokemonSelected, enemyname);
            } else { // If the computer cannot attack, then pass the turn
                pokeTools.delayPrintln(enemySelected.getName() + " is out of energy.");
                pokeTools.pause();
            }
        } else {
            pokeTools.delayPrintTable(enemySelected.getArt());  // Gets the art

            pokeTools.delayPrintln(enemySelected.getName() + " is stunned. Cannot attack this round");  // Gives the player the reason for the enemy not attacking
            pokeTools.pause();
        }

    }

    /**
     * The player's actions. This is mainly just a menu for selecting the actions
     */

    public void playerAction() {
        int option;  // The option that the user selected

        if (pokemonSelected.isStunned()) {  // If stunned then the pokemon skips the turn
            pokeTools.clearScreen();
            pokeTools.delayPrintTable(pokemonSelected.getArt());
            pokeTools.delayPrintln("Cannot attack, pokemon is stunned.");
            pokeTools.pause();

        } else {  // If not stunned

            while (true) {  // While the user has not selected a move
                pokeTools.clearScreen(); // Prints the current status along with the art and the choices for moves
                pokeTools.delayPrintTable(pokemonSelected.getArt());
                pokeTools.delayPrintTable("Current status:");
                pokeTools.delayPrintln(String.format("Energy: %5d \nHealth: %5d\nStatus: %s\n", pokemonSelected.getEC(), pokemonSelected.getHp(), pokemonSelected.getStatus()));

                pokeTools.delayPrintTable("╔═══╦══════════════════════╗\n" +
                        String.format("║ 1 ║ %20s ║", "Attack") + "\n" +
                        String.format("║ 2 ║ %20s ║", "Retreat") + "\n" +
                        String.format("║ 3 ║ %20s ║", "Pass") + "\n" +
                        "╚═══╩══════════════════════╝");

                option = pokeTools.getRange("Please select a valid option", "Select your Action >>> ", 1, 3); // gets the input
                if (option == 1 && pokemonSelected.playerAttack(enemySelected, name)) {  // check if the option is 1 and if the player actually attacked
                    break;  // action performed

                } else if (option == 2) {  // If the player wants to retreat
                    if (selectPokemon()) {  // Checking if the player retreated or not
                        break;
                    }
                } else if (option == 3) {  // Just pass the turn
                    break;
                }
            }  // If no moves are selected, then reprint everything to make the user select again
        }
    }

    /**
     * runs after the round ends, restores 20EC for all player pokemons and enemy active pokemon
     */

    public void roundEnd() {
        for (AttackPokemon p : playerPokemons) {  // Iterating through and running the roundEnd method for all pokemons
            p.roundEnd();  // Adds the EC
        }

        pokemonSelected.roundEnd();  // Adding EC to all the active pokemons

        enemySelected.roundEnd();
    }

    /**
     * Starting the battle
     */

    public void startBattle() {
        int cPokemon;  // the pokemon that the computer chooses
        selectPokemon();  // Makes the user select a pokemon for be first in battle

        while (enemyPokemon.size() != 0) {  // Checking if a winner can be determined
            cPokemon = rNumber.nextInt(enemyPokemon.size());  // Enemy chooses a random pokemon
            enemySelected = new AttackPokemon(pokedex.getPokemon(enemyPokemon.get(cPokemon)));  // Creates a new attackpokemon object to fight with
            enemyPokemon.remove(cPokemon); // remove the pokemon for the list of pokemons to choose from
            pokeTools.clearScreen();
            pokeTools.delayPrintln(enemyname + ": " + enemySelected.getName() + ", I choose you");  // doing the I choose you message
            pokeTools.pause();

            while (enemySelected.isAlive()) {  // Checking if the enemy is still alive
                pokeTools.clearScreen();
                pokeTools.delayPrintln("Flipping coin...");  // Flips a coin to determine who goes first
                if (rNumber.nextInt(2) == 1) {  // Player goes first
                    pokeTools.delayPrintln("Player goes FIRST.");
                    playerAction();  // Makes the player chooses an action

                    if (!enemySelected.isAlive()) {  // Checking if the enemy pokemon is still alive
                        break;
                    }

                    for (AttackPokemon p : playerPokemons) {  // Running turn done to remove any stun effects after the turn passes
                        p.turnDone();
                    }
                    pokemonSelected.turnDone();

                    computerAction();  // Computer performs an action

                    if (!pokemonSelected.isAlive() && playerPokemons.size() != 0) {  // Checking if the user needs another pokemon
                        pokemonSelected = null;  // Make the pokemon that is Selected null
                        selectPokemon();  // Make player select a pokemon

                    } else if (!pokemonSelected.isAlive() && playerPokemons.size() == 0) { // If the player is defeated
                        break;
                    }

                    enemySelected.turnDone();  // Player's turn is done

                } else {  // Same as about except the player goes second
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

                roundEnd();  // Adding ec to the pokemon after both sides have done something
            }
        }

        if (!enemySelected.isAlive() && enemyPokemon.size() == 0) {  // Checking if all the enemy's pokemons are defeated
            pokeTools.win(); // Display the win screen
        } else {  // Display the lose screen
            pokeTools.lose();
        }

        pokeTools.clearScreen();  // Clear the screen for the next battle
    }
}

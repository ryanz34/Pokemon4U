import java.util.*;

/**
 * Some useful methods that are used in the project
 */
public class pokeTools {
    private static Scanner input = new Scanner(System.in);  // the Scanner for input
    private static Random rNumber = new Random();

    /**
     * Getting Data from user, but limiting responses
     *
     * @param helper what to print as hint
     * @param error the error if the user enters a unknown input
     * @param data the valid inputs
     * @return a valid response
     */

    public static String getData(String helper, String error, String[] data) {
        HashSet<String> correctResponse = new HashSet<>(Arrays.asList(data));  // Making a hashset for easier comparison
        String response;  // the response from the user

        while (true) {  // While the user has not entered a valid response
            delayPrint(helper);  // Prints the helper
            response = input.next();  // gets input
            if (correctResponse.contains(response)) {  // If the input is valid then return the input
                return response;
            }

            delayPrintln(error);
        }
    }

    /**
     * Same as getData but with range of number
     *
     * @param helper what to print as hint
     * @param error the error if the user enters a unknown input
     * @param starting the start for the range, inclusive
     * @param end the end for the range, inclusive
     * @return A valid response (Integer)
     */

    public static int getRange(String error, String helper, int starting, int end) {
        String response;
        int selection;

        while (true) {
            delayPrint(helper);
            response = input.next();

            if (response.matches("^[0-9]+$")) {  // regular expression to check if input is a number from the start of the input, match digits 0-9, match 1 or more times, until the end of the string
                selection = Integer.parseInt(response);  // parsing the input if its a integer
                if (selection >= starting && selection <= end) {  // checking if its within the valid range
                    return selection;  // returns the input
                }
            }

            delayPrintln(error);
        }
    }

    /**
     * Generating a random number, python style
     * @param min The min for the random number
     * @param max The max for the random number
     * @return a int of the random number
     */
    public static int randint(int min, int max) {
        return rNumber.nextInt(max - min + 1) + min;
    }

    /**
     * Helper method with default delay with new line added
     * @param toPrint String to print
     */

    public static void delayPrintln(String toPrint) {
        delayPrint(toPrint + "\n", 5, 0);
    }

    /**
     * Printing a table/large amount of text.
     * @param toPrint to print
     */

    public static void delayPrintTable(String toPrint) {
        String[] table = toPrint.split("\n");  // Spiting the input into lines

        for (String line : table) {  // Go through each individual line
            System.out.println(line);

            try {
                Thread.sleep(5);  // Stop printing for 5 millisecond after every line
            } catch (Exception e) {
            }

        }
    }

    /**
     * Helper method with default delay
     * @param toPrint String to print
     */

    public static void delayPrint(String toPrint) {
        delayPrint(toPrint, 5, 0);
    }

    /**
     * Printing a piece of text with a typing effect
     *
     * @param toPrint The text to print
     * @param micro  The amount of millisecond to wait
     * @param nano  The amound of nano seconds to wait
     */

    public static void delayPrint(String toPrint, int micro, int nano) {
        for (char c : toPrint.toCharArray()) {  // Iterating through each character of the input
            System.out.print(c);
            try {
                Thread.sleep(micro, nano);  // stopping the program
            } catch (Exception e) {
            }
        }
    }

    /**
     * adds a pause effect to allow the user to read the information before progressing
     */

    public static void pause() {
        delayPrintln("\nPress enter to continue...");
        try {
            System.in.read();  // System.in.read is used here because scanner considers <enter>(\n) to be a white space therefore skipping it
        } catch (Exception e) {
        }
    }

    /**
     * Prints a bunch of blank lines, "clearing" the screen
     */

    public static void clearScreen() {
        for (int j = 0; j < 100; j++)
            System.out.println();
    }

    /**
     * Prints the main title graphics for the starting screen
     */

    public static void mainMenu() {
        String pokeTitle =
                "                               .::.                           \n" +
                "                              .;:**'                          \n" +
                "                              `                               \n" +
                "  .:XHHHHk.              db.   .;;.     dH  MX                \n" +
                "oMMMMMMMMMMM       ~MM  dMMP :MMMMMR   MMM  MR      ~MRMN     \n" +
                "QMMMMMb  'MMX       MMMMMMP !MX' :M~   MMM MMM  .oo. XMMM 'MMM\n" +
                "  `MMMM.  )M> :X!Hk. MMMM   XMM.o'  .  MMMMMMM X?XMMM MMM>!MMP\n" +
                "   'MMMb.dM! XM M'?M MMMMMX.`MMMMMMMM~ MM MMM XM `' MX MMXXMM \n" +
                "    ~MMMMM~ XMM. .XM XM`'MMMb.~*?**~ .MMX M t MMbooMM XMMMMMP \n" +
                "     ?MMM>  YMMMMMM! MM   `?MMRb.    `MM   !L'MMMMM XM IMMM   \n" +
                "      MMMX   'MMMM'  MM       ~%:           !Mh.''' dMI IMMP  \n" +
                "      'MMM.                                             IMX   \n" +
                "       ~M!M                                             IM    \n";

        delayPrintTable(pokeTitle);

        delayPrintln("");
        delayPrintln("Game made by: Ryan Zhang");
        delayPrintln("");

        delayPrintln("Welcome to pokemon");

        pause();
    }

    /**
     * prints the you win tag
     */

    public static void win() {
        clearScreen();

        delayPrintln(" __     ______  _    _  __          _______ _   _ _ \n" +
                " \\ \\   / / __ \\| |  | | \\ \\        / /_   _| \\ | | |\n" +
                "  \\ \\_/ / |  | | |  | |  \\ \\  /\\  / /  | | |  \\| | |\n" +
                "   \\   /| |  | | |  | |   \\ \\/  \\/ /   | | | . ` | |\n" +
                "    | | | |__| | |__| |    \\  /\\  /   _| |_| |\\  |_|\n" +
                "    |_|  \\____/ \\____/      \\/  \\/   |_____|_| \\_(_)\n");

        pause();
    }

    /**
     * prints the you loose tag
     */

    public static void lose() {
        clearScreen();

        delayPrintln("      _____          __  __ ______    ______      ________ _____  _            \n" +
                "     / ____|   /\\   |  \\/  |  ____|  / __ \\ \\    / /  ____|  __ \\| |           \n" +
                "    | |  __   /  \\  | \\  / | |__    | |  | \\ \\  / /| |__  | |__) | |           \n" +
                "    | | |_ | / /\\ \\ | |\\/| |  __|   | |  | |\\ \\/ / |  __| |  _  /| |           \n" +
                "    | |__| |/ ____ \\| |  | | |____  | |__| | \\  /  | |____| | \\ \\|_|           \n" +
                "     \\_____/_/    \\_\\_|  |_|______|  \\____/   \\/   |______|_|  \\_(_)           ");

        pause();
    }

    /**
     * the introduction screen
     * @param pokemon The pokemon to be printed at the introduction
     * @return  Returns String[] with the first element being the player's name and the second being the enemy's name
     */

    public static String[] intro(String pokemon) {
        String[] names = new String[2];

        clearScreen();
        delayPrintln("Oak: Hello there! Welcome to the world of POKEMON! My name is OAK! People call me the POKEMON PROF!");
        pause();

        clearScreen();
        delayPrintTable(pokemon);
        delayPrintln("Oak: This world is inhabited by creatures called POKEMON! For some people, POKEMON are pets. Others use them for fights. Myself... I study POKEMON as a profession.");
        pause();

        clearScreen();
        delayPrintln("Oak: First, what is your name?");
        delayPrint(">>> ");
        names[0] = input.next();

        clearScreen();
        delayPrintln("Oak: Right! So your name is " + names[0] + "!");
        pause();

        clearScreen();
        delayPrintln("Oak: This is my grandson. He's been your rival since you were a baby. ...Erm, what is his name again?");
        delayPrint(">>> ");
        names[1] = input.next();

        clearScreen();
        delayPrintln("Oak: That's right! I remember now! His name is " + names[1] + "!");
        pause();

        clearScreen();
        delayPrintln("Oak: " + names[0] + "! Your very own POKEMON legend is about to unfold! A world of dreams and adventures with POKEMON awaits! Let's go!");
        pause();

        clearScreen();

        return names;
    }

    /**
     * Displays the pokemons that are selected
     * @param pokemonLibrary The pokedex
     * @param selected The selected pokemons
     */

    public static void displaySelected(Pokedex pokemonLibrary, ArrayList<Integer> selected) {
        clearScreen();

        delayPrintln("Oak: Great choice of POKEMONS.\n");

        delayPrintTable(String.format("║ %-20s ║ %-5s ║ %-20s ║ %-20s ║ %-20s ║", "Name", "HP", "Type", "Resistance", "weakness"));
        delayPrintTable("╠══════════════════════╬═══════╬══════════════════════╬══════════════════════╬══════════════════════╣");

        for (Integer p : selected) {  // Iterating through the pokemons printing each one
            delayPrintTable(pokemonLibrary.getPokemon(p).toString());
        }

        pause();
    }

    /**
     * Gettubg the user to select the pokemons
     * @param pokedex The pokedex used
     * @param number  The number of pokemons for the user to select
     * @return the list of pokemons selected
     */

    public static ArrayList<Integer> selectPokemon(Pokedex pokedex, int number) {
        ArrayList<Integer> selectedPokemons = new ArrayList<>();  // Declaring a new list for storing the selections
        Pokemon selectedPokemon; // The current pokemon that is selected
        String confirmation;  // The confirmation

        int pokemonCounter = 0;  // The counter for the number of pokemon selected
        int pokeSelect;

        while (pokemonCounter < number) {
            pokedex.printTable();

            if (pokemonCounter > 0) {  // Prints whats currently on the player's team
                delayPrint("Your team currently has: ");
                for (int n : selectedPokemons) {
                    delayPrint(pokedex.getPokemon(n).getName() + " ");
                }
                System.out.println();
            }


            pokeSelect = getRange("Error, please enter a valid Pokemon number.", "Please select a pokemon >>> ", 1, 151);  // gets the input
            selectedPokemon = pokedex.getPokemon(pokeSelect);

            clearScreen();
            delayPrintTable(pokedex.getPokemon(pokeSelect).getArt());

            if (selectedPokemons.contains(pokeSelect)) {  // If the pokemon has already been selected
                delayPrintln(selectedPokemon.getName() + " is already on your team.");
                pause();
            } else {

                delayPrintTable(String.format("║ %-20s ║ %-10s ║ %-10s ║ %-20s ║", "Attack", "EC", "DMG", "Special"));  // displays the attacks for the selected pokemon
                delayPrintTable("╠══════════════════════╬════════════╬════════════╬══════════════════════╣");

                for (Attack a : selectedPokemon.getAttack()) {
                    delayPrintTable(a.toString());
                }

                confirmation = getData("You have chosen " + selectedPokemon.getName() + "(y/n) >>> ", "Invalid selection.", new String[]{"y", "n"});  // gets a confirmation

                if (confirmation.equals("y") && !selectedPokemons.contains(pokeSelect)) { // confirmed
                    selectedPokemons.add(pokeSelect);  // add the pokemon to the selected
                    pokemonCounter += 1;
                    delayPrintln(selectedPokemon.getName() + " has been added to your team.");
                    pause();

                }

                delayPrintln(confirmation);
            }
        }


        return selectedPokemons;
    }

}
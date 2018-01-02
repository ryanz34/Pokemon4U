import java.util.*;

public class pokeTools {
    private static Scanner input = new Scanner(System.in);
    private static Random rNumber = new Random();

    public static String getData(String helper, String error, String[] data) {
        HashSet<String> correctResponse = new HashSet<>(Arrays.asList(data));
        String response;

        while (true) {
            delayPrint(helper);
            response = input.next();
            if (correctResponse.contains(response)) {
                return response;
            }

            delayPrintln(error);
        }
    }

    public static int getRange(String error, String helper, int starting, int end) {
        String response;
        int selection;

        while (true) {
            delayPrint(helper);
            response = input.next();

            if (response.matches("^[0-9]+$")) {
                selection = Integer.parseInt(response);
                if (selection >= starting && selection <= end) {
                    return selection;
                }
            }

            delayPrintln(error);
        }
    }

    public static int randint(int min, int max) {
        return rNumber.nextInt(max - min + 1) + min;
    }

    public static void delayPrintln(String toPrint) {
        delayPrint(toPrint + "\n", 5, 0);
    }

    public static void delayPrintTable(String toPrint) {
        String[] table = toPrint.split("\n");

        for (String line : table) {
            System.out.println(line);

            try {
                Thread.sleep(5);
            } catch (Exception e) {
            }

        }
    }

    public static void delayPrint(String toPrint) {
        delayPrint(toPrint, 5, 0);
    }

    public static void delayPrint(String toPrint, int micro, int nano) {
        for (char c : toPrint.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(micro, nano);
            } catch (Exception e) {
            }
        }
    }

    public static void pause() {
        delayPrintln("\nPress enter to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

    public static void clearScreen() {
        for (int j = 0; j < 100; j++)
            System.out.println();
    }

    public static void mainMenu() {
        String[] pokeTitle = {
                "                               .::.                           ",
                "                              .;:**'                          ",
                "                              `                               ",
                "  .:XHHHHk.              db.   .;;.     dH  MX                ",
                "oMMMMMMMMMMM       ~MM  dMMP :MMMMMR   MMM  MR      ~MRMN     ",
                "QMMMMMb  'MMX       MMMMMMP !MX' :M~   MMM MMM  .oo. XMMM 'MMM",
                "  `MMMM.  )M> :X!Hk. MMMM   XMM.o'  .  MMMMMMM X?XMMM MMM>!MMP",
                "   'MMMb.dM! XM M'?M MMMMMX.`MMMMMMMM~ MM MMM XM `' MX MMXXMM ",
                "    ~MMMMM~ XMM. .XM XM`'MMMb.~*?**~ .MMX M t MMbooMM XMMMMMP ",
                "     ?MMM>  YMMMMMM! MM   `?MMRb.    `MM   !L'MMMMM XM IMMM   ",
                "      MMMX   'MMMM'  MM       ~%:           !Mh.''' dMI IMMP  ",
                "      'MMM.                                             IMX   ",
                "       ~M!M                                             IM    "
        };

        for (String l : pokeTitle) {
            delayPrintTable(l);
        }
        delayPrintln("");
        delayPrintln("Game made by: Ryan Zhang");
        delayPrintln("");

        delayPrintln("Welcome to pokemon");

        delayPrint("Press enter to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

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

    public static void displaySelected(Pokedex pokemonLibrary, ArrayList<Integer> selected) {
        clearScreen();

        pokeTools.delayPrintln("Oak: Great choice of POKEMONS.\n");

        pokeTools.delayPrintTable(String.format("║ %-20s ║ %-5s ║ %-20s ║ %-20s ║ %-20s ║", "Name", "HP", "Type", "Resistance", "weakness"));
        pokeTools.delayPrintTable("╠══════════════════════╬═══════╬══════════════════════╬══════════════════════╬══════════════════════╣");

        for (Integer p : selected) {
            pokeTools.delayPrintTable(pokemonLibrary.getPokemon(p).toString());
        }

        pause();
    }

    public static ArrayList<Integer> selectPokemon(Pokedex pokedex, int number) {
        ArrayList<Integer> selectedPokemons = new ArrayList<>();
        Pokemon selectedPokemon;
        String confirmation;

        int pokemonCounter = 0;
        int pokeSelect;


        while (pokemonCounter < number) {
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
                try {
                    System.in.read();
                } catch (Exception e) {
                }
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
                    try {
                        System.in.read();
                    } catch (Exception e) {
                    }

                }

                pokeTools.delayPrintln(confirmation);
            }
        }


        return selectedPokemons;
    }

}
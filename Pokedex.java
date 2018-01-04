import java.io.File;
import java.util.LinkedHashMap; // Linked hashmap is used here to keep the order
import java.util.Map;
import java.util.Scanner;

/**
 * pokedex reads all the pokemons from the file and stores everything
 */

public class Pokedex {
    private LinkedHashMap<Integer, Pokemon> pokeData = new LinkedHashMap<>();  // Stores the pokemon with their pokemon number

    public Pokedex() {
        try {  // Try catch is used because we need to handle the file not found exception
            Scanner artIn;  // The Scanner for art
            Scanner infile = new Scanner(new File("Data/allPokemon.txt"));  // The scanner to read all of the pokemon data
            String data;  // The data read by the scanner
            String[] processData; // The data in a split form
            String art;  // The art for the pokemon
            int counter = 1;  // The counter to get allow for pokemon numbering

            while (infile.hasNextLine()) {  // Reads until the end of the file
                art = "";  // Clearing the art var

                data = infile.nextLine();  // Reading the data
                processData = data.split(",");

                pokeData.put(counter, new Pokemon(data));  // Creates a new pokemon object and stores it with the pokemon number

                // Create a new Scanner to read pokemons
                artIn = new Scanner(new File("Data/images/" + processData[0].toLowerCase().replace(".", "").replace(" ", "-").replace("(", "-").replace(")", "").replace("'", "") + ".txt"));


                // Read everything from the art file
                while (artIn.hasNextLine()) {
                    art += artIn.nextLine() + "\n";
                }

                pokeData.get(counter).setArt(art);  // Set the art for that pokemon

                counter++;  // Adding to counter

                artIn.close(); // Close to save memory

            }

            infile.close();
        } catch (Exception e) {
            e.printStackTrace();  // Print the error if it occurs
        }
    }

    public LinkedHashMap<Integer, Pokemon> getPokeDex() {  // Getters
        return pokeData;
    }

    public Pokemon getPokemon(int number) {
        if (pokeData.containsKey(number)) {  // If the pokemon number is valid
            return pokeData.get(number);
        }

        return null;  // returns null if not valid
    }

    public void printTable() {  // Prints the entire table
        pokeTools.delayPrintTable(String.format("║ %3s ║ %-20s ║ %-5s ║ %-20s ║ %-20s ║ %-20s ║", "#", "Name", "HP", "Type", "Resistance", "weakness"));
        pokeTools.delayPrintTable("╠═════╬══════════════════════╬═══════╬══════════════════════╬══════════════════════╬══════════════════════║");

        for (Map.Entry<Integer, Pokemon> entry : pokeData.entrySet()) {  // Printing every pokemon in the pokedex
            pokeTools.delayPrintTable(String.format("║ %3d ", entry.getKey()) + entry.getValue().toString());
        }
    }
}

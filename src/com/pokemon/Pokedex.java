package com.pokemon;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Pokedex {
    private LinkedHashMap<Integer, Pokemon> pokeData = new LinkedHashMap<>();
    private int maxMoves = 0;

    public Pokedex(){
        try {
            Scanner artIn;
            Scanner infile = new Scanner(new File("src/com/pokemon/data/allPokemon.txt"));
            String data;
            String[] processData;
            String art;
            int counter = 1;

            while (infile.hasNextLine()){
                art = "";

                data = infile.nextLine();
                processData = data.split(",");

                pokeData.put(counter, new Pokemon(data));
                maxMoves = Math.max(maxMoves, Integer.parseInt(processData[5]));

                artIn = new Scanner(new File("src/com/pokemon/data/images/" + processData[0].toLowerCase().replace(".", "").replace(" ", "-").replace("(", "-").replace(")", "").replace("'", "") + ".txt"));

                while (artIn.hasNextLine()) {
                    art += artIn.nextLine() + "\n";
                }

                pokeData.get(counter).setArt(art);

                counter++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LinkedHashMap<Integer, Pokemon> getPokeDex() {
        return pokeData;
    }

    public Pokemon getPokemon(int number) {
        if (pokeData.containsKey(number)) {
            return pokeData.get(number);
        }

        return null;
    }

    public static String stringMultiply(int times, String text){
        return new String(new char[times]).replace("\0", text);  // Creates a String using a string array and replace the blanks
    }

    public boolean contains(int number){
        return pokeData.containsKey(number);

    }

    public void printTable() {
        utility.delayPrintln(String.format("║ %3s ║ %-20s ║ %-5s ║ %-20s ║ %-20s ║ %-20s ║", "#","Name", "HP", "Type", "Resistance", "weakness"), 1);
        utility.delayPrintln("╠═════╬══════════════════════╬═══════╬══════════════════════╬══════════════════════╬══════════════════════║", 1);

        String filler;
        for (Map.Entry<Integer, Pokemon> entry : pokeData.entrySet()){
            utility.delayPrintln(entry.getValue().toString(), 1);
        }
    }
}

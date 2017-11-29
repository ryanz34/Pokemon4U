package com.pokemon;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Pokedex {
    private LinkedHashMap<String, Pokemon> pokeData = new LinkedHashMap<>();
    private int maxMoves = 0;

    public Pokedex(){
        try {
            Scanner artIn;
            Scanner infile = new Scanner(new File("src/com/pokemon/data/allPokemon.txt"));
            String data;
            String[] processData;
            String art;

            while (infile.hasNextLine()){
                art = "";

                data = infile.nextLine();
                processData = data.split(",");

                pokeData.put(processData[0].toLowerCase(), new Pokemon(data));
                maxMoves = Math.max(maxMoves, Integer.parseInt(processData[5]));

                artIn = new Scanner(new File("src/com/pokemon/data/images/" + processData[0].toLowerCase().replace(".", "").replace(" ", "-").replace("(", "-").replace(")", "").replace("'", "") + ".png"));

                while (artIn.hasNextLine()) {
                    art += artIn.nextLine() + "\n";
                }

                pokeData.get(processData[0]).setArt(art);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LinkedHashMap<String, Pokemon> getPokeData() {
        return pokeData;
    }

    public static String stringMultiply(int times, String text){
        return new String(new char[times]).replace("\0", text);  // Creates a String using a string array and replace the blanks
    }

    public boolean contains(String Name){
        return pokeData.containsKey(Name.toLowerCase());

    }

    public void printTable() {
        System.out.printf("║ %-20s ║ %-5s ║ %-20s ║ %-20s ║ %-20s ║", "Name", "HP", "Type", "Resistance", "weakness");
//        System.out.println(stringMultiply(maxMoves, String.format(" %-20s ║ %-10s ║ %-10s ║ %-20s ║", "Attack", "EC", "DMG", "Special")));
        System.out.print("╠══════════════════════╬═══════╬══════════════════════╬══════════════════════╬══════════════════════║");
//        System.out.println(stringMultiply(maxMoves,"╬══════════════════════╬════════════╬════════════╬══════════════════════") + "║");

        String filler;
        for (Map.Entry<String, Pokemon> entry : pokeData.entrySet()){
//            filler = stringMultiply(maxMoves - entry.getValue().getAttCount(), "                      ║            ║            ║                      ║");
            utility.delayPrintln(entry.getValue().toString(), 10);
//            System.out.println(filler);
        }
    }
}

import java.util.ArrayList;

/**
 * A class mostly used for storing the Pokemons
 */

public class Pokemon {
    private String name;  // Stores the different attributes of each pokemon
    private int hp;
    private String type;
    private String resistance;
    private String weakness;
    private int attCount;
    private ArrayList<Attack> attack = new ArrayList<>();  // We dont know how many attacks a pokemon will have, so an ArrayList is used here
    private String art;

    /**
     * The constructor for the Pokemon class
     * @param pokeData  // The raw line from the text file
     */
    public Pokemon(String pokeData) {
        String[] data = pokeData.split(",");  // Breaks the data down

        this.name = data[0];  // parsing through the line
        this.hp = Integer.parseInt(data[1]);
        this.type = data[2];
        this.resistance = data[3];
        this.weakness = data[4];
        this.attCount = Integer.parseInt(data[5]);

        for (int i = 0; i < this.attCount; i++) {  // A loop to get all the attacks
            if (9+4*i >= data.length) {  // Sometimes, there is a trailing comma when the last attack doesnt have a special. This causes a problem because the split command will delete all trailing whitespace
                attack.add(new Attack(data[6+4*i], Integer.parseInt(data[7+4*i]), Integer.parseInt(data[8+4*i]), ""));  // adding a new attack object to the array
            } else {
                attack.add(new Attack(data[6 + 4 * i], Integer.parseInt(data[7 + 4 * i]), Integer.parseInt(data[8 + 4 * i]), data[9 + 4 * i]));
            }
        }
    }

    public String getName() {  // The getters, mostly no setters because these information are not supposed to be modified
        return name;
    }

    public int getHp() {
        return hp;
    }

    public String getResistance() {
        return resistance;
    }

    public String getType() {
        return type;
    }

    public String getWeakness() {
        return weakness;
    }

    public ArrayList<Attack> getAttack() {
        return attack;
    }

    public void setArt(String art) {  // Art setter is used because we do not want to have 150 scanners
        this.art = art;
    }

    public String getArt() {
        return art;
    }

    public String toString () {  // Prints the pokemons in a table fashion
        return String.format("║ %-20s ║ %-5d ║ %-20s ║ %-20s ║ %-20s ║", this.name, this.hp, this.type, this.resistance, this.weakness);
    }
}


import java.util.ArrayList;


public class Pokemon {
    private String name;
    private int hp;
    private String type;
    private String resistance;
    private String weakness;
    private int attCount;
    private ArrayList<Attack> attack = new ArrayList<>();
    private String art;


    public Pokemon(String pokeData) {
        String[] data = pokeData.split(",");

        this.name = data[0];
        this.hp = Integer.parseInt(data[1]);
        this.type = data[2];
        this.resistance = data[3];
        this.weakness = data[4];
        this.attCount = Integer.parseInt(data[5]);

        for (int i = 0; i < this.attCount; i++) {
            if (9+4*i >= data.length) {
                attack.add(new Attack(data[6+4*i], Integer.parseInt(data[7+4*i]), Integer.parseInt(data[8+4*i]), ""));
            } else {
                attack.add(new Attack(data[6 + 4 * i], Integer.parseInt(data[7 + 4 * i]), Integer.parseInt(data[8 + 4 * i]), data[9 + 4 * i]));
            }
        }
    }

    public String getName() {
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

    public int getAttCount() {
        return attCount;
    }

    public ArrayList<Attack> getAttack() {
        return attack;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getArt() {
        return art;
    }

    public String toString () {
        return String.format("║ %-20s ║ %-5d ║ %-20s ║ %-20s ║ %-20s ║", this.name, this.hp, this.type, this.resistance, this.weakness);
    }
}


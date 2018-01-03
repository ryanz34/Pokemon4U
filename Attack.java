/**
 * Attack class contains all everything about an attack, used for easier storage of attacks
 */

public class Attack {
    private String name; // stores the names, ec, damage, special. Private because we do not want these fields to be modified by anything
    private int ec;
    private int damage;
    private String special;

    /**
     * @param name name of attack
     * @param ec  the energy cost to perform this attack
     * @param damage  the damage that this attack can do
     * @param special  the specialty of the attack
     */

    public Attack (String name, int ec, int damage, String special) {
        this.name = name;  // making the params belong to this object
        this.ec = ec;
        this.damage = damage;
        this.special = special;
    }

    public String getName() {  // Getters for the attack
        return name;
    }

    public int getEc() {
        return ec;
    }

    public int getDamage() {
        return damage;
    }

    public String getSpecial(){
        return special;
    }

    public String toString() {  // to String allows for easier printing
        return String.format("║ %-20s ║ %-10d ║ %-10d ║ %-20s ║", name, ec, damage, special);
    }
}

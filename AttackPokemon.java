import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * The object used for battling
 */

public class AttackPokemon {
    private int EC = 50;  // more attributes are added here
    private String name;
    private int ohp;  // Keeps track of the old hp so that heal does not go over the max health
    private int hp;
    private String type;
    private String resistance;
    private String weakness;
    private ArrayList<Attack> attacks;
    private String art;
    private boolean fainted = false;
    private boolean stunned = false;
    private boolean disabled = false;

    /**
     * Copies information from the pokemon class over
     *
     * @param poke the pokemon object
     */
    public AttackPokemon(Pokemon poke) {
        name = poke.getName();
        hp = ohp = poke.getHp();
        type = poke.getType();
        resistance = poke.getResistance();
        weakness = poke.getWeakness();
        attacks = poke.getAttack();
        art = poke.getArt();
    }

    public String getType() {  // Getters to prevent cheating
        return type;
    }

    public boolean isAlive() {
        return !fainted;
    }

    public String getName() {
        return name;
    }

    public boolean isStunned() {
        return stunned;
    }

    public int getHp() {
        return hp;
    }

    public int getEC() {
        return EC;
    }

    public void roundEnd() { // Runs after each round
        EC = Math.min(EC + 10, 50);  // add 10 to the ec with a max of 50
    }

    public void turnDone () {  // Runs after the turn is done
        if (stunned) {
            stunned = false;
        }
    }

    public void battleDone () {hp = Math.min(ohp, hp + 20);}  // After its done battling, since the number of pokemons have increased, a 6v6 system is used. Therefore this is not used

    public String getStatus() {  // A very simple function to get the current conditions of the active pokemon
        String stat = "";

        if (stunned) {  // If the pokemon is stunned
            stat += "Stunned ";  // add stunned to the current status
        }
        if (disabled) {
            stat += "Disabled";
        }

        if (stat.length() != 0) {  // If the pokemon has a special status
            return stat;
        } else {// If the pokemon does not have any special status
            return "Alive";  // Returns a default
        }

    }

    public String getArt() { // Gets the art for the pokemon
        return art;
    }

    public ArrayList<Attack> getAttacks() {
        return attacks;
    }

    public boolean playerAttack(AttackPokemon opponent, String username) {  // The attacking class for the player, this is in here because this interacts with the current pokemon a lot
        pokeTools.delayPrintln(String.format("║ %3s ║ %-20s ║ %-10s ║ %-10s ║ %-20s ║", "#", "Attack", "EC", "DMG", "Special"));  // Prints the headers
        pokeTools.delayPrintln("╠═════╬══════════════════════╬════════════╬════════════╬══════════════════════╣");

        int moveNum, counter = 0; // The vars used for user input and the counter for selecting an attack
        Attack selectedAttack;  // The attack selected


        for (Attack a : attacks) { // Looping through the attacks
            counter++;
            pokeTools.delayPrintTable(String.format("║ %3d ", counter) + a.toString()); // displaying each attack
        }

        while (true) {  // do not quit until an action has been selected
            moveNum = pokeTools.getRange("Please select a valid option", "Select your attack (0 to exit) >>> ", 0, counter); // gets a valid input

            if (moveNum == 0) {  // if the option selected is 0
                return false;  // return that no move has been selected
            } else {
                selectedAttack = attacks.get(moveNum - 1);  // gets the attack selected

                if (EC >= selectedAttack.getEc()) {  // Check if the player has the required EC
                    this.EC -= selectedAttack.getEc();  // Subtract the EC of the attack
                    this.Attack(selectedAttack, opponent, username);  // Attack the pokemon

                    return true;  // Action performed

                } else {
                    pokeTools.delayPrintln("Not enough energy.");  // Error message for not enough energy
                }
            }
        }

    }

    /**
     * Attacking the opponent
     * @param att  The attack used
     * @param opponent  The opponent that is going to be attacked
     * @param username  The name of the current player
     */

    public void Attack(Attack att, AttackPokemon opponent, String username) {
        int damage = att.getDamage();  // Gets the damage of the attack

        if (disabled) {  // If the pokemon is disabled, then subtract 10 from the damage of the attack, limit at 0.
            damage = Math.max(damage - 10, 0);
        }

        pokeTools.delayPrintln(username + ": " + this.name + " use " + att.getName());  // Prints the attack usage

        switch (att.getSpecial()) {  // testing for what is the special of this attack
            case "wild card":  // If its wild card
                if (pokeTools.randint(0, 1) == 1) {  // 50 % chance hit
                    opponent.hit(damage, "", this);  // Attack the opponent
                } else {
                    pokeTools.delayPrintln(att.getName() + " missed.");
                }

                break;  // Break out of the switch

            case "wind storm":
                while (pokeTools.randint(0, 1) == 1 && opponent.isAlive()) {  // While store can attack infinity, 50 % chance of continued attack
                    opponent.hit(damage, "",this);
                }
                break;

            case "stun":
                if (pokeTools.randint(0, 1) == 1) {  // 50% Chance for stun
                    opponent.hit(damage, "stun", this);  // Tells the pokemon being hit what special this attack will cause
                } else {
                    opponent.hit(damage, "", this);
                }

                break;

            case "disable":
                if (pokeTools.randint(0, 1) == 1) {
                    opponent.hit(damage, "disable", this);
                } else {
                    opponent.hit(damage, "", this);
                }

                break;

            case "recharge":  // Recharging the EC
                EC = Math.min(50, EC + damage);

                pokeTools.delayPrintln(name + "'s EC was restored by " + damage + ".");

                break;

            case "heal":  // Healing the pokemon
                opponent.hit(damage, "", this);

                pokeTools.delayPrintln(name + "'s health was restored by " + Math.min(ohp - hp, damage) + ".");
                hp = Math.min(ohp, hp+50);  // Healing caps out at max health

                break;


            default:  // If there is not special attack or the special attack is unknown
                opponent.hit(damage, "", this);

                break;
        }

        pokeTools.pause();

    }

    /**
     * The damage after being attacked
     *
     * @param damage the damage that the attack is going to cause
     * @param type  the type of the special
     * @param attacker  the attacker
     */

    public void hit(int damage, String type, AttackPokemon attacker) {
        if (attacker.getType().equals(weakness)) {  // if the attacker's type is the current pokemon's weakness, then double damage, super effective
            hp -= damage * 2; // subtract double damage
            pokeTools.delayPrintln("Its super effective");
            pokeTools.delayPrintln(this.name + " was hit for " + damage * 2 + " damage.");


        } else if (attacker.getType().equals(resistance)) {  // If attacker's type is the current pokemon's weakness
            hp -= damage / 2;
            pokeTools.delayPrintln("Its not very effective");
            pokeTools.delayPrintln(this.name + " was hit for " + damage / 2 + " damage.");

        } else {  // Not super effective or not effective
            hp -= damage;
            pokeTools.delayPrintln(this.name + " was hit for " + damage + " damage.");

        }

        if (type.equals("stun")) {  // Specials, stunning the current pokemon
            pokeTools.delayPrintln(this.name + " is stunned.");
            stunned = true;  // Setting the stunned attr to true
        } else if (type.equals("disable")) {
            pokeTools.delayPrintln(this.name + " is disabled.");
            disabled = true;
        }

        if (hp <= 0) {  // Checking if the pokemon has fainted
            fainted = true;
            pokeTools.delayPrintln(name + " has fainted");
        }
    }

    public String toString() { // prints the current attack pokemon
        return String.format("║ %-20s ║ %-5d ║ %-5d ║ %-20s ║ %-20s ║ %-20s ║ %-10b ║ %-10b ║", this.name, this.hp, this.EC, this.type, this.resistance, this.weakness, this.stunned, this.disabled);
    }


}

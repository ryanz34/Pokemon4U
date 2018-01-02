import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AttackPokemon {
    private int EC = 50;
    private String name;
    private int ohp;
    private int hp;
    private String type;
    private String resistance;
    private String weakness;
    private ArrayList<Attack> attacks;
    private String art;
    private boolean fainted = false;
    private boolean stunned = false;
    private boolean disabled = false;

    public AttackPokemon(Pokemon poke) {
        name = poke.getName();
        hp = ohp = poke.getHp();
        type = poke.getType();
        resistance = poke.getResistance();
        weakness = poke.getWeakness();
        attacks = poke.getAttack();
        art = poke.getArt();
    }

    public String getType() {
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

    public boolean isDisabled() {
        return disabled;
    }

    public int getHp() {
        return hp;
    }

    public int getEC() {
        return EC;
    }

    public void roundEnd() {
        EC = Math.min(EC + 10, 50);
    }

    public void turnDone () {
        if (stunned) {
            stunned = false;
        }
    }

    public void battleDone () {hp = Math.min(ohp, hp + 20);}

    public String getStatus() {
        String stat = "";

        if (stunned) {
            stat += "Stunned";
        }
        if (disabled) {
            stat += "Disabled";
        }

        if (stat.length() != 0) {
            return stat;
        } else {
            return "Alive";
        }

    }

    public String getArt() {
        return art;
    }

    public ArrayList<Attack> getAttacks() {
        return attacks;
    }

    public boolean playerAttack(AttackPokemon opponent, String username) {
        pokeTools.delayPrintln(String.format("║ %3s ║ %-20s ║ %-10s ║ %-10s ║ %-20s ║", "#", "Attack", "EC", "DMG", "Special"));
        pokeTools.delayPrintln("╠═════╬══════════════════════╬════════════╬════════════╬══════════════════════╣");

        int moveNum, counter = 0;
        Attack selectedAttack;


        for (Attack a : attacks) {
            counter++;
            pokeTools.delayPrintTable(String.format("║ %3d ", counter) + a.toString());
        }

        while (true) {
            moveNum = pokeTools.getRange("Please select a valid option", "Select your attack (0 to exit) >>> ", 0, counter);

            if (moveNum == 0) {
                return false;
            } else {
                selectedAttack = attacks.get(moveNum - 1);

                if (EC >= selectedAttack.getEc()) {
                    this.EC -= selectedAttack.getEc();
                    this.Attack(selectedAttack, opponent, username);

                    return true;

                } else {
                    pokeTools.delayPrintln("Not enough energy.");
                }
            }
        }

    }

    public void Attack(Attack att, AttackPokemon opponent, String username) {
        int damage = att.getDamage();

        if (disabled) {
            damage -= 10;
        }

        pokeTools.delayPrintln(username + ": " + this.name + " use " + att.getName());

        switch (att.getSpecial()) {
            case "wild card":
                if (pokeTools.randint(0, 1) == 1) {
                    opponent.hit(damage, "", this);
                } else {
                    pokeTools.delayPrintln(att.getName() + " missed.");
                }

                break;

            case "wind storm":
                while (pokeTools.randint(0, 1) == 1 && opponent.isAlive()) {
                    opponent.hit(damage, "",this);
                }
                break;

            case "stun":
                if (pokeTools.randint(0, 1) == 1) {
                    opponent.hit(damage, "stun", this);
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

            case "recharge":
                EC = Math.min(50, EC + damage);

                pokeTools.delayPrintln(name + "'s EC was restored by " + damage + ".");

                break;

            case "heal":
                opponent.hit(damage, "", this);

                pokeTools.delayPrintln(name + "'s health was restored by " + Math.min(ohp - hp, damage) + ".");
                hp = Math.min(ohp, hp+50);

                break


            default:
                opponent.hit(damage, "", this);

                break;
        }

        pokeTools.pause();

    }

    public void hit(int damage, String type, AttackPokemon attacker) {
        if (attacker.getType().equals(weakness)) {
            hp -= damage * 2;
            pokeTools.delayPrintln("Its super effective");
            pokeTools.delayPrintln(this.name + " was hit for " + damage * 2 + " damage.");


        } else if (attacker.getType().equals(resistance)) {
            hp -= damage / 2;
            pokeTools.delayPrintln("Its not very effective");
            pokeTools.delayPrintln(this.name + " was hit for " + damage / 2 + " damage.");

        } else {
            hp -= damage;
            pokeTools.delayPrintln(this.name + " was hit for " + damage + " damage.");

        }

        if (type.equals("stun")) {
            pokeTools.delayPrintln(this.name + " is stunned.");
            stunned = true;
        } else if (type.equals("disable")) {
            pokeTools.delayPrintln(this.name + " is disabled.");
            disabled = true;
        }

        if (hp <= 0) {
            fainted = true;
            pokeTools.delayPrintln(name + " has fainted");
        }
    }

    public String toString() {
        return String.format("║ %-20s ║ %-5d ║ %-5d ║ %-20s ║ %-20s ║ %-20s ║ %-10b ║ %-10b ║", this.name, this.hp, this.EC, this.type, this.resistance, this.weakness, this.stunned, this.disabled);
    }


}

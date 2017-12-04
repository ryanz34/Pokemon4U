package com.pokemon;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AttackPokemon {
    private int EC = 50;
    private String name;
    private int hp;
    private String type;
    private String resistance;
    private String weakness;
    private int attCount;
    private ArrayList<Attack> attacks;
    private String art;
    private boolean fainted = false;
    private boolean stunned = false;
    private boolean disabled = false;

    public AttackPokemon(Pokemon poke) {
        name = poke.getName();
        hp = poke.getHp();
        type = poke.getType();
        resistance = poke.getResistance();
        weakness = poke.getWeakness();
        attCount = poke.getAttCount();
        attacks = poke.getAttack();
        art = poke.getArt();
    }

    public String getType () {
        return type;
    }

    public boolean isAlive () {
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

    public String getStatus () {
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

    public boolean Attack (AttackPokemon opponent) {
        Scanner input = new Scanner(System.in);
        Random rNumber = new Random();
        int moveNum, counter = 0;
        Attack selectedAttack;

        utility.delayPrintln(String.format("║ %3s ║ %-20s ║ %-10s ║ %-10s ║ %-20s ║", "#", "Attack", "EC", "DMG", "Special"), 1);
        utility.delayPrintln("╠═════╬══════════════════════╬════════════╬════════════╬══════════════════════╣");

        for (Attack a : attacks) {
            counter ++;
            utility.delayPrintln(String.format("║ %3d ", counter) + a.toString(), 1);
        }

        while (true) {
            moveNum = utility.getRange(input, "Please select a valid option", "Select your attack (0 to exit) >>> ", 0, counter);

            if (moveNum == 0) {
                return false;
            } else {
                selectedAttack = attacks.get(moveNum-1);
                utility.delayPrintln(this.name + " use " + selectedAttack.getName());

                if (EC >= selectedAttack.getEc()){
                    this.EC -= selectedAttack.getEc();

                    switch (selectedAttack.getSpecial()){
                        case "wild card":
                            if (rNumber.nextInt(2) == 1) {
                                opponent.hit(selectedAttack, this);
                            } else {
                                utility.delayPrintln(selectedAttack.getName() + " missed.");
                            }
                            utility.delayPrint("Press enter to continue...");
                            try {System.in.read();} catch (Exception e){}
                            break;

                        case "wind storm":
                            while (rNumber.nextInt(2) != 1 && opponent.isAlive()) {
                                opponent.hit(selectedAttack, this);
                                utility.delayPrint("Press enter to continue...");
                                try {System.in.read();} catch (Exception e){}
                            }
                            break;

                        default:
                            opponent.hit(selectedAttack, this);
                            utility.delayPrint("Press enter to continue...");
                            try {System.in.read();} catch (Exception e){}
                            break;
                    }
                    return true;

                } else {
                    utility.delayPrintln("Not enough energy.");
                }
            }
        }
    }

    public void hit (Attack att, AttackPokemon opponent) {
        if (opponent.getType().equals(weakness)) {
            utility.delayPrintln("Its not very effective");
            hp -= att.getDamage() * 2;
        } else if (opponent.getType().equals(resistance)) {
            utility.delayPrintln("Its super effective");
            hp -= att.getDamage() / 2;
        } else {
            hp -= att.getDamage();
        }

        utility.delayPrintln(this.name + " was hit for " + att.getDamage() + " damage.");

        if (hp <= 0) {
            fainted = true;
            System.out.println(name + " has fainted");
        }
    }

    public String toString () {
        return String.format("║ %-20s ║ %-5d ║ %-5d ║ %-20s ║ %-20s ║ %-20s ║ %-10b ║ %-10b ║", this.name, this.hp, this.EC, this.type, this.resistance, this.weakness, this.stunned, this.disabled);
    }


}

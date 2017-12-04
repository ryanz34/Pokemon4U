package com.pokemon;

public class Attack {
    private String name;
    private int ec;
    private int damage;
    private String special;

    public Attack (String name, int ec, int damage, String special) {
        this.name = name;
        this.ec = ec;
        this.damage = damage;
        this.special = special;
    }

    public String getName() {
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

    public String toString() {
        return String.format("║ %-20s ║ %-10d ║ %-10d ║ %-20s ║", name, ec, damage, special);
    }
}

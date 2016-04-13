package com.starfighter.game.game.units;

public class Weapon {
    private int weapon;
    private int fireRate;

    public Weapon(int weapon, int fireRate) {
        this.weapon = weapon;
        this.fireRate = fireRate;
    }

    public int getWeapon() {
        return weapon;
    }

    public void upgrade(int upgrade) {
        this.weapon += upgrade;

        if (weapon > 3) {
            weapon = 3;
        }
    }

    public int getFireRate() {
        return fireRate;
    }

    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }
}

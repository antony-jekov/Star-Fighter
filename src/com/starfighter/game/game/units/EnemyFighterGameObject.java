package com.starfighter.game.game.units;

public class EnemyFighterGameObject extends FighterGameObject {
    public EnemyFighterGameObject() {
        this.weapon.setWeapon(1);
        this.weapon.setFireRate(1000);
    }

    @Override
    public void update() {
        fire();

        super.update();
    }
}

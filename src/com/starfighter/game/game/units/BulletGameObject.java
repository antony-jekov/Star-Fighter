package com.starfighter.game.game.units;

import com.starfighter.game.engine.units.GameObject;

public class BulletGameObject extends MovingGameObject {

    private int damage;
    private GameObject owner;
    private long selfDestructAfter;

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void start() {
        selfDestructAfter = System.currentTimeMillis() + 1100;
        super.start();
    }

    @Override
    public void update() {
        if (System.currentTimeMillis() > selfDestructAfter) {
            active = false;
            return;
        }

        super.update();
    }

    public GameObject getOwner() {
        return owner;
    }

    public void setOwner(GameObject owner) {
        this.owner = owner;
    }

    @Override
    public void onCollision(GameObject other) {
        if (owner != null && other == owner) {
            return;
        }

        if (other instanceof AliveGameObject) {
            ((AliveGameObject) other).adjustHitpoints(-damage);
            active = false;
            return;
        }

        super.onCollision(other);
    }
}

package com.starfighter.game.game.units;

import com.starfighter.game.engine.units.GameObject;

public class PowerupGameObject extends MovingGameObject {

    public enum PowerupType {
        WEAPON,
        HEALTH,
        LIFE
    }

    private PowerupType type;

    public PowerupType getType() {
        return type;
    }

    public void setType(PowerupType type) {
        this.type = type;
    }

    @Override
    public void onCollision(GameObject other) {
        if (other instanceof FighterGameObject) {
            switch (type) {
                case WEAPON:
                    ((FighterGameObject)other).getWeapon().upgrade(1);
                    break;
                case HEALTH:
                    ((FighterGameObject)other).adjustHitpoints(20);
                    break;
                case LIFE:
                    ((FighterGameObject)other).adjustLives(1);
                    break;
            }

            active = false;
            return;
        }

        super.onCollision(other);
    }
}

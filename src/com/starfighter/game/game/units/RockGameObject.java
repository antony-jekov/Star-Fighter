package com.starfighter.game.game.units;

import com.starfighter.game.Game;
import com.starfighter.game.engine.units.GameObject;

public class RockGameObject extends AliveGameObject {

    @Override
    public void update() {
        if (positionY > Game.GAME_HEIGHT) {
            active = false;
            return;
        }

        super.update();
    }

    @Override
    public void onCollision(GameObject other) {
        if (other.getTag().equals("rock") || other.getTag().equals("enemy"))
            return;

        if (other instanceof AliveGameObject) {
            ((AliveGameObject) other).adjustHitpoints(-this.hitpoints);
            if (((AliveGameObject) other).alive) {
                die();
            }
        }

        super.onCollision(other);
    }
}

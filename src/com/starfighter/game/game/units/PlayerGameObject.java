package com.starfighter.game.game.units;

import com.starfighter.game.Game;
import com.starfighter.game.engine.input.InputHandler;
import com.starfighter.game.engine.units.GameObject;

public class PlayerGameObject extends FighterGameObject {

    public PlayerGameObject() {
        speed = 250;
    }

    @Override
    public void update() {
        if (InputHandler.isActionPressed(InputHandler.ACTION_RIGHT)) {
            velocityX = 1;
        } else if (InputHandler.isActionPressed(InputHandler.ACTION_LEFT)) {
            velocityX = -1;
        } else {
            velocityX = 0;
        }

        if (InputHandler.isActionPressed(InputHandler.ACTION_UP)) {
            velocityY = -1;
        } else if (InputHandler.isActionPressed(InputHandler.ACTION_DOWN)) {
            velocityY = 1;
        } else {
            velocityY = 0;
        }

        if (InputHandler.isActionPressed(InputHandler.ACTION_FIRE)) {
            fire();
        }

        super.update();

        if (positionX + width > Game.GAME_WIDTH) {
            positionX = Game.GAME_WIDTH - width;
        } else if (positionX < 0) {
            positionX = 0;
        }

        if (positionY + height > Game.GAME_HEIGHT) {
            positionY = Game.GAME_HEIGHT - height;
        } else if (positionY < 0) {
            positionY = 0;
        }
    }

    @Override
    protected void onAliveStatusChanged() {
        if (!alive) {
            weapon.setWeapon(1);
        }
        super.onAliveStatusChanged();
    }
}

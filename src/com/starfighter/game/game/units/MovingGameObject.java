package com.starfighter.game.game.units;

import com.starfighter.game.Game;
import com.starfighter.game.engine.units.GameObject;

public class MovingGameObject extends GameObject {

    protected int speed;
    protected float velocityX;
    protected float velocityY;
    private float velocityLen;

    protected int direction;

    @Override
    public void update() {
        if (velocityX != 0 || velocityY != 0) {
            velocityLen = (float) Math.abs(Math.sqrt((velocityX * velocityX) + (velocityY * velocityY)));
            if (velocityX != 0) {
                positionX += normalizedX() * speed * Game.deltaTime;
            }

            if (velocityY != 0) {
                positionY += normalizedY() * speed * Game.deltaTime;
            }
        }

        super.update();
    }

    private float normalizedX() {
        return velocityX / velocityLen;
    }

    private float normalizedY() {
        return velocityY / velocityLen;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}

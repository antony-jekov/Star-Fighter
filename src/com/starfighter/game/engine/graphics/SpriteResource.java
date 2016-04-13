package com.starfighter.game.engine.graphics;

public class SpriteResource {
    public String name;
    public int positionX;
    public int positionY;
    public int width;
    public int height;

    @Override
    public String toString() {
        return name + "- x: " + positionX + " y: " + positionY + " w: " + width + " h: " + height;
    }
}
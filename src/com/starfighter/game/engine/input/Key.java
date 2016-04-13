package com.starfighter.game.engine.input;

public class Key {
    private boolean isPressed;

    public boolean isPressed() {
        return this.isPressed;
    }

    public void toggle(boolean isPressed) {
        this.isPressed = isPressed;
    }
}
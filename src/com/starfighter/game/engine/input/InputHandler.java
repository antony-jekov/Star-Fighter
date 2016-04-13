package com.starfighter.game.engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    public static final int ACTION_LEFT = 1;
    public static final int ACTION_UP = 2;
    public static final int ACTION_RIGHT = 3;
    public static final int ACTION_DOWN = 4;
    public static final int ACTION_FIRE = 5;

    private static Key left = new Key();
    private static Key up = new Key();
    private static Key right = new Key();
    private static Key down = new Key();
    private static Key fire = new Key();

    public InputHandler() {
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);
    }

    public void toggleKey(int keyCode, boolean isPressed) {
        Key key = null;
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            key = left;
        } else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            key = up;
        } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            key = right;
        } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            key = down;
        } else if (keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_ENTER) {
            key = fire;
        }

        if (key != null) {
            key.toggle(isPressed);
        }
    }

    public static boolean isActionPressed(int actionId) {
        if (actionId == ACTION_DOWN) {
            return down.isPressed();
        }

        if (actionId == ACTION_FIRE) {
            return fire.isPressed();
        }

        if (actionId == ACTION_LEFT) {
            return left.isPressed();
        }

        if (actionId == ACTION_RIGHT) {
            return right.isPressed();
        }

        if (actionId == ACTION_UP) {
            return up.isPressed();
        }

        return false;
    }
}

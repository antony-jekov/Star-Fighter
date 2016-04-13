package com.starfighter.game.engine.managers;

import com.starfighter.game.Game;
import com.starfighter.game.engine.units.GameObject;

import java.util.List;

public class UpdateManager implements com.starfighter.game.engine.contracts.UpdateManager {

    private final List<GameObject> gameObjects;

    public UpdateManager(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public void update() {
        updateCollisions();
        updateGameObjects();
    }

    private void updateCollisions() {
        GameObject current;
        GameObject other;

        for (int i = 0, len = gameObjects.size(); i < len; i++) {
            current = gameObjects.get(i);
            if (!current.isActive())
                continue;

            for (int j = 0; j < len; j++) {
                if (j == i) {
                    continue;
                }

                other = gameObjects.get(j);
                if (!other.isActive())
                    continue;

                if (gameObjectsIntersect(current, other)) {
                    current.onCollision(other);
                    //System.out.println("Collision: " + current.getTag() + " -><- " + other.getTag());
                }
            }
        }
    }

    private boolean gameObjectsIntersect(GameObject current, GameObject other) {
        int tw = current.getWidth();
        int th = current.getHeight();
        int rw = other.getWidth();
        int rh = other.getHeight();
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        float tx = current.positionX;
        float ty = current.positionY;
        float rx = other.positionX;
        float ry = other.positionY;
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;
        //      overflow || intersect
        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
    }

    private void updateGameObjects() {
        for (int i = 0, len = gameObjects.size(); i < len; i++) {
            if (gameObjects.get(i).isActive())
                gameObjects.get(i).update();

            if (gameObjects.get(i).positionY > Game.GAME_HEIGHT)
                gameObjects.get(i).setActive(false);
        }
    }
}

package com.starfighter.game.game.units;

import java.util.ArrayList;
import java.util.List;

public class AliveGameObject extends MovingGameObject {

    private List<OnAliveStatusChangedListener> aliveChangeListeners;
    private List<OnHitpointsChangedListener> hitpointsChangedListeners;
    private int maxLives;

    public void adjustLives(int amount) {
        lives += amount;
        if (lives > maxLives)
            lives = maxLives;

        onLifeStatusChanged();
    }

    public interface OnHitpointsChangedListener {
        void onLifeStatusChanged(int hitpoints, int maxHitpoints, int lives);
    }

    public interface OnAliveStatusChangedListener {
        void OnAliveStausChanged(boolean alive, AliveGameObject aliveGameObject);
    }

    protected boolean alive = true;
    protected int maxHitpoints;
    protected int hitpoints;
    protected int lives;

    public AliveGameObject() {
        this.aliveChangeListeners = new ArrayList<OnAliveStatusChangedListener>();
        this.hitpointsChangedListeners = new ArrayList<OnHitpointsChangedListener>();
    }

    public int getMaxLives() {
        return maxLives;
    }

    public void setMaxLives(int maxLives) {
        this.maxLives = maxLives;
    }

    public void addAliveChangeListener(OnAliveStatusChangedListener listener) {
        if (!aliveChangeListeners.contains(listener)) {
            this.aliveChangeListeners.add(listener);
        }
    }

    public void removeAliveChangeListener(OnAliveStatusChangedListener listener) {
        this.aliveChangeListeners.remove(listener);
    }

    public void addHitpointsChangedListener(OnHitpointsChangedListener listener) {
        this.hitpointsChangedListeners.add(listener);
    }

    public void removeHitpointsChangedListener(OnHitpointsChangedListener listener) {
        this.hitpointsChangedListeners.remove(listener);
    }

    public int getMaxHitpoints() {
        return maxHitpoints;
    }

    public void setMaxHitpoints(int maxHitpoints) {
        this.maxHitpoints = maxHitpoints;
        if (hitpoints > maxHitpoints) {
            hitpoints = maxHitpoints;
        }
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
        if (hitpoints > maxHitpoints) {
            this.hitpoints = maxHitpoints;
        }
    }

    public void adjustHitpoints(int amount) {
        hitpoints += amount;

        if (hitpoints < 0) {
            hitpoints = 0;
        } else if (hitpoints > maxHitpoints) {
            hitpoints = maxHitpoints;
        }

        attemptDie();
        onLifeStatusChanged();
    }

    private void onLifeStatusChanged() {
        for (int i = 0; i < hitpointsChangedListeners.size(); i++) {
            hitpointsChangedListeners.get(i).onLifeStatusChanged(this.hitpoints, this.maxHitpoints, this.lives);
        }
    }

    public void attemptDie() {
        if (hitpoints <= 0) {
            lives--;
            die();

            if (lives >= 1) {
                resurrect();
            }
        }
    }

    protected void resurrect() {
        if (lives > 0) {
            hitpoints = maxHitpoints;
            alive = true;
            active = true;
        }

        onLifeStatusChanged();
    }

    protected void die() {
        alive = false;
        active = false;

        onAliveStatusChanged();
    }

    protected void onAliveStatusChanged() {
        for (int i = 0; i < aliveChangeListeners.size(); i++) {
            aliveChangeListeners.get(i).OnAliveStausChanged(alive, this);
        }
    }
}

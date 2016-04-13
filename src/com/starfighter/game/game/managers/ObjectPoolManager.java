package com.starfighter.game.game.managers;

import com.starfighter.game.engine.units.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ObjectPoolManager {
    public static final String TAG_BULLET = "bullet";
    public static final String TAG_PLAYER = "player";
    public static final String TAG_ROCK = "rock";
    public static final String TAG_POWERUP = "powerup";
    public static final String TAG_ENEMY = "enemy";

    private static ObjectPoolManager instance;

    private ArrayList<GameObject> objectsPool;

    public ObjectPoolManager() {
        this.objectsPool = new ArrayList<GameObject>();
    }

    public static ObjectPoolManager getInstance() {
        return instance;
    }

    public ArrayList<GameObject> getGameObjects() {
        return objectsPool;
    }

    public GameObject getGameObjectByTag(String tag) {
        GameObject gameObj = null;
        for (int i = 0, len = objectsPool.size(); i < len; i++) {
            if (!objectsPool.get(i).isActive() && objectsPool.get(i).getTag().equals(tag)) {
                gameObj = objectsPool.get(i);
                gameObj.setActive(true);

                break;
            }
        }

        return gameObj;
    }

    public void registerGameObject(GameObject gameObject) {
        System.out.println("Expanding pool: " + objectsPool.size() + ". Added " + gameObject.getTag());
        this.objectsPool.add(gameObject);
        Collections.sort(objectsPool, new Comparator<GameObject>() {
            @Override
            public int compare(GameObject o1, GameObject o2) {
                if (o1.getzIndex() > o2.getzIndex())
                    return 1;

                if (o1.getzIndex() < o2.getzIndex())
                    return -1;

                return 0;
            }
        });
    }

    public static void init() {
        instance = new ObjectPoolManager();
    }
}

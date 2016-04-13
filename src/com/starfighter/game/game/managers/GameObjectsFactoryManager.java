package com.starfighter.game.game.managers;

import com.starfighter.game.game.units.*;
import com.starfighter.game.engine.units.GameObject;

public class GameObjectsFactoryManager {

    public enum GameObjectType {
        BULLET,
        ROCK,
        PLAYER,
        POWERUP,
        ENEMY
    }

    public static GameObject instantiateGameObject(GameObjectType type) {
        GameObject gameObject = null;

        switch (type) {
            case BULLET:
                gameObject = ObjectPoolManager.getInstance().getGameObjectByTag(ObjectPoolManager.TAG_BULLET);
                if (gameObject == null) {
                    gameObject = new BulletGameObject();
                    gameObject.setTag(ObjectPoolManager.TAG_BULLET);
                    ObjectPoolManager.getInstance().registerGameObject(gameObject);
                }

                break;
            case PLAYER:
                gameObject = ObjectPoolManager.getInstance().getGameObjectByTag(ObjectPoolManager.TAG_PLAYER);
                if (gameObject == null) {
                    gameObject = new PlayerGameObject();
                    gameObject.setTag(ObjectPoolManager.TAG_PLAYER);
                    ObjectPoolManager.getInstance().registerGameObject(gameObject);
                }

                break;
            case ROCK:
                gameObject = ObjectPoolManager.getInstance().getGameObjectByTag(ObjectPoolManager.TAG_ROCK);
                if (gameObject == null) {
                    gameObject = new RockGameObject();
                    gameObject.setTag(ObjectPoolManager.TAG_ROCK);
                    ObjectPoolManager.getInstance().registerGameObject(gameObject);
                }

                break;
            case POWERUP:
                gameObject = ObjectPoolManager.getInstance().getGameObjectByTag(ObjectPoolManager.TAG_POWERUP);
                if (gameObject == null) {
                    gameObject = new PowerupGameObject();
                    gameObject.setTag(ObjectPoolManager.TAG_POWERUP);
                    ObjectPoolManager.getInstance().registerGameObject(gameObject);
                }

                break;
            case ENEMY:
                gameObject = ObjectPoolManager.getInstance().getGameObjectByTag(ObjectPoolManager.TAG_ENEMY);
                if (gameObject == null) {
                    gameObject = new EnemyFighterGameObject();
                    gameObject.setTag(ObjectPoolManager.TAG_ENEMY);
                    ObjectPoolManager.getInstance().registerGameObject(gameObject);
                    FireManager.getInstance().registerFighter((FighterGameObject) gameObject);
                }

                break;
        }

        gameObject.start();

        return gameObject;
    }
}

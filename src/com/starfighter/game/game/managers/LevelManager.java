package com.starfighter.game.game.managers;

import com.starfighter.game.Game;
import com.starfighter.game.engine.graphics.ResourceParser;
import com.starfighter.game.engine.utils.Random;
import com.starfighter.game.game.units.AliveGameObject;
import com.starfighter.game.game.units.EnemyFighterGameObject;
import com.starfighter.game.game.units.PowerupGameObject;
import com.starfighter.game.game.units.RockGameObject;

public class LevelManager {

    public static int score;

    private static final int MIN_ROCK_SPAWN_TIME = 200;
    private static final int MAX_ROCK_SPAWN_TIME = 300;

    private long nextRockSpawnTime;
    private long nextEnemySpawnTime;
    private AliveGameObject.OnAliveStatusChangedListener onRockDestroyedCallback;

    public LevelManager() {
        onRockDestroyedCallback = new AliveGameObject.OnAliveStatusChangedListener() {
            @Override
            public void OnAliveStausChanged(boolean alive, AliveGameObject aliveObject) {
                aliveObject.removeAliveChangeListener(onRockDestroyedCallback);

                if (!alive) {
                    score += aliveObject.getMaxHitpoints();
                    if (Random.range(0, 100) > 60) {
                        SpawnPowerup(aliveObject.positionX + (aliveObject.getWidth() / 2), aliveObject.positionY + (aliveObject.getHeight() / 2));
                    }
                }
            }
        };
    }

    public int getScore() {
        return score;
    }

    public void update() {
        if (System.currentTimeMillis() > nextRockSpawnTime) {
            spawnRock();

            nextRockSpawnTime = System.currentTimeMillis() + Random.range(MIN_ROCK_SPAWN_TIME, MAX_ROCK_SPAWN_TIME);
        }

        if (System.currentTimeMillis() > nextEnemySpawnTime) {
            spawnEnemy();

            nextEnemySpawnTime = System.currentTimeMillis() + 5000;
        }
    }

    private void spawnEnemy() {
        EnemyFighterGameObject enemyFighterGameObject = (EnemyFighterGameObject) GameObjectsFactoryManager.instantiateGameObject(GameObjectsFactoryManager.GameObjectType.ENEMY);
        enemyFighterGameObject.setResource(ResourceParser.getResource("enemyRed1.png"));
        enemyFighterGameObject.positionAt(Random.range(0, Game.GAME_WIDTH - enemyFighterGameObject.getWidth()), -enemyFighterGameObject.getHeight());
        enemyFighterGameObject.setVelocityY(1);
        enemyFighterGameObject.setSpeed(70);
        enemyFighterGameObject.setMaxHitpoints(100);
        enemyFighterGameObject.setHitpoints(100);
    }

    private void spawnRock() {
        int chance = Random.range(0, 100);
        if (chance > 95)
            SpawnRock(100, 250, "meteorBrown_big1.png");
        else if (chance > 90)
            SpawnRock(90, 300, "meteorBrown_big2.png");
        else if (chance > 85)
            SpawnRock(110, 200, "meteorBrown_big3.png");
        else if (chance > 80)
            SpawnRock(120, 150, "meteorBrown_big4.png");
        else if (chance > 75)
            SpawnRock(130, 120, "meteorBrown_med1.png");
        else if (chance > 70)
            SpawnRock(160, 100, "meteorBrown_med3.png");
        else if (chance > 75)
            SpawnRock(180, 80, "meteorBrown_small1.png");
        else if (chance > 70)
            SpawnRock(190, 60, "meteorBrown_small2.png");
        else if (chance > 65)
            SpawnRock(200, 30, "meteorBrown_tiny1.png");
        else if (chance > 60)
            SpawnRock(220, 20, "meteorBrown_tiny2.png");
    }

    private void SpawnRock(int speed, int hitpoints, String resourceName) {
        RockGameObject rockGameObject = (RockGameObject) GameObjectsFactoryManager.instantiateGameObject(GameObjectsFactoryManager.GameObjectType.ROCK);
        rockGameObject.setResource(ResourceParser.getResource(resourceName));
        rockGameObject.positionAt(Random.range(0, Game.GAME_WIDTH - rockGameObject.getWidth()), -rockGameObject.getHeight());
        rockGameObject.setVelocityY(1);
        rockGameObject.setSpeed(speed);
        rockGameObject.setMaxHitpoints(hitpoints);
        rockGameObject.setHitpoints(hitpoints);
        rockGameObject.addAliveChangeListener(this.onRockDestroyedCallback);
    }

    private void SpawnPowerup(float posX, float posY) {
        int chance = Random.range(0, 100);
        PowerupGameObject powerupGameObject = (PowerupGameObject) GameObjectsFactoryManager.instantiateGameObject(GameObjectsFactoryManager.GameObjectType.POWERUP);
        powerupGameObject.setVelocityY(1);
        powerupGameObject.setSpeed(50);

        if (chance > 60) {
            powerupGameObject.setResource(ResourceParser.getResource("powerupRed.png"));
            powerupGameObject.setType(PowerupGameObject.PowerupType.HEALTH);
        } else if (chance > 30) {
            powerupGameObject.setResource(ResourceParser.getResource("powerupBlue_bolt.png"));
            powerupGameObject.setType(PowerupGameObject.PowerupType.WEAPON);
        } else {
            powerupGameObject.setResource(ResourceParser.getResource("playerLife1_blue.png"));
            powerupGameObject.setType(PowerupGameObject.PowerupType.LIFE);
        }

        powerupGameObject.positionAt(posX - (powerupGameObject.getWidth() / 2), posY - (powerupGameObject.getHeight() / 2));
    }
}

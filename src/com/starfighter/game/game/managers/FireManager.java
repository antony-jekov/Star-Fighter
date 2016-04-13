package com.starfighter.game.game.managers;

import com.starfighter.game.engine.graphics.ResourceParser;
import com.starfighter.game.game.units.BulletGameObject;
import com.starfighter.game.game.units.FighterGameObject;

public class FireManager {

    private static FireManager instance;

    private FireManager() {
    }

    public static FireManager getInstance() {
        return instance;
    }

    public static void init() {
        instance = new FireManager();
    }

    public void registerFighter(FighterGameObject fighterGameObject) {
        fighterGameObject.addAttackListener(new FighterGameObject.OnFighterAttackedListener() {
            @Override
            public void OnFighterAttack(FighterGameObject fighter) {
                OnFighterAttacked(fighter);
            }
        });
    }

    private void OnFighterAttacked(FighterGameObject fighter) {
        if (fighter.getTag().equals("enemy")) {
            enemyFire(fighter);
        } else {
            playerFire(fighter);
        }
    }

    private void playerFire(FighterGameObject fighter) {
        int weapon = fighter.getWeapon().getWeapon();

        if (weapon == 1 || weapon == 3) {
            BulletGameObject bulletGameObject = (BulletGameObject) GameObjectsFactoryManager.instantiateGameObject(GameObjectsFactoryManager.GameObjectType.BULLET);
            bulletGameObject.setResource(ResourceParser.getResource(weapon == 1 ? "laserGreen06.png" : "laserBlue06.png"));
            bulletGameObject.positionAt((fighter.positionX + (fighter.getWidth() / 2)) - (bulletGameObject.getWidth() / 2), fighter.positionY - (bulletGameObject.getHeight()));
            bulletGameObject.setDirection(fighter.getDirection());
            bulletGameObject.setOwner(fighter);
            bulletGameObject.setDamage(40);
            bulletGameObject.setSpeed(600);
            bulletGameObject.setVelocityY(-1);
        }

        if (weapon == 2 || weapon == 3) {
            BulletGameObject bulletGameObject = (BulletGameObject) GameObjectsFactoryManager.instantiateGameObject(GameObjectsFactoryManager.GameObjectType.BULLET);
            bulletGameObject.setResource(ResourceParser.getResource("laserGreen06.png"));
            bulletGameObject.positionAt((fighter.positionX + (fighter.getWidth() / 4)) - (bulletGameObject.getWidth() / 2), fighter.positionY);
            bulletGameObject.setDirection(fighter.getDirection());
            bulletGameObject.setOwner(fighter);
            bulletGameObject.setDamage(30);
            bulletGameObject.setSpeed(600);
            bulletGameObject.setVelocityY(-1);

            BulletGameObject bulletGameObject2 = (BulletGameObject) GameObjectsFactoryManager.instantiateGameObject(GameObjectsFactoryManager.GameObjectType.BULLET);
            bulletGameObject2.setResource(ResourceParser.getResource("laserGreen06.png"));
            bulletGameObject2.positionAt((fighter.positionX + fighter.getWidth() - (fighter.getWidth() / 4)) - (bulletGameObject.getWidth() / 2), fighter.positionY);
            bulletGameObject2.setDirection(fighter.getDirection());
            bulletGameObject2.setOwner(fighter);
            bulletGameObject2.setDamage(30);
            bulletGameObject2.setSpeed(600);
            bulletGameObject2.setVelocityY(-1);
        }
    }

    private void enemyFire(FighterGameObject fighter) {
        BulletGameObject bulletGameObject = (BulletGameObject) GameObjectsFactoryManager.instantiateGameObject(GameObjectsFactoryManager.GameObjectType.BULLET);
        bulletGameObject.setResource(ResourceParser.getResource("laserRed06.png"));
        bulletGameObject.positionAt((fighter.positionX + (fighter.getWidth() / 2)) - (bulletGameObject.getWidth() / 2), fighter.positionY + fighter.getHeight());
        bulletGameObject.setDirection(fighter.getDirection());
        bulletGameObject.setVelocityY(1);
        bulletGameObject.setOwner(fighter);
        bulletGameObject.setDamage(20);
        bulletGameObject.setSpeed(600);
    }
}

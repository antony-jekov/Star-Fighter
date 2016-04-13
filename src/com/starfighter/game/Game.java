package com.starfighter.game;

import com.starfighter.game.engine.contracts.RenderManager;
import com.starfighter.game.engine.contracts.UpdateManager;
import com.starfighter.game.engine.graphics.ResourceParser;
import com.starfighter.game.game.managers.FireManager;
import com.starfighter.game.game.managers.GameObjectsFactoryManager;
import com.starfighter.game.game.managers.LevelManager;
import com.starfighter.game.game.managers.ObjectPoolManager;
import com.starfighter.game.game.managers.render.JFrameRenderManager;
import com.starfighter.game.game.units.PlayerGameObject;

public class Game implements Runnable {

    public static final int GAME_WIDTH = 960;
    public static final int GAME_HEIGHT = 620;
    public static final String GAME_NAME = "Star Fighter";
    private static final int DESIRED_FPS = 100;
    public static float deltaTime;
    private static Game instance;
    private boolean running = false;

    private RenderManager renderManager;
    private UpdateManager updateManager;
    private LevelManager levelManager;

    public Game() {
        instance = this;
        init();
    }

    public static void main(String[] args) {
        new Game().start();
    }

    public static Game instance() {
        return instance;
    }

    public synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    public synchronized void stop() {
        running = false;
    }

    private void init() {
        ObjectPoolManager.init();
        ResourceParser.init("/sprites/sheet.xml");
        FireManager.init();
        this.levelManager = new LevelManager();
        this.renderManager = new JFrameRenderManager(GAME_WIDTH, GAME_HEIGHT, GAME_NAME, ObjectPoolManager.getInstance().getGameObjects());
        this.updateManager = new com.starfighter.game.engine.managers.UpdateManager(ObjectPoolManager.getInstance().getGameObjects());

        PlayerGameObject player = (PlayerGameObject) GameObjectsFactoryManager.instantiateGameObject(GameObjectsFactoryManager.GameObjectType.PLAYER);
        player.setSpriteName("playerShip1_blue.png");
        player.setResource(ResourceParser.getResource(player.getSpriteName()));
        player.positionAt((GAME_WIDTH / 2) - (player.getWidth() / 2), (int) (GAME_HEIGHT - (player.getHeight() * 2f)));
        player.setDirection(-1);
        player.setLives(3);
        player.setzIndex(1000);
        player.setMaxHitpoints(100);
        player.setHitpoints(100);
        player.setMaxLives(7);

        FireManager.getInstance().registerFighter(player);
        renderManager.registerPlayer(player);
    }

    @Override
    public void run() {
        // convert the time to seconds
        float delta = 1.0f / DESIRED_FPS;
        float nextTime = getPreciseCurrentTime();
        float maxTimeDiff = 0.5f;
        int skippedFrames = 1;
        int maxSkippedFrames = 5;
        float prevFrameTick;
        float currentFrameTick = getPreciseCurrentTime();
        while (running) {
            // convert the time to seconds
            float currTime = getPreciseCurrentTime();
            if ((currTime - nextTime) > maxTimeDiff) nextTime = currTime;
            if (currTime >= nextTime) {
                // assign the time for the next update
                nextTime += delta;
                prevFrameTick = currentFrameTick;
                currentFrameTick = getPreciseCurrentTime();
                deltaTime = currentFrameTick - prevFrameTick;
                update();
                if ((currTime < nextTime) || (skippedFrames > maxSkippedFrames)) {
                    render();
                    skippedFrames = 1;
                } else {
                    skippedFrames++;
                }
            } else {
                // calculate the time to sleep
                int sleepTime = (int) (1000.0 * (nextTime - currTime));
                // sanity check
                if (sleepTime > 0) {
                    // sleep until the next update
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        // do nothing
                    }
                }
            }
        }
    }

    private float getPreciseCurrentTime() {
        return System.nanoTime() / 1000000000.0f;
    }

    private void render() {
        this.renderManager.render();
    }

    private void update() {
        levelManager.update();
        this.updateManager.update();
    }
}

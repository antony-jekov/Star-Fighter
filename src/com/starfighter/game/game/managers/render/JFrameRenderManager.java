package com.starfighter.game.game.managers.render;

import com.starfighter.game.Game;
import com.starfighter.game.engine.contracts.RenderManager;
import com.starfighter.game.engine.graphics.Image;
import com.starfighter.game.engine.graphics.ResourceParser;
import com.starfighter.game.engine.graphics.SpriteResource;
import com.starfighter.game.engine.graphics.SpriteSheet;
import com.starfighter.game.engine.input.InputHandler;
import com.starfighter.game.engine.units.GameObject;
import com.starfighter.game.game.managers.LevelManager;
import com.starfighter.game.game.units.AliveGameObject;
import com.starfighter.game.game.units.PlayerGameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.List;

public class JFrameRenderManager implements RenderManager {

    private final List<GameObject> gameObjects;
    private final int[] mainPixels;
    private final Image background;
    private final int width;
    private final int height;
    private final int[] playerLiveSpritePixels;
    private final SpriteResource playerLiveSpriteResource;
    private final int playerHealthBarPosX;
    int yPixel;
    int xPixel;
    int col;
    int index;
    private JFrame frame;
    private Canvas canvas;
    private SpriteSheet spriteSheet;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private BufferedImage mainImage;
    private int playerLives;
    private int playerHitpoints;
    private int playerMaxHitpoints;

    private int playerHealthBarWidth;
    private int currentPlayerHealthBar;

    public JFrameRenderManager(int width, int height, String name, java.util.List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
        this.spriteSheet = new SpriteSheet("/sprites/sheet.png");
        background = new com.starfighter.game.engine.graphics.Image("/backgrounds/purple.png");
        playerLiveSpriteResource = ResourceParser.getResource("playerLife1_blue.png");
        this.playerLiveSpritePixels = spriteSheet.getSubImagePixels(playerLiveSpriteResource.name, playerLiveSpriteResource.positionX, playerLiveSpriteResource.positionY, playerLiveSpriteResource.width, playerLiveSpriteResource.height);
        this.playerHealthBarWidth = (int) (Game.GAME_WIDTH * .3f);
        this.playerHealthBarPosX = (Game.GAME_WIDTH / 2) - (playerHealthBarWidth / 2);
        this.canvas = new Canvas();
        this.canvas.addKeyListener(new InputHandler());

        this.canvas.setMinimumSize(new Dimension(width, height));
        this.canvas.setMaximumSize(new Dimension(width, height));
        this.canvas.setPreferredSize(new Dimension(width, height));

        this.width = width;
        this.height = height;

        this.frame = new JFrame(name);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());

        this.frame.add(canvas, BorderLayout.CENTER);
        this.frame.pack();
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        this.frame.requestFocus();

        mainImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.mainPixels = ((DataBufferInt) mainImage.getRaster().getDataBuffer()).getData();
    }

    @Override
    public void registerPlayer(PlayerGameObject player) {
        playerLives = player.getLives();
        playerMaxHitpoints = player.getMaxHitpoints();
        playerHitpoints = player.getHitpoints();

        player.addHitpointsChangedListener(new AliveGameObject.OnHitpointsChangedListener() {
            @Override
            public void onLifeStatusChanged(int hitpoints, int maxHitpoints, int lives) {
                playerMaxHitpoints = maxHitpoints;
                playerHitpoints = hitpoints;
                playerLives = lives;
            }
        });
    }

    public void render() {
        bufferStrategy = canvas.getBufferStrategy();
        if (bufferStrategy == null) {
            canvas.createBufferStrategy(2);
            return;
        }

        graphics = bufferStrategy.getDrawGraphics();
        graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        updateImage();
        graphics.drawImage(this.mainImage, 0, 0, null);

        graphics.setColor(Color.white);
        graphics.setFont(new Font("Comic Sans MS", Font.BOLD, 30));

        graphics.drawString(String.format("%010d", LevelManager.score), (Game.GAME_WIDTH - 200), 60);

        if (playerLives > 0) {
            currentPlayerHealthBar = (int) (playerHealthBarWidth * (playerHitpoints / (float) playerMaxHitpoints));

            graphics.setColor(Color.white);
            graphics.fillRoundRect((Game.GAME_WIDTH / 2) - (currentPlayerHealthBar / 2) - 3, 37, currentPlayerHealthBar + 6, 26, 5, 5);

            graphics.setColor(Color.red);
            graphics.fillRoundRect((Game.GAME_WIDTH / 2) - (currentPlayerHealthBar / 2), 40, currentPlayerHealthBar, 20, 5, 5);
        }

        graphics.dispose();
        bufferStrategy.show();
    }

    private void updateImage() {
        renderBackground();
        renderGameObjects();

        renderPlayerLives();
    }

    private void renderGameObjects() {
        for (int i = 0, len = gameObjects.size(); i < len; i++) {
            GameObject spriteObj = gameObjects.get(i);
            if (!spriteObj.isActive())
                continue;

            renderPixels((int) spriteObj.positionX, (int) spriteObj.positionY, spriteObj.getWidth(), spriteObj.getHeight(),
                    spriteSheet.getSubImagePixels(spriteObj.getSpriteName(), spriteObj.getSpritePosX(), spriteObj.getSpritePosY(), spriteObj.getWidth(), spriteObj.getHeight()));
        }
    }

    private void renderBackground() {
        for (int row = 0, rows = (Game.GAME_HEIGHT / background.getHeight()); row <= rows; row++) {
            for (int col = 0, cols = (Game.GAME_WIDTH / background.getWidth()); col <= cols; col++) {
                renderPixels(col * background.getWidth(), row * background.getHeight(), background.getWidth(), background.getHeight(), background.getPixels());
            }
        }
    }

    private void renderPlayerLives() {
        for (int i = 0; i < playerLives; i++) {
            renderPixels(10 + (playerLiveSpriteResource.width * i) + (10 * i), 40, playerLiveSpriteResource.width, playerLiveSpriteResource.height, playerLiveSpritePixels);
        }
    }

    public void renderPixels(int xPos, int yPos, int width, int height, int[] pix) {
        for (int y = 0; y < height; y++) {
            yPixel = y + yPos;
            if (yPixel >= this.height) {
                break;
            }

            if (yPixel < 0)
                continue;

            for (int x = 0; x < width; x++) {
                xPixel = x + xPos;
                col = pix[x + y * width];
                if (col != 0x000000) {
                    index = xPixel + (yPixel * this.width);
                    if (mainPixels.length > index)
                        mainPixels[index] = col;
                }
            }
        }
    }
}

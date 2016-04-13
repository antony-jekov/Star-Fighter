package com.starfighter.game.engine.graphics;

public class Screen {
    private static final int MAP_WIDTH = 64;
    private static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;

    private int xOffset = 0;
    private int yOffset = 0;

    private int width;
    private int height;

    private SpriteSheet spriteSheet;

    public Screen(int width, int height, SpriteSheet spriteSheet) {
        this.width = width;
        this.height = height;

        this.spriteSheet = spriteSheet;
    }
}

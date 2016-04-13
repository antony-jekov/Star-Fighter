package com.starfighter.game.engine.units;

import com.starfighter.game.engine.graphics.SpriteResource;

public class GameObject {
    protected String tag;

    protected boolean active = true;
    protected int spritePosX;
    protected int spritePosY;

    private int zIndex;

    public float positionX;
    public float positionY;

    protected int width;
    protected int height;

    public int getSpritePosX() {
        return spritePosX;
    }

    public int getSpritePosY() {
        return spritePosY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void update(){}

    public void start() {}

    public void onCollision(GameObject other) {
    }

    protected SpriteResource resource;
    protected String spriteName;

    public String getSpriteName() {
        return spriteName;
    }

    public void setSpriteName(String spriteName) {
        this.spriteName = spriteName;
    }

    public SpriteResource getResource() {
        return resource;
    }

    public void setResource(SpriteResource resource) {
        this.resource = resource;
        onResourceChanged();
    }

    private void onResourceChanged() {
        spriteName = resource.name;
        this.width = resource.width;
        this.height = resource.height;
        this.spritePosX = resource.positionX;
        this.spritePosY = resource.positionY;
    }

    public void positionAt(float x, float y) {
        this.positionX = x;
        this.positionY = y;
    }
}

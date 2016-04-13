package com.starfighter.game.engine.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

public class Image {

    private int[] pixels;
    private BufferedImage image;
    private int positionX = 0;
    private int positionY = 0;
    private int width;
    private int height;

    public Image() {
        this(null);
    }

    public Image(String path) {
        if (path != null) {
            try {
                image = ImageIO.read(Image.class.getResourceAsStream(path));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (image != null) {
                width = image.getWidth();
                height = image.getHeight();

                pixels = image.getRGB(0, 0, width, height, null, 0, width);
            }
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getPixels() {
        return pixels;
    }
}

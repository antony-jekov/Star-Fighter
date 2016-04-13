package com.starfighter.game.engine.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

public class SpriteSheet {

    private BufferedImage image;
    private Dictionary<String, int[]> subImagesPixels;

    public SpriteSheet(String path) {
        try {
            image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        subImagesPixels = new Hashtable<String, int[]>();
    }

    public int[] getSubImagePixels(String name, int x, int y, int w, int h) {
        int[] pixels = subImagesPixels.get(name);
        if (pixels == null) {
            pixels = image.getSubimage(x, y, w, h).getRGB(0, 0, w, h, null, 0, w);
            subImagesPixels.put(name, pixels);
        }

        return pixels;
    }
}

package com.starfighter.game.engine.utils;

public class Random {
    private static java.util.Random random = new java.util.Random();

    public static int range(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }
}

package kamilswojak.ellio.util;

import java.util.Random;

public class RandomNumberGenerator {
    public static Random random = new Random();

    public static int getRandomBetween(int lowerBound, int upperBound) {
        return random.nextInt(upperBound - lowerBound) + lowerBound;
    }

    public static int getRandom(int upperBound) {
        return random.nextInt(upperBound);
    }

}

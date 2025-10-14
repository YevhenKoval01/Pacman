package controller.util;

import java.util.Random;

public class RandomUtil {

    private final static Random random = new Random();

    public static int randomInt(int upperBound) {
        return random.nextInt(upperBound);
    }

    public static boolean randomBool() {
        return random.nextInt(2) == 1;
    }

    public static int randomInt(int lowerBound, int upperBound) {
        return lowerBound + random.nextInt(upperBound - lowerBound + 1);
    }

}

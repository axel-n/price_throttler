package com.price.processor.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class RandomNumberHelper {

    private static final Random random = new Random();
    private final static int rangeMax = 100;
    private final static int rangeMin = 1;

    public static double getRandomDouble() {
        double priceRaw = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
        BigDecimal bigDecimal = BigDecimal.valueOf(priceRaw).setScale(2, RoundingMode.CEILING);
        return bigDecimal.doubleValue();
    }

    public static int randomInt(int maxClientDelayMs) {
        return random.nextInt(maxClientDelayMs);
    }
}

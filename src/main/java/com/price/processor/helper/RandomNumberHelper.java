package com.price.processor.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class RandomNumberHelper {

    private Random random = new Random();
    private int rangeMax = 100;
    private int rangeMin = 1;

    public double getRandomDouble() {
        double priceRaw = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
        BigDecimal bigDecimal = BigDecimal.valueOf(priceRaw).setScale(2, RoundingMode.CEILING);
        return bigDecimal.doubleValue();
    }
}

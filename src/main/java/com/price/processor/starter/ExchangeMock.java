package com.price.processor.starter;

import com.price.processor.PriceThrottler;
import com.price.processor.common.CurrencyPair;
import com.price.processor.helper.RandomNumberHelper;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

@AllArgsConstructor
public class ExchangeMock {

    private final PriceThrottler priceThrottler;
    private final int countPairs = CurrencyPair.values().length;

    public void start() {
        produce();
    }

    private void produce() {
        new Thread(() -> {

            while (true) {
                String ccyPair = randomPair();
                double price = RandomNumberHelper.getRandomDouble();

                System.out.println("new price. ccyPair=" + ccyPair + ", price=" + price);
                priceThrottler.onPrice(ccyPair, price);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private String randomPair() {
        int indexPair = RandomNumberHelper.randomInt(countPairs - 1);
        return CurrencyPair.values()[indexPair].name();
    }
}

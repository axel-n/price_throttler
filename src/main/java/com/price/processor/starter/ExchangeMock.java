package com.price.processor.starter;

import com.price.processor.PriceThrottler;
import com.price.processor.helper.RandomNumberHelper;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

@AllArgsConstructor
public class ExchangeMock {

    private final PriceThrottler priceThrottler;
    private final RandomNumberHelper numberHelper = new RandomNumberHelper();

    private void produce() {
        new Thread(() -> {

            while (true) {
                String ccyPair = "EURUSD";
                double price = numberHelper.getRandomDouble();

                System.out.println("new price. ccyPair=" + ccyPair + ", price=" + price);
                priceThrottler.onPrice(ccyPair, price);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }



    public void start() {
        produce();
    }
}

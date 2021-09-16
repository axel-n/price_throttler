package com.price.processor.starter;

import com.price.processor.throttler.PriceThrottler;
import com.price.processor.common.CurrencyPair;
import com.price.processor.helper.RandomNumberHelper;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.price.processor.common.CurrencyPair.EUR_USD;

@AllArgsConstructor
public class ExchangeMock {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    private final PriceThrottler priceThrottler;
    private final int delay;
    private final int countPairs = CurrencyPair.values().length;

    public void start() {
        produce();
    }

    private void produce() {
        new Thread(() -> {

            long counter = 0;
            while (true) {
                String ccyPair = randomPair();
                double price = RandomNumberHelper.getRandomDouble();

                if (counter % 100 == 0) {
                    logger.info("sent count={} prices", counter);
                }

                priceThrottler.onPrice(ccyPair, price);

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter++;
            }
        }).start();

    }

    private String randomPair() {
        int indexPair = RandomNumberHelper.randomInt(countPairs - 1);
        return CurrencyPair.values()[indexPair].name();
    }
}

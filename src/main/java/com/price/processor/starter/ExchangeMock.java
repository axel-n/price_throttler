package com.price.processor.starter;

import com.price.processor.throttler.PriceThrottler;
import com.price.processor.common.CurrencyPair;
import com.price.processor.helper.RandomNumberHelper;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.price.processor.common.CurrencyPair.EUR_RUB;

@AllArgsConstructor
public class ExchangeMock {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    private final PriceThrottler priceThrottler;
    private final int countPairs = CurrencyPair.values().length;

    public void start() {
        produce();
    }

    private void produce() {
        new Thread(() -> {

            while (true) {
                String ccyPair = EUR_RUB.name(); // TODO restore old - randomPair()
                double price = RandomNumberHelper.getRandomDouble();

                logger.info("\nnew price. ccyPair={}, price={}", ccyPair, price);
                priceThrottler.onPrice(ccyPair, price);

                try {
                    Thread.sleep(100);
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

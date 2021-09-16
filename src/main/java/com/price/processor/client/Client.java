package com.price.processor.client;

import com.price.processor.common.PriceProcessor;
import com.price.processor.throttler.PriceThrottler;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor
public class Client implements PriceProcessor {
    @EqualsAndHashCode.Exclude
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    private final int clientId;

    @EqualsAndHashCode.Exclude
    private final PriceThrottler priceThrottler;

    @EqualsAndHashCode.Exclude
    private final int timeForConsume;

    @Override
    public void onPrice(String ccyPair, double rate) {
        logger.debug("client id={}, timeForConsume={}, pair={}, rate={}", clientId, timeForConsume, ccyPair, rate);

        try {
            Thread.sleep(timeForConsume);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void subscribe() {
        priceThrottler.subscribe(this);
    }

    public void unsubscribe() {
        priceThrottler.unsubscribe(this);
    }

    @Override
    public void subscribe(PriceProcessor priceProcessor) {

    }

    @Override
    public void unsubscribe(PriceProcessor priceProcessor) {
    }

    public int getClientId() {
        return clientId;
    }
}

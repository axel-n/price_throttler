package com.price.processor.client;

import com.price.processor.common.PriceProcessor;
import com.price.processor.PriceThrottler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Client implements PriceProcessor {

    private final int clientId;
    private final PriceThrottler priceThrottler;
    private final int timeForConsume;

    @Override
    public void onPrice(String ccyPair, double rate) {
        System.out.println("client id=." + clientId + "ccyPair=" + ccyPair + "rate=" + rate);

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
}

package com.price.processor.client;

import com.price.processor.common.PriceProcessor;
import com.price.processor.PriceThrottler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Client implements PriceProcessor {

    private final PriceThrottler priceThrottler;

    @Override
    public void onPrice(String ccyPair, double rate) {
        System.out.println("client. update. ccyPair=" + ccyPair + "rate=" + rate);
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

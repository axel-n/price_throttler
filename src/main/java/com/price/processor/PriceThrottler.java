package com.price.processor;

import com.price.processor.common.PriceProcessor;
import lombok.AllArgsConstructor;

import java.util.LinkedList;

@AllArgsConstructor
public class PriceThrottler implements PriceProcessor {

    private final LinkedList<PriceProcessor> listSubscribers = new LinkedList<>();

    @Override
    public void onPrice(String ccyPair, double rate) {
        System.out.println("PriceThrottler. ccyPair=" + ccyPair + "rate=" + rate);

        listSubscribers
                .stream()
                .parallel()
                .forEach(client -> client.onPrice(ccyPair, rate));
    }

    @Override
    public void subscribe(PriceProcessor priceProcessor) {
        System.out.println("subscribe new client. priceProcessor=" + priceProcessor);
        listSubscribers.add(priceProcessor);
    }

    @Override
    public void unsubscribe(PriceProcessor priceProcessor) {
        System.out.println("unsubscribe client. priceProcessor=" + priceProcessor);
        listSubscribers.remove(priceProcessor);
    }
}

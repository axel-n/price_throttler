package com.price.processor.throttler;

import com.price.processor.common.PriceProcessor;
import lombok.AllArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@AllArgsConstructor
public class PriceThrottler implements PriceProcessor {

    private final List<PriceProcessor> listSubscribers = new LinkedList<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public void onPrice(String ccyPair, double rate) {
        System.out.println("PriceThrottler. ccyPair=" + ccyPair + "rate=" + rate);

        executor.submit(new Task(listSubscribers, ccyPair, rate));
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

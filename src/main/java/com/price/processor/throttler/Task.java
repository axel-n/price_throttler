package com.price.processor.throttler;

import com.price.processor.common.PriceProcessor;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Task implements Runnable {

    private final List<PriceProcessor> listSubscribers;
    private final String pair;
    private volatile double rate;

    @Override
    public void run() {
        listSubscribers
                .stream()
                .parallel()
                .forEach(client -> client.onPrice(pair, rate));
    }
}

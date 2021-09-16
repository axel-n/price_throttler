package com.price.processor.throttler;

import com.price.processor.common.PriceProcessor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class Task implements Runnable {

    private final List<PriceProcessor> listSubscribers;
    private final Map<String, Double> pairsByLastRate;
    private final String pair;

    @Override
    public void run() {

        listSubscribers
                .stream()
                .parallel()
                .forEach(client -> {
                    client.onPrice(pair, pairsByLastRate.get(pair));
                });
    }
}

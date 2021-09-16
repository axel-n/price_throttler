package com.price.processor.throttler;

import com.price.processor.common.PriceProcessor;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@AllArgsConstructor
public class PriceThrottler implements PriceProcessor {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    private static final List<PriceProcessor> listSubscribers = new LinkedList<>();
    private static final ExecutorService executor = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
    private static final Map<String, Double> pairsByLastRate = new HashMap<>();

    @Override
    public void onPrice(String pair, double rate) {
        logger.debug("ccyPair={}, ccyPair={}", pair, rate);
        pairsByLastRate.put(pair, rate);

        executor.execute(new Task(listSubscribers, pairsByLastRate, pair));
    }

    @Override
    public void subscribe(PriceProcessor priceProcessor) {
        logger.info("subscribe new client. priceProcessor={}", priceProcessor);
        listSubscribers.add(priceProcessor);
    }

    @Override
    public void unsubscribe(PriceProcessor priceProcessor) {
        logger.info("unsubscribe client. priceProcessor={}", priceProcessor);
        listSubscribers.remove(priceProcessor);
    }
}

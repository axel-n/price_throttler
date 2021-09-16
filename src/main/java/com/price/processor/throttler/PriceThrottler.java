package com.price.processor.throttler;

import com.price.processor.common.PriceProcessor;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@AllArgsConstructor
public class PriceThrottler implements PriceProcessor {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    private final List<PriceProcessor> listSubscribers = new LinkedList<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public void onPrice(String ccyPair, double rate) {
        logger.info("PriceThrottler. ccyPair={}, ccyPair={}", ccyPair, rate);
        executor.submit(new Task(listSubscribers, ccyPair, rate));
    }

    @Override
    public void subscribe(PriceProcessor priceProcessor) {
        logger.info("subscribe new client. priceProcessor=" + priceProcessor);
        listSubscribers.add(priceProcessor);
    }

    @Override
    public void unsubscribe(PriceProcessor priceProcessor) {
        logger.info("unsubscribe client. priceProcessor={}", priceProcessor);
        listSubscribers.remove(priceProcessor);
    }
}

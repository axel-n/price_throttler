package com.price.processor.throttler;

import com.price.processor.common.PriceProcessor;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class Task implements Runnable {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    private final PriceProcessor priceProcessor;
    private final Map<String, Double> pairsByLastRate;
    private final String pair;

    @Override
    public void run() {
        logger.info("before run task");
        priceProcessor.onPrice(pair, pairsByLastRate.get(pair));
    }
}

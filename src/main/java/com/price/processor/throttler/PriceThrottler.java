package com.price.processor.throttler;

import com.price.processor.client.Client;
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
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PriceThrottler implements PriceProcessor {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    private final List<Client> listSubscribers = new LinkedList<>();
    private final Thread[] threads;
    private final Map<String, Double> pairsByLastRate = new HashMap<>();

    public PriceThrottler(int maxClients) {
        threads = new Thread[maxClients];
    }

    @Override
    public void onPrice(String pair, double rate) {
        logger.debug("ccyPair={}, ccyPair={}", pair, rate);
        pairsByLastRate.put(pair, rate);

        listSubscribers.forEach(client -> {
            int clientId = client.getClientId();
            Thread prevThread = threads[client.getClientId()];

            if (prevThread == null || !prevThread.isAlive()) {
                Thread thread = new Thread(() -> listSubscribers.get(0).onPrice(pair, pairsByLastRate.get(pair)));

                threads[clientId] = thread;
                thread.start();
            }
        });
    }

    @Override
    public void subscribe(PriceProcessor priceProcessor) {
        logger.info("subscribe new client. priceProcessor={}", priceProcessor);
        listSubscribers.add((Client) priceProcessor);
    }

    @Override
    public void unsubscribe(PriceProcessor priceProcessor) {
        logger.info("unsubscribe client. priceProcessor={}", priceProcessor);
        listSubscribers.remove((Client) priceProcessor);
    }
}

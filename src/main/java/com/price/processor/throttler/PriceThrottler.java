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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PriceThrottler implements PriceProcessor {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    private final List<Client> listSubscribers = new LinkedList<>();
    private final Map<Integer, Future<Void>> futures; // monitor sent or not price
    private final Map<String, Double> pairsByLastRate = new HashMap<>();

    public PriceThrottler(int maxClients) {
        futures = new HashMap<>(maxClients);
    }

    @Override
    public void onPrice(String pair, double rate) {
        logger.debug("ccyPair={}, ccyPair={}", pair, rate);
        pairsByLastRate.put(pair, rate);

        listSubscribers
                .forEach(client -> {
                    int clientId = client.getClientId();
                    Future<Void> prevFuture = futures.get(clientId);

                    if (prevFuture == null || prevFuture.isDone()) {
                        Future<Void> future = CompletableFuture.runAsync(() ->
                                listSubscribers.get(0).onPrice(pair, pairsByLastRate.get(pair)));

                        logger.debug("send message async for clientId={}", clientId);

                        futures.put(clientId, future);
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

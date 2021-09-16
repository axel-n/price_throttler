package com.price.processor.starter;

import com.price.processor.throttler.PriceThrottler;
import com.price.processor.client.Client;
import com.price.processor.client.ClientFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PriceThrottler priceThrottler =  new PriceThrottler();

        ClientFactory clientFactory = new ClientFactory(priceThrottler);
        List<Client> clients = clientFactory.generateClients(1, 1_000,3_000);
        for (Client client : clients) {
            client.subscribe();
        }

        ExchangeMock exchange = new ExchangeMock(priceThrottler, 100);
        exchange.start();
    }
}

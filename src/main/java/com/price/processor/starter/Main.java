package com.price.processor.starter;

import com.price.processor.throttler.PriceThrottler;
import com.price.processor.client.Client;
import com.price.processor.client.ClientFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PriceThrottler priceThrottler =  new PriceThrottler();

        ClientFactory clientFactory = new ClientFactory(priceThrottler);
        List<Client> clients = clientFactory.generateClients(10, 5_000);
        for (Client client : clients) {
            client.subscribe();
        }

        ExchangeMock exchange = new ExchangeMock(priceThrottler);
        exchange.start();
    }
}

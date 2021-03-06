package com.price.processor.starter;

import com.price.processor.throttler.PriceThrottler;
import com.price.processor.client.Client;
import com.price.processor.client.ClientFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        int clientsSize = 1_000;
        PriceThrottler priceThrottler =  new PriceThrottler(clientsSize);

        ClientFactory clientFactory = new ClientFactory(priceThrottler);
        List<Client> clients = clientFactory.generateClients(clientsSize, 1,30_000);
        for (Client client : clients) {
            client.subscribe();
        }

        ExchangeMock exchange = new ExchangeMock(priceThrottler, 10);
        exchange.start();
    }
}

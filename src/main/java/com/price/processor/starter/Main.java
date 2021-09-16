package com.price.processor.starter;

import com.price.processor.PriceThrottler;
import com.price.processor.client.Client;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        PriceThrottler priceThrottler =  new PriceThrottler();

        Client client1 = new Client(priceThrottler);
        client1.subscribe();

        ExchangeMock exchange = new ExchangeMock(priceThrottler);
        exchange.start();
    }
}

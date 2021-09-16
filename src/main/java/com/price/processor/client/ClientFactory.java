package com.price.processor.client;

import com.price.processor.throttler.PriceThrottler;
import com.price.processor.helper.RandomNumberHelper;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ClientFactory {
    private final PriceThrottler priceThrottler;

    public List<Client> generateClients(int count, int maxClientDelayMs) {
        List<Client> clients = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            clients.add(new Client(i, priceThrottler, RandomNumberHelper.randomInt(maxClientDelayMs)));
        }
        return clients;
    }
}

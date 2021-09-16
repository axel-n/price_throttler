package com.price.processor.client;

import com.price.processor.throttler.PriceThrottler;
import com.price.processor.helper.RandomNumberHelper;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ClientFactory {
    private final PriceThrottler priceThrottler;

    public List<Client> generateClients(int count, int minDelay, int maxClientDelay) {
        List<Client> clients = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            int delay = RandomNumberHelper.randomInt(minDelay, maxClientDelay);

            clients.add(new Client(i, priceThrottler, delay));
        }
        return clients;
    }
}

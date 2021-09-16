package com.price.processor.client;

import com.price.processor.PriceThrottler;
import com.price.processor.helper.RandomNumberHelper;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ClientFactory {

    private final PriceThrottler priceThrottler;
    private static final int MAX_CLIENT_DELAY_MS = 1000;

    public List<Client> generateClients(int count) {
        List<Client> clients = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            clients.add(new Client(i, priceThrottler, RandomNumberHelper.randomInt(MAX_CLIENT_DELAY_MS)));
        }
        return clients;
    }
}

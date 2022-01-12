package com.pro.producerconsumer.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.sql.Time;
import java.util.*;

@Repository
public class ReplayMode {

    private final Queue<Product> history;
    private final QueuesManager queuesManager;

    @Autowired
    public ReplayMode(QueuesManager queuesManager) {
        history = new LinkedList<>();
        this.queuesManager = queuesManager;
    }

    public void addProduct(Product product) {
        history.add(product);
    }

    public void generateProducts() throws InterruptedException, CloneNotSupportedException {
        queuesManager.emptyQueuesList();
        Queue<Product> historyQueue = new LinkedList<>();

        for (Product product : history) {
            historyQueue.add((Product) product.clone());
        }

        long interval = System.currentTimeMillis();;
        while (!historyQueue.isEmpty()) {
            if (queuesManager.getQueueByIndex(0).size() == 4)
                continue;
            if (System.currentTimeMillis() - interval >= historyQueue.peek().getInterval()) {
                queuesManager.getQueueByIndex(0).put(historyQueue.remove());
                interval = System.currentTimeMillis();
            }
        }
    }
}

package com.pro.producerconsumer.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Random;

@Repository
public class ProductGenerator {

    private final ReplayMode replayMode;
    private final QueuesManager queuesManager;

    @Autowired
    public ProductGenerator(QueuesManager queuesManager, ReplayMode replayMode) {
        this.queuesManager = queuesManager;
        this.replayMode = replayMode;
    }

    public void generateProducts() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int interval = new Random().nextInt(1000) + 1000;
                    int colorNo = new Random().nextInt(16777215);
                    String color = "#" + Integer.toHexString(colorNo);
                    try {
                        if (!queuesManager.getQueueList().isEmpty()) {
                            System.out.println("Queue Size"+ queuesManager.getQueueByIndex(0).size());
                            queuesManager.getQueueByIndex(0).put(new Product(color, interval));
                            replayMode.addProduct(new Product(color, interval));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

}

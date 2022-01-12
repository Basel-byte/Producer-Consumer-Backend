package com.pro.producerconsumer.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class Machine implements Runnable{
    private List<Integer> consumedQueuesIds;
    private int productQueueId;
    private String color;
    private final QueuesManager queuesManager;
    private volatile Product product;
    private Object lock = new Object();

    @Autowired
    public Machine(QueuesManager queuesManager) {
        this.queuesManager = queuesManager;
        consumedQueuesIds = new ArrayList<>();
        color = "#FFFFFF";
    }

    public List<Integer> getConsumedQueuesIds() {
        return consumedQueuesIds;
    }

    public void setConsumedQueuesIds(List<Integer> consumedQueuesIds) {
        this.consumedQueuesIds = consumedQueuesIds;
    }

    public int getProductQueueId() {
        return productQueueId;
    }

    public void setProductQueueId(int productQueueId) {
        this.productQueueId = productQueueId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void addToConsumedQueuesIds(int id) {
        consumedQueuesIds.add(id);
    }

    private void consume() throws InterruptedException {
        while (true) {
            for (Integer consumedQueuesId : consumedQueuesIds) {
                if (product == null) {
                    System.out.println("Consuming from queue of index..." + consumedQueuesId + "of current size " + queuesManager.getQueueByIndex(consumedQueuesId).size());
                    product = queuesManager.getQueueByIndex(consumedQueuesId).take();
                    setColor(product.getColor());
                    Thread.sleep(new Random().nextInt(500) + 500);
                }
            }
        }
    }

    private void produce() throws InterruptedException {
        while (true) {
            if (product != null && queuesManager.getQueueByIndex(productQueueId).size() != 4) {
                System.out.println("Producing to queue of index..."+ productQueueId + "of current size "+ queuesManager.getQueueByIndex(productQueueId).size());
                queuesManager.getQueueByIndex(productQueueId).put(product);
                product = null;
            }
            int indexLastQueue = queuesManager.getQueueList().size() - 1;
            int sizeOfLastQueue = queuesManager.getQueueByIndex(indexLastQueue).size();
            if (sizeOfLastQueue == 4) {
                queuesManager.getQueueByIndex(indexLastQueue).take();
            }
        }
    }

    @Override
    public void run() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
    }
}

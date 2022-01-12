package com.pro.producerconsumer.model;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Repository
public class QueuesManager {
    private List<BlockingQueue<Product>> queueList;

    public QueuesManager() {
        queueList = new ArrayList<>();
    }

    public List<BlockingQueue<Product>> getQueueList() {
        return queueList;
    }

    public void addQueue() {
        queueList.add(new ArrayBlockingQueue<Product>(4));
    }
    public BlockingQueue<Product> getQueueByIndex(int index) {
        return queueList.get(index);
    }

    public void emptyQueuesList() {
        for(BlockingQueue<Product> blockingQueue : queueList) {
            blockingQueue.clear();
        }
    }
}

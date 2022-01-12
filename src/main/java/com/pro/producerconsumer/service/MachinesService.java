package com.pro.producerconsumer.service;

import com.pro.producerconsumer.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class MachinesService {

    private List<Machine> machineList;
    ProductGenerator productGenerator;
    ReplayMode replayMode;
    private ExecutorService executorService;
    private final DesignerService designerService;
    private final QueuesManager queuesManager;

    @Autowired
    public MachinesService(DesignerService designerService, ProductGenerator productGenerator, ReplayMode replayMode, QueuesManager queuesManager) throws InterruptedException {
        this.designerService = designerService;
        this.productGenerator = productGenerator;
        this.replayMode = replayMode;
        this.queuesManager = queuesManager;
    }

    public List<Machine> getMachineList() {
        return machineList;
    }

    public void setMachineList() {
        this.machineList = designerService.submitMachines();
        executorService = Executors.newFixedThreadPool(machineList.size());
    }

    public List<BlockingQueue<Product>> getQueueList() {
        return queuesManager.getQueueList();
    }

    public void simulateProgram(String mode) throws InterruptedException, CloneNotSupportedException {
        if(mode.equals("start"))
            this.productGenerator.generateProducts();
        else if (mode.equals("replay"))
            this.replayMode.generateProducts();
    }

    public void produceAndConsume() throws InterruptedException {
        for (Machine machine : machineList) {
            executorService.submit(machine);
        }
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.MINUTES);
    }

}

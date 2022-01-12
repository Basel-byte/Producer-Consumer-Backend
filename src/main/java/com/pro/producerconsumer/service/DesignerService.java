package com.pro.producerconsumer.service;

import com.pro.producerconsumer.model.Machine;
import com.pro.producerconsumer.model.QueuesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

@Service
public class DesignerService {

    private final List<Machine> machineList;
    private final QueuesManager queuesManager;


    @Autowired
    private DesignerService(QueuesManager queuesManager) {
        machineList = new ArrayList<>();
        this.queuesManager = queuesManager;
    }

    public void addMachine() {
        machineList.add(new Machine(queuesManager));
    }

    public List<Machine> submitMachines() {
        return machineList;
    }

    public void deleteMachine(int machineId) {
        machineList.remove(machineId);
    }

    public void addQueue() {
        queuesManager.addQueue();
    }

    public void setConsumeConnection(int machineId, int queueId) {
        machineList.get(machineId).addToConsumedQueuesIds(queueId);
    }

    public void setProduceConnection(int machineId, int queueId) {
        machineList.get(machineId).setProductQueueId(queueId);
        System.out.println("QUEUE LIST SIZE "+ queuesManager.getQueueList().size());
    }


}

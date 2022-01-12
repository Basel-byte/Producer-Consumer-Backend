package com.pro.producerconsumer.controller;

import com.pro.producerconsumer.model.Machine;
import com.pro.producerconsumer.model.Product;
import com.pro.producerconsumer.service.MachinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.BlockingQueue;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/simulation")
public class MachinesController {

    private final MachinesService machinesService;

    @Autowired
    public MachinesController(MachinesService machinesService) {
        this.machinesService = machinesService;
    }

    @PostMapping("/{programMode}")
    public void startSimulation(@PathVariable String programMode) throws InterruptedException, CloneNotSupportedException {
        machinesService.simulateProgram(programMode);
        machinesService.produceAndConsume();
    }

    @GetMapping("/getAllMachines")
    public List<Machine> getAllMachines() {
        return machinesService.getMachineList();
    }

    @GetMapping("/getAllQueues")
    public List<BlockingQueue<Product>> getAllQueues() {
        return machinesService.getQueueList();
    }
}

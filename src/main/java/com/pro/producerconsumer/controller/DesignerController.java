package com.pro.producerconsumer.controller;

import com.pro.producerconsumer.service.DesignerService;
import com.pro.producerconsumer.service.MachinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/design")
public class DesignerController {

    private final DesignerService designerService;
    private final MachinesService machinesService;

    @Autowired
    public DesignerController(DesignerService designerService, MachinesService machinesService) {
        this.designerService = designerService;
        this.machinesService = machinesService;
    }

    @PostMapping("/addMachine")
    public void addMachine() {
        designerService.addMachine();
    }

    @DeleteMapping("/deleteMachine/{machineId}")
    public void deleteMachine(@PathVariable int machineId) {
        designerService.deleteMachine(machineId);
    }

    @PostMapping("/addQueue")
    public void addQueue() {
        designerService.addQueue();
    }

    @PostMapping("/setProduceConnection/{machineId}/{queueId}")
    public void setProduceConnection(@PathVariable int machineId, @PathVariable int queueId) {
        designerService.setProduceConnection(machineId, queueId);
    }

    @PostMapping("/setConsumeConnection/{machineId}/{queueId}")
    public void setConsumeConnection(@PathVariable int machineId, @PathVariable int queueId) {
        designerService.setConsumeConnection(machineId, queueId);
    }

    @PostMapping("/submitMachines")
    public void submitMachines() {
        machinesService.setMachineList();
    }

}

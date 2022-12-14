package com.example.controllers;

import com.example.models.BookingTimeSlot;
import com.example.models.Branch;
import com.example.models.Vehicle;
import com.example.db.BranchDatabase;

import java.util.ArrayList;
import java.util.List;

public class BranchManagementController {
    BranchDatabase branchDatabase;

    public BranchManagementController(BranchDatabase branchDatabase) {
        this.branchDatabase = branchDatabase;
    }

    /***
     * Create a new branch
     ***/
    public boolean createBranch(String branchName, List<String> vehicleTypes){
        return branchDatabase.createNewBranch(new Branch(branchName, vehicleTypes));
    }

    /***
     * Onboard a new vehicle in specific branch
     ***/
    public boolean onboardVehicle(String branchName, String vehicleType, String vehicleId, Double price){
        return branchDatabase.createVehicle(branchDatabase.getBranch(branchName), new Vehicle(vehicleType, vehicleId, price));
    }

    /***
     * Display available vehicles between for booking between given time slots
     ***/
    public List<String> displayVehicles(String branchName, long fromTime, long toTime){
        List<Vehicle> availableVehicles = branchDatabase.displayVehicles(branchDatabase.getBranch(branchName), new BookingTimeSlot(fromTime, toTime));
        List<String> vehicleIds = new ArrayList<>();
        for(Vehicle vehicle : availableVehicles)
            vehicleIds.add(vehicle.getVehicleId());
        return vehicleIds;
    }
}

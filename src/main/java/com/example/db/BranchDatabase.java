package com.example.db;

import com.example.models.BookingTimeSlot;
import com.example.models.Branch;
import com.example.models.Vehicle;
import com.example.strategies.VehicleDisplayStrategy;

import java.util.*;

public class BranchDatabase {
    Map<String, Branch> branches = new HashMap<>();
    private VehicleDisplayStrategy vehicleDisplayStrategy;

    public BranchDatabase(VehicleDisplayStrategy vehicleDisplayStrategy) {
        this.vehicleDisplayStrategy = vehicleDisplayStrategy;
    }

    public boolean isBranchExists(String branchName){
        return branches.containsKey(branchName);
    }

    /***
     * Create a new vehicle in branch in DB
     * ***/
    public boolean createNewBranch(final Branch newBranch){
        if(isBranchExists(newBranch.getBranchName()))
            return false;
        branches.put(newBranch.getBranchName(), newBranch);
        return true;
    }

    public Branch getBranch(final String branchName){
        return branches.getOrDefault(branchName, null);
    }


    /***
     * Onboard a new vehicle in branch
     * ***/
    public boolean createVehicle(Branch branch, Vehicle vehicle) {
        if(branch == null || !branch.isVehicleTypeSupported(vehicle.getVehicleType()) || branch.isVehicleAlreadyExists(vehicle))
            return false;
        branch.addNewVehicle(vehicle);
        return true;
    }

    /***
     * Given all vehicles fine available vehicles for the given time slot
     * @param vehicles All vehicles
     * @param searchTimeSlot given time slot
     * ***/

    private List<Vehicle> availableVehicles(Set<Vehicle> vehicles, BookingTimeSlot searchTimeSlot){
        List<Vehicle> availableVehicles = new ArrayList<>();
        if(vehicles == null)
            return  availableVehicles;
        for(Vehicle vehicle : vehicles){
            // If there are no overlapping slots for current vehicle, consider it
            boolean isVehicleAvailable = true;
            for(BookingTimeSlot bookingTimeSlot : vehicle.getBookedSlots()){
                if((bookingTimeSlot.getStartTime() >= searchTimeSlot.getStartTime() && bookingTimeSlot.getStartTime() <= searchTimeSlot.getEndTime())
                        || (bookingTimeSlot.getEndTime() >= searchTimeSlot.getStartTime() && bookingTimeSlot.getEndTime() <= searchTimeSlot.getEndTime())) {
                    isVehicleAvailable = false;
                    break;
                }
            }
            if(isVehicleAvailable)
                availableVehicles.add(vehicle);
        }
        return availableVehicles;
    }

    public List<Vehicle> getAvailableVehiclesForVehicleType(Branch branch, String vehicleType, BookingTimeSlot newBookingSlot){
        return availableVehicles(branch.getAllAvailableVehiclesForVehicleType(vehicleType), newBookingSlot);
    }

    public List<Vehicle> getAvailableVehicles(Branch branch, BookingTimeSlot newBookingSlot){
        return availableVehicles(branch.getAllAvailableVehicles(), newBookingSlot);
    }

    public List<Vehicle> displayVehicles(Branch branch, BookingTimeSlot betweenTimeSlot){
        if(branch == null)
            return new ArrayList<>();
        List<Vehicle> availableVehicles = getAvailableVehicles(branch, betweenTimeSlot);
        return vehicleDisplayStrategy.displayOrderedVehicles(availableVehicles, betweenTimeSlot);
    }


}

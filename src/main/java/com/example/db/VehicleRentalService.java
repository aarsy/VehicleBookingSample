//package com.example.services;
//
//import com.example.models.Branch;
//import com.example.models.Vehicle;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
//public class VehicleRentalService{
//    Map<String, Branch> branches;
//    public VehicleRentalService(){
//        branches = new HashMap<>();
//    }
//
//    public boolean addNewBranch(String branchName, String vehicleTypes){
//        if(branches.containsKey(branchName))
//            return false;
//        Branch newBranch = new Branch(branchName, Arrays.asList(vehicleTypes.split(",")));
//        branches.put(branchName, newBranch);
//        return true;
//    }
//
//    public boolean addNewVehicle(String branchName, String vehicleType, String vehicleId, double price){
//        // If there is no branch with the specified name
//        if(!branches.containsKey(branchName))
//            return false;
//
//        // If the vehicle type is not supported
//        Branch branch = branches.get(branchName);
//        if(!branch.isVehicleTypeSupported(vehicleType)){
//            return false;
//        }
//
//        // If vehicle already exist,
//        Vehicle newVehicle = new Vehicle(vehicleType, vehicleId, price);
//        if(branch.isVehicleAlreadyExists(newVehicle)){
//            return false;
//        }else{
//            branch.addNewVehicle(newVehicle);
//        }
//        return true;
//    }
//
//    public int bookVehicle(String branchName, String vehicleType, int startTime, int endTime){
//        // If there is no branch with the specified name
//        if(!branches.containsKey(branchName))
//            return -1;
//
//        // If the vehicle type is not supported or no vehicle available of the type
//        Branch branch = branches.get(branchName);
//        if(!branch.isVehicleTypeSupported(vehicleType) || branch.getVehicleCount(vehicleType) == 0){
//            return -1;
//        }
//
////        Set<Vehicle> bookedVehicles =  branch.getAllBookedVehiclesForVehicleType(vehicleType);
//        for(Vehicle vehicle : bookedVehicles){
//
//        }
//
//
//
//    }
//
//
//}
package com.example.models;


import java.util.*;

public class Branch{
    private String branchName;
    private Map<String, Set<Vehicle>> vehicles;

    public Branch(String branchName, List<String> vehicleTypes){
        this.branchName = branchName;
        this.vehicles = new HashMap<>();
        for(String type : vehicleTypes){
            vehicles.put(type, new HashSet<>());
        }
    }

    public String getBranchName(){
        return branchName;
    }

    public boolean isVehicleTypeSupported(String vehicleType){
        return vehicles.containsKey(vehicleType);
    }

    public boolean isVehicleAlreadyExists(Vehicle vehicle){
        if(isVehicleTypeSupported(vehicle.getVehicleType())){
            return vehicles.get(vehicle.getVehicleType()).contains(vehicle);
        }
        return false;
    }

    public Map<String, Set<Vehicle>> getAllVehicles(){
        return vehicles;
    }

    public Set<Vehicle> getAllAvailableVehiclesForVehicleType(String vehicleType){
        return vehicles.get(vehicleType);
    }

    public Set<Vehicle> getAllAvailableVehicles(){
        Set<Vehicle> allVehicles = new HashSet<>();
        for(Set<Vehicle> typevehicles : vehicles.values()){
            allVehicles.addAll(typevehicles);
        }
        return allVehicles;
    }

    public void addNewVehicle(Vehicle vehicle){
        vehicles.get(vehicle.getVehicleType()).add(vehicle);
    }

    public int getVehicleCount(String vehicleType){
        return vehicles.get(vehicleType).size();
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        if (this == obj)
            return true;
        Branch other = (Branch) obj;
        return branchName.equals(other.branchName);
    }

    @Override
    public int hashCode() {
        return branchName.hashCode();
    }
}
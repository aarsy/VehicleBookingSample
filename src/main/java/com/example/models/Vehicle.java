package com.example.models;

import java.util.Set;
import java.util.TreeSet;


public class Vehicle{
    private String vehicleType;
    private String vehicleId;
    private VehicleStatus status;
    private Set<BookingTimeSlot> bookedSlots;
    private Double price;

    public Vehicle(String vehicleType, String vehicleId, Double price){
        this.vehicleType = vehicleType;
        this.vehicleId = vehicleId;
        this.price = price;
        this.status = VehicleStatus.VACANT;
        this.bookedSlots = new TreeSet<>();
    }

    public String getVehicleType(){
        return vehicleType;
    }

    public String  getVehicleId(){
        return vehicleId;
    }

    public Double getPrice(){
        return price;
    }

    public VehicleStatus getVehicleStatus(){
        return status;
    }

    public Set<BookingTimeSlot> getBookedSlots() {
        return bookedSlots;
    }

    public void setBookedSlots(Set<BookingTimeSlot> bookedSlots) {
        this.bookedSlots = bookedSlots;
    }

    public void reserveSlot(BookingTimeSlot timeSlot){
        bookedSlots.add(timeSlot);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;
        if (this == obj)
            return true;
        Vehicle other = (Vehicle) obj;
        return vehicleId.equals(other.vehicleId);
    }

    @Override
    public int hashCode() {
        return vehicleId.hashCode();
    }

}
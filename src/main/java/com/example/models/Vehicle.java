package com.example.models;

import java.util.ArrayList;
import java.util.List;


public class Vehicle{
    private String vehicleType;
    private String vehicleId;
    private VehicleStatus status;
    private List<BookingTimeSlot> bookedSlots;
    private Double price;

    public Vehicle(String vehicleType, String vehicleId, Double price){
        this.vehicleType = vehicleType;
        this.vehicleId = vehicleId;
        this.price = price;
        this.status = VehicleStatus.VACANT;
        this.bookedSlots = new ArrayList<>();
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

    public List<BookingTimeSlot> getBookedSlots() {
        return bookedSlots;
    }

    public void setBookedSlots(List<BookingTimeSlot> bookedSlots) {
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
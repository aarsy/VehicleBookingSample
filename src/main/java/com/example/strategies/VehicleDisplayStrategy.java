package com.example.strategies;

import com.example.models.BookingTimeSlot;
import com.example.models.User;
import com.example.models.Vehicle;

import java.util.List;

public interface VehicleDisplayStrategy {
    List<Vehicle> displayOrderedVehicles(List<Vehicle> vehicles, BookingTimeSlot bookingTimeSlot);
}

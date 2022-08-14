package com.example.strategies;

import com.example.models.BookingTimeSlot;
import com.example.models.User;
import com.example.models.Vehicle;

import java.util.List;

public interface VehicleSelectionStrategy {
    Vehicle selectVehicleForRider(List<Vehicle> vehicles, User user, BookingTimeSlot bookingTimeSlot);
}

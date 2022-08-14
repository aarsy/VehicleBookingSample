package com.example.strategies;

import com.example.comparator.MinimumPriceComparator;
import com.example.models.BookingTimeSlot;
import com.example.models.Vehicle;

import java.util.List;

public class MinimumCostVehicleDisplayStrategy implements VehicleDisplayStrategy {
    @Override
    public List<Vehicle> displayOrderedVehicles(List<Vehicle> vehicles, BookingTimeSlot bookingTimeSlot) {
        vehicles.sort(new MinimumPriceComparator());
        return vehicles;
    }
}

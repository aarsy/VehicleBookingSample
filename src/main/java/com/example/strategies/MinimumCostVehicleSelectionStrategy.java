package com.example.strategies;

import com.example.comparator.MinimumPriceComparator;
import com.example.models.BookingTimeSlot;
import com.example.models.User;
import com.example.models.Vehicle;

import java.util.Collections;
import java.util.List;

public class MinimumCostVehicleSelectionStrategy implements VehicleSelectionStrategy{
    @Override
    public Vehicle selectVehicleForRider(List<Vehicle> vehicles, User user, BookingTimeSlot bookingTimeSlot) {
        if(vehicles.isEmpty())
            return null;
        vehicles.sort(new MinimumPriceComparator());
        return vehicles.get(0);
    }
}

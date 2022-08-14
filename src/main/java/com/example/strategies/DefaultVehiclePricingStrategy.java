package com.example.strategies;

import com.example.models.BookingTimeSlot;
import com.example.models.Vehicle;

public class DefaultVehiclePricingStrategy implements VehiclePricingStrategy {
    @Override
    public Double findPrice(Vehicle vehicle, BookingTimeSlot bookingTimeSlot) {
        return (bookingTimeSlot.getEndTime() - bookingTimeSlot.getStartTime()) * vehicle.getPrice();
    }
}

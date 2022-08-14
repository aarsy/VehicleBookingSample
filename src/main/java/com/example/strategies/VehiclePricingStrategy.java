package com.example.strategies;

import com.example.models.BookingTimeSlot;
import com.example.models.Vehicle;

public interface VehiclePricingStrategy {
    Double findPrice(Vehicle vehicle, BookingTimeSlot bookingTimeSlot);
}

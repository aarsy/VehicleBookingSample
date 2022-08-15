package com.example.strategies;

import com.example.models.BookingTimeSlot;
import com.example.models.Vehicle;

public class DynamicVehiclePricingStrategy implements VehiclePricingStrategy {

    private double dynamicPriceIncreasePercentage;
    public DynamicVehiclePricingStrategy(double dynamicPriceIncreasePercentage){
        this.dynamicPriceIncreasePercentage = dynamicPriceIncreasePercentage;
    }
    @Override
    public Double findPrice(Vehicle vehicle, BookingTimeSlot bookingTimeSlot) {
        double totalCost = (bookingTimeSlot.getEndTime() - bookingTimeSlot.getStartTime()) * vehicle.getPrice();
        return totalCost + ( totalCost * dynamicPriceIncreasePercentage);
    }
}

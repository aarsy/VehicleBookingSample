package com.example.strategies;

import com.example.models.BookingTimeSlot;
import com.example.models.Vehicle;

public class VehiclePricingSelector {
    private double DYNAMIC_PRICING_THRESHOLD = 0.8;
    private double HIGH_DEMAND_INCREASE_PERCENTAGE = 0.1;
    public VehiclePricingStrategy getVehiclePricingStrategy(String vehicleType, int totalVehicleCount, int availableVehicleCount){
        switch (vehicleType.toUpperCase()){
            case "CAR":
                if((totalVehicleCount - availableVehicleCount)/(double)totalVehicleCount >= DYNAMIC_PRICING_THRESHOLD)
                    return new DynamicVehiclePricingStrategy(HIGH_DEMAND_INCREASE_PERCENTAGE);
            default:
                return new DefaultVehiclePricingStrategy();
        }

    }
}

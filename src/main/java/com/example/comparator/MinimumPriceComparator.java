package com.example.comparator;

import com.example.models.Vehicle;

import java.util.Comparator;

public class MinimumPriceComparator implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle o1, Vehicle o2) {
        if (o1.getPrice() > o2.getPrice())
            return 1;
        else if (o1.getPrice() < o2.getPrice())
            return -1;
        return o1.getVehicleId().compareTo(o2.getVehicleId());
    }
}

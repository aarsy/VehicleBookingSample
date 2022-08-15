package com.example.db;

import com.example.models.*;
import com.example.strategies.VehiclePricingSelector;
import com.example.strategies.VehicleSelectionStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingDatabase {

    private BranchDatabase branchDatabase;
    private VehicleSelectionStrategy vehicleSelectionStrategy;
    private VehiclePricingSelector vehiclePricingSelector;
    private Map<String, List<Booking>> bookings = new HashMap<>();

    public BookingDatabase(BranchDatabase branchDatabase,
                           VehicleSelectionStrategy vehicleSelectionStrategy,
                           VehiclePricingSelector vehiclePricingSelector) {
        this.branchDatabase = branchDatabase;
        this.vehicleSelectionStrategy = vehicleSelectionStrategy;
        this.vehiclePricingSelector = vehiclePricingSelector;
    }

    public Double bookVehicle(User user, String bookingBranchName, String vehicleType, BookingTimeSlot newBookingSlot){
        if(user == null
                || !branchDatabase.isBranchExists(bookingBranchName)
                || !branchDatabase.getBranch(bookingBranchName).isVehicleTypeSupported(vehicleType))
            return (double) -1;

        Branch bookingBranch = branchDatabase.getBranch(bookingBranchName);
        int totalVehiclesCount = bookingBranch.getVehicleCount(vehicleType);
        List<Vehicle> availableVehicles = branchDatabase.getAvailableVehiclesForVehicleType(bookingBranch, vehicleType, newBookingSlot);
        Vehicle vehicle = vehicleSelectionStrategy.selectVehicleForUser(availableVehicles, user, newBookingSlot);
        //No available vehicles
        if(vehicle == null)
            return (double) -1;
        vehicle.reserveSlot(newBookingSlot);
        /* Select the pricing strategy from Default strategy and Dynamic strategy for surcharge*/
        Double price = vehiclePricingSelector.getVehiclePricingStrategy(vehicleType, totalVehiclesCount, availableVehicles.size())
                                            .findPrice(vehicle, newBookingSlot);
        final Booking booking = new Booking(user, bookingBranch, vehicle, price, newBookingSlot);
        bookings.getOrDefault(user.getId(), new ArrayList<>()).add(booking);
        return price;
    }

    public List<Booking> bookingHistory(User user) {
        return bookings.get(user.getId());
    }


}

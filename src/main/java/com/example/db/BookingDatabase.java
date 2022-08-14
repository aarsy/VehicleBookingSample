package com.example.db;

import com.example.models.*;
import com.example.strategies.VehiclePricingStrategy;
import com.example.strategies.VehicleSelectionStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingDatabase {

    private BranchDatabase branchDatabase;
    private VehicleSelectionStrategy vehicleSelectionStrategy;
    private VehiclePricingStrategy vehiclePricingStrategy;
    private Map<String, List<Booking>> bookings = new HashMap<>();

    public BookingDatabase(BranchDatabase branchDatabase,
                           VehicleSelectionStrategy vehicleSelectionStrategy,
                           VehiclePricingStrategy vehiclePricingStrategy) {
        this.branchDatabase = branchDatabase;
        this.vehicleSelectionStrategy = vehicleSelectionStrategy;
        this.vehiclePricingStrategy = vehiclePricingStrategy;
    }

    public Double bookVehicle(User user, String bookingBranchName, String vehicleType, BookingTimeSlot newBookingSlot){
        if(user == null
                || !branchDatabase.isBranchExists(bookingBranchName)
                || !branchDatabase.getBranch(bookingBranchName).isVehicleTypeSupported(vehicleType))
            return (double) -1;

        Branch bookingBranch = branchDatabase.getBranch(bookingBranchName);
        List<Vehicle> availableVehicles = branchDatabase.getAvailableVehiclesForVehicleType(bookingBranch, vehicleType, newBookingSlot);
        Vehicle vehicle = vehicleSelectionStrategy.selectVehicleForRider(availableVehicles, user, newBookingSlot);
        //No available vehicles
        if(vehicle == null)
            return (double) -1;
        vehicle.reserveSlot(newBookingSlot);
        Double price = vehiclePricingStrategy.findPrice(vehicle, newBookingSlot);
        final Booking booking = new Booking(user, bookingBranch, vehicle, price, newBookingSlot);
        bookings.getOrDefault(user.getId(), new ArrayList<>()).add(booking);
        return price;
    }

    public List<Booking> bookingHistory(User user) {
        return bookings.get(user.getId());
    }


}

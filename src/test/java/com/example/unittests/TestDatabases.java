package com.example.unittests;

import com.example.comparator.MinimumPriceComparator;
import com.example.controllers.BranchManagementController;
import com.example.controllers.UserManagementController;
import com.example.db.BookingDatabase;
import com.example.db.BranchDatabase;
import com.example.db.UserDatabase;
import com.example.models.BookingTimeSlot;
import com.example.models.Branch;
import com.example.models.User;
import com.example.models.Vehicle;
import com.example.strategies.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TestDatabases {
    BranchDatabase branchDatabase;
    UserDatabase userDatabase;
    BookingDatabase bookingDatabase;

    @BeforeEach
    void setUp(){
        VehicleDisplayStrategy vehicleDisplayStrategy = new MinimumCostVehicleDisplayStrategy();
        VehicleSelectionStrategy vehicleSelectionStrategy = new MinimumCostVehicleSelectionStrategy();
        VehiclePricingSelector vehiclePricingSelector = new VehiclePricingSelector();

        userDatabase = new UserDatabase();
        branchDatabase = new BranchDatabase(vehicleDisplayStrategy);
        bookingDatabase = new BookingDatabase(branchDatabase, vehicleSelectionStrategy, vehiclePricingSelector);
    }

    @Test
    @DisplayName("User Registration tests")
    void testUserDatabase(){
        String userId = UUID.randomUUID().toString();
        String userName = "U1";
        User user = new User(userId, userName);
        boolean result = userDatabase.createUser(user);
        assertTrue(result, "User registration should work.");

        assertEquals(user, userDatabase.getUser(userId), "User Objects shall be equal if IDs are same");

        User user2 = new User(userId, "U2");
        boolean result1 = userDatabase.createUser(user2);
        assertFalse(result1, "User re-registration should not work");

    }

    @Test
    void testBranchDatabase(){

        String branchName1 = "B1";
        String vehicleTypes1 = "CAR,BIKE";
        String branchName2 = "B2";
        String vehicleTypes2 = "VAN,TRUCK,CAR";

        Branch branch1 = new Branch(branchName1, Arrays.asList(vehicleTypes1.split(",")));
        Branch branch2 = new Branch(branchName2, Arrays.asList(vehicleTypes2.split(",")));
        Branch branch3 = new Branch(branchName1, Arrays.asList(vehicleTypes2.split(",")));
        Branch branch4 = new Branch("B4", Arrays.asList(vehicleTypes2.split(",")));

        boolean result = branchDatabase.createNewBranch(branch1);
        assertTrue(result, "Branch '"+ branchName1 +"' creation should work.");

        boolean result1 = branchDatabase.createNewBranch(branch2);
        assertTrue(result1, "Branch '"+ branchName2 +"' creation should work.");

        assertEquals(branch1, branchDatabase.getBranch(branchName1), "Branch Objects shall be equal if IDs are same");
        assertEquals(branch2, branchDatabase.getBranch(branchName2), "Branch Objects shall be equal if IDs are same");

        boolean result3 = branchDatabase.createNewBranch(branch3);
        assertFalse(result3, "Branch '"+ branchName1 +"' re-creation should not work.");

        assertTrue(branchDatabase.isBranchExists(branchName1));
        assertFalse(branchDatabase.isBranchExists(branch4.getBranchName()));


        //Branch does not exist
        boolean result4 = branchDatabase.createVehicle(branch4, new Vehicle("CAR", "V1", 500d));
        assertFalse(result4, "Vehicle OnBoard shouldn't work as branch is not available in database");

        //Branch exists but Vehicle type doesn't exists
        boolean result5 = branchDatabase.createVehicle(branch4, new Vehicle("AUTO", "V1", 300d));
        assertFalse(result5, "Vehicle OnBoard shouldn't work as vehicle type doesn't exist in branch");

        //Successful Vehicle Onboard
        boolean result6 = branchDatabase.createVehicle(branch1, new Vehicle("CAR", "V1", 800d));
        assertTrue(result6, "Vehicle OnBoard for should work");

        //Successful Vehicle Onboard of different type
        boolean result7 = branchDatabase.createVehicle(branch1, new Vehicle("BIKE", "V4", 250d));
        assertTrue(result7, "Vehicle OnBoard of different type should work");

        //Branch exists and vehicle type is supported but vehicle with ID is already present.
        boolean result8 = branchDatabase.createVehicle(branch1, new Vehicle("CAR", "V1", 500d));
        assertFalse(result8, "Vehicle re-OnBoard with same ID should not work");

        List<Vehicle> availableVehicleList = branchDatabase.displayVehicles(branch1, new BookingTimeSlot(1,6));
        List<Vehicle> sampleVehicles = new ArrayList<>();
        sampleVehicles.add(new Vehicle("CAR", "V1", 800d));
        sampleVehicles.add(new Vehicle("BIKE", "V4", 250d));
        sampleVehicles.sort(new MinimumPriceComparator());
        assertArrayEquals(availableVehicleList.toArray(), sampleVehicles.toArray(), "Vehicle IDs from display vehicle should match.");

        List<Vehicle> availableVehicleList1 = branchDatabase.getAvailableVehiclesForVehicleType(branch1, "CAR", new BookingTimeSlot(1,6));
        List<Vehicle> sampleVehicles1 = new ArrayList<>();
        sampleVehicles1.add(new Vehicle("CAR", "V1", 800d));
        sampleVehicles1.sort(new MinimumPriceComparator());
        assertArrayEquals(availableVehicleList1.toArray(), sampleVehicles1.toArray(), "Vehicle IDs from display vehicle should match.");
    }
}

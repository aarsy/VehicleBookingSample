package com.example.unittests;

import com.example.comparator.MinimumPriceComparator;
import com.example.controllers.BranchManagementController;
import com.example.controllers.UserManagementController;
import com.example.db.BookingDatabase;
import com.example.db.BranchDatabase;
import com.example.db.UserDatabase;
import com.example.models.Vehicle;
import com.example.strategies.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestBookingFlow {
    BranchManagementController branchManagementController;
    UserManagementController userManagementController;

    @BeforeEach
    void setUp(){
        VehicleDisplayStrategy vehicleDisplayStrategy = new MinimumCostVehicleDisplayStrategy();
        VehicleSelectionStrategy vehicleSelectionStrategy = new MinimumCostVehicleSelectionStrategy();
        VehiclePricingSelector vehiclePricingSelector = new VehiclePricingSelector();

        UserDatabase userDatabase = new UserDatabase();
        BranchDatabase branchDatabase = new BranchDatabase(vehicleDisplayStrategy);
        BookingDatabase bookingDatabase = new BookingDatabase(branchDatabase, vehicleSelectionStrategy, vehiclePricingSelector);

        branchManagementController = new BranchManagementController(branchDatabase);
        userManagementController = new UserManagementController(userDatabase, bookingDatabase);
    }

    @Test
    @DisplayName("User Registration tests")
    void testUserObBoard(){
        String userId = UUID.randomUUID().toString();
        String userName = "U1";
        boolean result = userManagementController.registerUser(userId, userName);
        assertTrue(result, "User registration should work.");

        boolean result1 = userManagementController.registerUser(userId, userName);
        assertFalse(result1, "User re-registration should not work");

    }

    @Test
    void testBranchCreation(){

        String branchName1 = "B1";
        String vehicleTypes1 = "CAR,BIKE";
        String branchName2 = "B2";
        String vehicleTypes2 = "VAN,TRUCK,CAR";

        boolean result = branchManagementController.createBranch(branchName1, Arrays.asList(vehicleTypes1.split(",")));
        assertTrue(result, "Branch '"+ branchName1 +"' creation should work.");

        boolean result1 = branchManagementController.createBranch(branchName2, Arrays.asList(vehicleTypes2.split(",")));
        assertTrue(result1, "Branch '"+ branchName2 +"' creation should work.");

        boolean result3 = branchManagementController.createBranch(branchName1, Arrays.asList(vehicleTypes2.split(",")));
        assertFalse(result3, "Branch '"+ branchName1 +"' re-creation should not work.");

    }

    @Test
    void testVehicleOnBoardAndDisplay(){
        String userId = UUID.randomUUID().toString();
        String userName = "U1";
        userManagementController.registerUser(userId, userName);

        String branchName1 = "B1";
        String vehicleTypes1 = "CAR,BIKE";
        String branchName3 = "B3";

        branchManagementController.createBranch(branchName1, Arrays.asList(vehicleTypes1.split(",")));

        //Branch does not exist
        boolean result3 = branchManagementController.onboardVehicle(branchName3, "CAR", "V1", 500d);
        assertFalse(result3, "Vehicle OnBoard shouldn't work as branch is not available in database");

        //Branch exists but Vehicle type doesn't exists
        boolean result4 = branchManagementController.onboardVehicle(branchName1, "AUTO", "V1", 300d);
        assertFalse(result4, "Vehicle OnBoard shouldn't work as vehicle type doesn't exist in branch");

        //Successful 1st Vehicle Onboard
        boolean result5 = branchManagementController.onboardVehicle(branchName1, "CAR", "V1", 800d);
        assertTrue(result5, "Vehicle OnBoard for 1st time should work");

        //Successful 2nd Vehicle Onboard of same type
        boolean result6 = branchManagementController.onboardVehicle(branchName1, "CAR", "V3", 500d);
        assertTrue(result6, "Another Vehicle OnBoard of same type should work");

        //Successful Vehicle Onboard of different type
        boolean result7 = branchManagementController.onboardVehicle(branchName1, "BIKE", "V4", 250d);
        assertTrue(result7, "Vehicle OnBoard of different type should work");

        //Branch exists and vehicle type is supported but vehicle with ID is already present.
        boolean result8 = branchManagementController.onboardVehicle(branchName1, "CAR", "V1", 500d);
        assertFalse(result8, "Vehicle re-OnBoard with same ID should not work");

        List<String> vehicleIds = branchManagementController.displayVehicles(branchName1, 1, 6);
        List<String> sampleIds = Arrays.asList("V4", "V3", "V1");
        assertArrayEquals(sampleIds.toArray(), vehicleIds.toArray(), "Vehicle IDs from display vehicle should match.");
    }

    @Test
    void testVehicleBookingDisplay(){
        String userId = UUID.randomUUID().toString();
        String userName = "U1";
        userManagementController.registerUser(userId, userName);

        String branchName1 = "B1";
        String vehicleTypes = "CAR,BIKE,VAN";

        branchManagementController.createBranch(branchName1, Arrays.asList(vehicleTypes.split(",")));

        branchManagementController.onboardVehicle(branchName1, "CAR", "V1", 500d);
        branchManagementController.onboardVehicle(branchName1, "CAR", "V2", 1000d);
        branchManagementController.onboardVehicle(branchName1, "CAR", "V3", 800d);
        branchManagementController.onboardVehicle(branchName1, "CAR", "V4", 800d);
        branchManagementController.onboardVehicle(branchName1, "CAR", "V5", 800d);
        branchManagementController.onboardVehicle(branchName1, "CAR", "V6", 800d);
        branchManagementController.onboardVehicle(branchName1, "CAR", "V7", 800d);
        branchManagementController.onboardVehicle(branchName1, "BIKE", "V8", 250d);
        branchManagementController.onboardVehicle(branchName1, "BIKE", "V9", 300d);
        branchManagementController.onboardVehicle(branchName1, "BUS", "V10", 2500d);

        Double result = userManagementController.bookVehicle(userId, branchName1, "VAN", 1, 5);
        assertEquals(-1.0, (double)result, "vehicleType VAN doesn't exist in database");
        Double result1 = userManagementController.bookVehicle(userId, branchName1, "CAR", 1, 3);
        assertEquals(1000.0, (double)result1, "Booking of CAR should work");
        Double result2 = userManagementController.bookVehicle(userId, branchName1, "CAR", 1, 3);
        assertEquals(1600.0, (double)result2, "Booking of CAR should work");
        Double result3 = userManagementController.bookVehicle(userId, branchName1, "BIKE", 6, 7);
        assertEquals(250.0, (double)result3, "Booking of BIKE should work");
        Double result4 = userManagementController.bookVehicle(userId, branchName1, "CAR", 1, 8);
        assertEquals(5600.0, (double)result4, "Booking of CAR should work");



        List<String> vehicleIds = branchManagementController.displayVehicles(branchName1, 1, 5);
        //Arranged with cost in ascending order, if cost is same then vehicle ID
        List<String> sampleIds = Arrays.asList("V8", "V9", "V5", "V6", "V7", "V2");

        assertArrayEquals(sampleIds.toArray(), vehicleIds.toArray(), "Vehicle IDs from display vehicle should match.");

        //Will book 1st vehicle as earlier it was booked from 6 - 7
        Double result5 = userManagementController.bookVehicle(userId, branchName1, "BIKE", 1, 4);
        assertEquals(750.0, (double)result5, "Booking of BIKE should work");

        Double result6 = userManagementController.bookVehicle(userId, branchName1, "BIKE", 1, 4);
        assertEquals(900, (double)result6, "Booking of BIKE should work");

        Double result7 = userManagementController.bookVehicle(userId, branchName1, "CAR", 1, 3);
        assertEquals(1600.0, (double)result7, "Booking of CAR should work");
        Double result8 = userManagementController.bookVehicle(userId, branchName1, "CAR", 1, 3);
        assertEquals(1600.0, (double)result8, "Booking of CAR should work");
        Double result9 = userManagementController.bookVehicle(userId, branchName1, "CAR", 1, 3);
        assertEquals(1600.0, (double)result9, "Booking of CAR should work");

        //Surcharge case when 80% of cars are already booked add 10% surcharge amount
        Double result10 = userManagementController.bookVehicle(userId, branchName1, "CAR", 1, 3);
        assertEquals(2200.0, (double)result10, "Booking of CAR should work for surcharge");


        List<String> vehicleIds2 = branchManagementController.displayVehicles(branchName1, 1, 6);
        List<String> sampleIds2 = new ArrayList<>();

        assertArrayEquals(sampleIds2.toArray(), vehicleIds2.toArray(), "Vehicle IDs from display vehicle should match.");
    }
}

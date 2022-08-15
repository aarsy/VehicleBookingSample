package com.example;

import com.example.controllers.BranchManagementController;
import com.example.controllers.UserManagementController;
import com.example.db.BookingDatabase;
import com.example.db.BranchDatabase;
import com.example.db.UserDatabase;
import com.example.strategies.*;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String args[]) throws Exception{
        System.out.println("Abhay raffj singh");
        System.out.println(args.length +" " + args[0]);
        if(args.length == 0){
            throw new IllegalArgumentException("Provide input file as command line argument ");
        }


        VehicleDisplayStrategy vehicleDisplayStrategy = new MinimumCostVehicleDisplayStrategy();
        VehicleSelectionStrategy vehicleSelectionStrategy = new MinimumCostVehicleSelectionStrategy();
        VehiclePricingSelector vehiclePricingSelector = new VehiclePricingSelector();

        UserDatabase userDatabase = new UserDatabase();
        BranchDatabase branchDatabase = new BranchDatabase(vehicleDisplayStrategy);
        BookingDatabase bookingDatabase = new BookingDatabase(branchDatabase, vehicleSelectionStrategy, vehiclePricingSelector);

        BranchManagementController branchManagementController = new BranchManagementController(branchDatabase);
        UserManagementController userManagementController = new UserManagementController(userDatabase, bookingDatabase);

        //Onboard a user
        String userId = UUID.randomUUID().toString();
        String userName = "Abhay";
        if(userManagementController.registerUser(userId, userName)){
            System.out.println("User " + userName + "(" + userId + ") is successfully registered");
        }


        File file = new File(args[0]);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String command = sc.nextLine();
            System.out.println(command);
            String[] nextCmd =command.split(" ");
            switch (nextCmd[0]){
                case "ADD_BRANCH": {
                    boolean result = branchManagementController.createBranch(nextCmd[1], Arrays.asList(nextCmd[2].split(",")));
                    System.out.println(String.valueOf(result).toUpperCase());
                    break;
                }
                case "ADD_VEHICLE": {
                    boolean result = branchManagementController.onboardVehicle(nextCmd[1], nextCmd[2], nextCmd[3], Double.parseDouble(nextCmd[4]));
                    System.out.println(String.valueOf(result).toUpperCase());
                    break;
                }
                case "BOOK": {
                    double result = userManagementController.bookVehicle(userId, nextCmd[1], nextCmd[2], Long.parseLong(nextCmd[3]), Long.parseLong(nextCmd[4]));
                    System.out.println(result);
                    break;
                }
                case "DISPLAY_VEHICLES": {
                    List<String> vehicleIds = branchManagementController.displayVehicles(nextCmd[1], Long.parseLong(nextCmd[2]), Long.parseLong(nextCmd[3]));
                    System.out.println(String.join(",", vehicleIds));
                    break;
                }
                default:
                    System.out.println(nextCmd[0]+ " Command is not supported.");
            }
        }
    }
}
package com.example.controllers;

import com.example.models.Booking;
import com.example.models.BookingTimeSlot;
import com.example.models.User;
import com.example.db.BookingDatabase;
import com.example.db.UserDatabase;

import java.util.List;

public class UserManagementController {
    private UserDatabase userDatabase;
    private BookingDatabase bookingDatabase;

    public UserManagementController(UserDatabase userDatabase, BookingDatabase bookingDatabase) {
        this.userDatabase = userDatabase;
        this.bookingDatabase = bookingDatabase;
    }

    public boolean registerUser(String userId, String userName){
        return userDatabase.createUser(new User(userId, userName));
    }

    public Double bookVehicle(String userId, String branchName, String vehicleType, long fromTime, long toTime){
        return bookingDatabase.bookVehicle(userDatabase.getUser(userId), branchName, vehicleType, new BookingTimeSlot(fromTime, toTime));
    }

    public List<Booking> bookingHistory(String userId){
        return bookingDatabase.bookingHistory(userDatabase.getUser(userId));
    }
}

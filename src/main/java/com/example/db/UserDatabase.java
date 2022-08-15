package com.example.db;

import com.example.models.User;

import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    // Map from UserId to User object
    Map<String, User> users = new HashMap<>();

    public boolean createUser(User newUser) {
        if (users.containsKey(newUser.getId())) {
            return false;
        }
        users.put(newUser.getId(), newUser);
        return true;
    }

    public User getUser(String userId) {
        return users.getOrDefault(userId, null);
    }
}

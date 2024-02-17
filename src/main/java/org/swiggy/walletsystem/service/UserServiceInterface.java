package org.swiggy.walletsystem.service;

import org.swiggy.walletsystem.models.entites.UserModel;

public interface UserServiceInterface {
    public UserModel registerUser(String username, String password);
    public boolean isUserPresent(String username);

    void deleteUser(String username);
}

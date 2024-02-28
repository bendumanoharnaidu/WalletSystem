package org.swiggy.walletsystem.service;

import org.swiggy.walletsystem.dto.request.UserRequest;
import org.swiggy.walletsystem.dto.response.WalletResponse;
import org.swiggy.walletsystem.execptions.UserAlreadyPresentException;
import org.swiggy.walletsystem.execptions.UserNotFoundException;
import org.swiggy.walletsystem.models.entites.UserModel;

public interface UserServiceInterface {
    public UserModel registerUser(UserRequest userRequest) throws UserAlreadyPresentException;
    public boolean isUserPresent(String username);
    public String deleteUser(String username) throws UserNotFoundException;
    public UserModel addWalletToUser(String username) throws UserNotFoundException;
}

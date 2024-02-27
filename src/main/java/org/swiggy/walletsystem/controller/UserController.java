package org.swiggy.walletsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.swiggy.walletsystem.dto.request.UserRequest;
import org.swiggy.walletsystem.dto.request.WalletRequest;
import org.swiggy.walletsystem.dto.response.WalletResponse;
import org.swiggy.walletsystem.execptions.UserAlreadyPresentException;
import org.swiggy.walletsystem.execptions.UserNotFoundException;
import org.swiggy.walletsystem.models.entites.UserModel;
import org.swiggy.walletsystem.service.UserServiceInterface;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceInterface userServiceInterface;

    @PostMapping("/")
    public ResponseEntity<UserModel> registerUser(@RequestBody UserRequest request) throws UserAlreadyPresentException {
        UserModel userModel = userServiceInterface.registerUser(request);
        return new ResponseEntity<>(userModel, HttpStatus.CREATED);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteUser() throws UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if(authentication.isAuthenticated()) {
            String response = userServiceInterface.deleteUser(username);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }

}

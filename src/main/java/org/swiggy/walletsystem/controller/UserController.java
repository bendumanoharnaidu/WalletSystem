package org.swiggy.walletsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.swiggy.walletsystem.dto.request.UserRequest;
import org.swiggy.walletsystem.models.entites.UserModel;
import org.swiggy.walletsystem.service.UserServiceInterface;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceInterface userServiceInterface;

    @PostMapping("/register")
    public ResponseEntity<UserModel> registerUser(@RequestBody UserRequest request) {
        UserModel userModel = userServiceInterface.registerUser(request.getUsername(), request.getPassword());
        return new ResponseEntity<>(userModel, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if(authentication.isAuthenticated() && userServiceInterface.isUserPresent(username)) {
            userServiceInterface.deleteUser(username);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        }
        else {
            throw new RuntimeException("User not found");
        }
    }
}

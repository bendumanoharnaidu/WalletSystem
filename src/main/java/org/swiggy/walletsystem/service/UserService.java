package org.swiggy.walletsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.swiggy.walletsystem.dto.request.UserRequest;
import org.swiggy.walletsystem.execptions.UserAlreadyPresentException;
import org.swiggy.walletsystem.execptions.UserNotFoundException;
import org.swiggy.walletsystem.models.entites.UserModel;
import org.swiggy.walletsystem.models.entites.Wallet;
import org.swiggy.walletsystem.models.enums.Currency;
import org.swiggy.walletsystem.models.repository.UserRepository;
import org.swiggy.walletsystem.models.repository.WalletRepository;

import java.util.Optional;
@Service
public class UserService implements UserServiceInterface {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserModel registerUser(UserRequest userRequest) throws UserAlreadyPresentException {
        if (isUserPresent(userRequest.getUsername())) {
            throw new UserAlreadyPresentException("User already present");
        }
        Currency currency = Currency.getCurrency(userRequest.getLocation());
        UserModel userModel = new UserModel(userRequest.getUsername() ,
                passwordEncoder.encode(userRequest.getPassword()),
                new Wallet(currency),
                userRequest.getLocation());

        return userRepository.save(userModel);
    }
    public boolean isUserPresent(String username) {
        Optional<UserModel> userModel = userRepository.findByUsername(username);
        return userModel.isPresent();
    }
    public String deleteUser(String username) throws UserNotFoundException {
        Optional<UserModel> userModel = userRepository.findByUsername(username);
        if(userModel.isPresent()) {
            userRepository.delete(userModel.get());
            return "User deleted successfully";
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }


}

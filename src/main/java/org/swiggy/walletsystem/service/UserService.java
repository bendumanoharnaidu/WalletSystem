package org.swiggy.walletsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.swiggy.walletsystem.models.entites.UserModel;
import org.swiggy.walletsystem.models.entites.Wallet;
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
    public UserModel registerUser(String username, String password) {

        UserModel userModel = new UserModel(username, passwordEncoder.encode(password), new Wallet());
        return userRepository.save(userModel);
    }
    public boolean isUserPresent(String username) {
        Optional<UserModel> userModel = userRepository.findByUsername(username);
        return userModel.isPresent();
    }
    public void deleteUser(String username) {
        Optional<UserModel> userModel = userRepository.findByUsername(username);
        if(userModel.isPresent()) {
            userRepository.delete(userModel.get());
        }
        else {
            throw new RuntimeException("User not found");
        }
    }


}

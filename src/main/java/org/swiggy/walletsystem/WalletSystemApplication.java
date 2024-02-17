package org.swiggy.walletsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class WalletSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletSystemApplication.class, args);
    }

}

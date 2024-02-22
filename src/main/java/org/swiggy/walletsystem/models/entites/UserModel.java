package org.swiggy.walletsystem.models.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String location;

    @OneToOne(cascade = CascadeType.ALL)
    private Wallet wallet;

    public UserModel(String username, String password, Wallet wallet, String location) {
        this.username = username;
        this.password = password;
        this.wallet = wallet;
        this.location = location;
    }

}

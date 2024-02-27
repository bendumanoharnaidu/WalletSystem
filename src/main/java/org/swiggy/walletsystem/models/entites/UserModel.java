package org.swiggy.walletsystem.models.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
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

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, targetEntity = Wallet.class, fetch = FetchType.LAZY)
    private List<Wallet> wallets;

    @Builder.Default
    @Column(name = "isActive")
    private boolean isActive=true;

    public UserModel(String username, String password, Wallet wallet, String location) {
        this.username = username;
        this.password = password;
        List<Wallet> wallets = List.of(wallet);
        this.wallets = wallets;
        this.location = location;
    }
    public UserModel addWallet(Wallet wallet) {
        this.wallets.add(wallet);
        return this;
    }

}

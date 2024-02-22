package org.swiggy.walletsystem.models.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;

    @Embedded
    private Money money;

    @ManyToOne(cascade = CascadeType.ALL)
    private UserModel sender;

    @ManyToOne(cascade = CascadeType.ALL)
    private UserModel receiver;

    @Column(name = "date")
    private LocalDateTime date;

    @Builder.Default
    @Column(name = "isActive")
    private boolean isActive=true;

}

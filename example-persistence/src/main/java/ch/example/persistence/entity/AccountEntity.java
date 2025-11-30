package ch.example.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "currency")
    private String currency;
}

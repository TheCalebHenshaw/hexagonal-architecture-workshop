package ch.example.persistence.entity;

import ch.example.domain.model.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    @Id
    @Column(name = "payment_id")
    private String paymentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @Column(name = "from_user_id")
    private String fromUserId;

    @Column(name = "to_user_id")
    private String toUserId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "timestamp")
    private Instant timestamp;

    @Column(name = "fraud_score")
    private Double fraudScore;
}

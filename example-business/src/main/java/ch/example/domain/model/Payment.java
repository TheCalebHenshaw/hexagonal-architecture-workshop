package ch.example.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private String paymentId;
    private PaymentStatus status;
    private String fromUserId;
    private String toUserId;
    private Double amount;
    private Instant timestamp;
    private Double fraudScore;
}

package ch.example.application.service;

import ch.example.domain.model.Payment;
import ch.example.domain.model.PaymentStatus;
import ch.example.domain.port.AccountPersistencePort;
import ch.example.domain.port.FraudDetectionPort;
import ch.example.domain.port.PaymentPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private static final double FRAUD_CHECK_THRESHOLD = 1000.0;
    private static final double FRAUD_SCORE_REJECTION_THRESHOLD = 0.8;

    private final PaymentPersistencePort paymentPersistencePort;
    private final AccountPersistencePort accountPersistencePort;
    private final FraudDetectionPort fraudDetectionPort;

    public Payment processPayment(String fromUserId, String toUserId, Double amount) {
        // Validate amount
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        // Check sender balance
        var senderAccount = accountPersistencePort.findByUserId(fromUserId)
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));

        if (senderAccount.getBalance() < amount) {
            throw new InsufficientFundsException(
                    String.format("Sender balance (%.2f) is less than payment amount (%.2f)",
                            senderAccount.getBalance(), amount)
            );
        }

        // Build payment
        var payment = Payment.builder()
                .paymentId("pay_" + UUID.randomUUID().toString().replace("-", ""))
                .fromUserId(fromUserId)
                .toUserId(toUserId)
                .amount(amount)
                .timestamp(Instant.now())
                .status(PaymentStatus.PENDING)
                .build();

        // Check for fraud if amount is over threshold
        if (amount > FRAUD_CHECK_THRESHOLD) {
            double fraudScore = fraudDetectionPort.calculateFraudScore(fromUserId, toUserId, amount);
            payment.setFraudScore(fraudScore);

            if (fraudScore >= FRAUD_SCORE_REJECTION_THRESHOLD) {
                payment.setStatus(PaymentStatus.REJECTED);
                paymentPersistencePort.save(payment);
                throw new FraudDetectedException(
                        String.format("Payment rejected due to high fraud score (%.2f)", fraudScore)
                );
            }
        }

        // Update balances
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        accountPersistencePort.save(senderAccount);

        var recipientAccount = accountPersistencePort.findByUserId(toUserId)
                .orElseThrow(() -> new IllegalArgumentException("Recipient account not found"));
        recipientAccount.setBalance(recipientAccount.getBalance() + amount);
        accountPersistencePort.save(recipientAccount);

        // Complete payment
        payment.setStatus(PaymentStatus.COMPLETED);
        return paymentPersistencePort.save(payment);
    }

    public Optional<Payment> getPayment(String paymentId) {
        return paymentPersistencePort.findById(paymentId);
    }
}

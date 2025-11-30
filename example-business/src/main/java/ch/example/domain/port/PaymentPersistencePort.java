package ch.example.domain.port;

import ch.example.domain.model.Payment;

import java.util.Optional;

public interface PaymentPersistencePort {
    Payment save(Payment payment);
    Optional<Payment> findById(String paymentId);
}

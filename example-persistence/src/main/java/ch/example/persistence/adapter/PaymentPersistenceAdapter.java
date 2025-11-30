package ch.example.persistence.adapter;

import ch.example.domain.model.Payment;
import ch.example.domain.port.PaymentPersistencePort;
import ch.example.persistence.entity.PaymentEntity;
import ch.example.persistence.repository.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements PaymentPersistencePort {
    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = toEntity(payment);
        PaymentEntity savedEntity = paymentJpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Payment> findById(String paymentId) {
        return paymentJpaRepository.findById(paymentId)
                .map(this::toDomain);
    }

    private PaymentEntity toEntity(Payment payment) {
        return PaymentEntity.builder()
                .paymentId(payment.getPaymentId())
                .status(payment.getStatus())
                .fromUserId(payment.getFromUserId())
                .toUserId(payment.getToUserId())
                .amount(payment.getAmount())
                .timestamp(payment.getTimestamp())
                .fraudScore(payment.getFraudScore())
                .build();
    }

    private Payment toDomain(PaymentEntity entity) {
        return Payment.builder()
                .paymentId(entity.getPaymentId())
                .status(entity.getStatus())
                .fromUserId(entity.getFromUserId())
                .toUserId(entity.getToUserId())
                .amount(entity.getAmount())
                .timestamp(entity.getTimestamp())
                .fraudScore(entity.getFraudScore())
                .build();
    }
}

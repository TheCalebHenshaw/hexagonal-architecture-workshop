package ch.example.web.controller;

import ch.example.application.service.PaymentService;
import ch.example.domain.model.Payment;
import ch.example.web.api.PaymentsApi;
import ch.example.web.dto.PaymentRequest;
import ch.example.web.dto.PaymentResponse;
import ch.example.web.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController implements PaymentsApi {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @Override
    public ResponseEntity<PaymentResponse> processPayment(PaymentRequest paymentRequest) {
        Payment payment = paymentService.processPayment(
                paymentRequest.getFromUserId(),
                paymentRequest.getToUserId(),
                paymentRequest.getAmount()
        );

        PaymentResponse response = paymentMapper.toDto(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<PaymentResponse> getPayment(String paymentId) {
        return paymentService.getPayment(paymentId)
                .map(paymentMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

package ch.example.domain.port;

public interface FraudDetectionPort {
    double calculateFraudScore(String fromUserId, String toUserId, Double amount);
}

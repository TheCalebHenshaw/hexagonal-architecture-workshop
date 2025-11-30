package ch.example.persistence.adapter;

import ch.example.domain.port.FraudDetectionPort;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SimpleFraudDetectionAdapter implements FraudDetectionPort {
    private final Random random = new Random();

    @Override
    public double calculateFraudScore(String fromUserId, String toUserId, Double amount) {
        // Simple mock fraud detection - returns random score between 0 and 1
        // In a real system, this would call an external fraud detection service
        double baseScore = random.nextDouble() * 0.5; // 0 to 0.5

        // Add risk for very high amounts
        if (amount > 5000) {
            baseScore += 0.3;
        }

        return Math.min(baseScore, 1.0);
    }
}

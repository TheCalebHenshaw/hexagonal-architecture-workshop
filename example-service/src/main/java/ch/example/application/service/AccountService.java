package ch.example.application.service;

import ch.example.domain.model.Account;
import ch.example.domain.port.AccountPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountPersistencePort accountPersistencePort;

    public Account getAccountBalance(String userId) {
        return accountPersistencePort.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found for user: " + userId));
    }
}

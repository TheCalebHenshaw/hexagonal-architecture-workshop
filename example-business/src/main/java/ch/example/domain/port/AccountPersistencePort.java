package ch.example.domain.port;

import ch.example.domain.model.Account;

import java.util.Optional;

public interface AccountPersistencePort {
    Optional<Account> findByUserId(String userId);
    Account save(Account account);
}

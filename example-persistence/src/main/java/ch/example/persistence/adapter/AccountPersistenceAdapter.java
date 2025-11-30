package ch.example.persistence.adapter;

import ch.example.domain.model.Account;
import ch.example.domain.port.AccountPersistencePort;
import ch.example.persistence.entity.AccountEntity;
import ch.example.persistence.repository.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements AccountPersistencePort {
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Optional<Account> findByUserId(String userId) {
        return accountJpaRepository.findById(userId)
                .map(this::toDomain);
    }

    @Override
    public Account save(Account account) {
        AccountEntity entity = toEntity(account);
        AccountEntity savedEntity = accountJpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    private AccountEntity toEntity(Account account) {
        return AccountEntity.builder()
                .userId(account.getUserId())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .build();
    }

    private Account toDomain(AccountEntity entity) {
        return Account.builder()
                .userId(entity.getUserId())
                .balance(entity.getBalance())
                .currency(entity.getCurrency())
                .build();
    }
}

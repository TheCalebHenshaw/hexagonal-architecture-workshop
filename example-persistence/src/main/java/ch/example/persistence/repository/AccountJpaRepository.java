package ch.example.persistence.repository;

import ch.example.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, String> {
}

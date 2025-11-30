package ch.example.web.mapper;

import ch.example.domain.model.Account;
import ch.example.web.dto.AccountBalanceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountBalanceResponse toDto(Account account);
}

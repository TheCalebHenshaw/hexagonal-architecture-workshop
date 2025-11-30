package ch.example.web.controller;

import ch.example.application.service.AccountService;
import ch.example.domain.model.Account;
import ch.example.web.api.AccountsApi;
import ch.example.web.dto.AccountBalanceResponse;
import ch.example.web.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountsApi {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @Override
    public ResponseEntity<AccountBalanceResponse> getAccountBalance(String userId) {
        Account account = accountService.getAccountBalance(userId);
        AccountBalanceResponse response = accountMapper.toDto(account);
        return ResponseEntity.ok(response);
    }
}

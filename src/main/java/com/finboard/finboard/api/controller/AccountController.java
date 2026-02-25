package com.finboard.finboard.api.controller;

import com.finboard.finboard.api.dto.AccountDTO;
import com.finboard.finboard.api.dto.CreateAccountRequest;
import com.finboard.finboard.api.dto.UpdateAccountRequest;
import com.finboard.finboard.api.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountDTO> getAllByUser() {
        Long userId = 1L;
        return accountService.getAllByUser(userId);
    }

    @GetMapping("/{id}")
    public AccountDTO getById(@PathVariable Long id) {
        Long userId = 1L;
        return accountService.getById(id, userId);
    }

    @PostMapping
    public AccountDTO create(@Valid @RequestBody CreateAccountRequest request) {
        Long userId = 1L;
        return accountService.create(request, userId);
    }

    @PutMapping("/{id}")
    public AccountDTO update(@PathVariable Long id, @Valid @RequestBody UpdateAccountRequest request) {
        Long userId = 1L;
        return accountService.update(id, request, userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Long userId = 1L;
        accountService.delete(id, userId);
    }
}

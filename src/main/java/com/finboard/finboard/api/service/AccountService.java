package com.finboard.finboard.api.service;

import com.finboard.finboard.api.dto.AccountDTO;
import com.finboard.finboard.api.dto.CreateAccountRequest;
import com.finboard.finboard.api.dto.UpdateAccountRequest;
import com.finboard.finboard.api.entity.Account;
import com.finboard.finboard.api.entity.User;
import com.finboard.finboard.api.exception.ResourceNotFoundException;
import com.finboard.finboard.api.repository.AccountRepository;
import com.finboard.finboard.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    AccountService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public AccountDTO create(CreateAccountRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        Account account = new Account();
        account.setName(request.getName());
        account.setType(request.getType());
        account.setBalance(request.getBalance());
        account.setCurrency(request.getCurrency());
        account.setUser(user);
        account.setCreatedAt(LocalDateTime.now());

        Account saved = accountRepository.save(account);

        AccountDTO dto = new AccountDTO();
        dto.setId(saved.getId());
        dto.setName(saved.getName());
        dto.setType(saved.getType());
        dto.setBalance(saved.getBalance());
        dto.setCurrency(saved.getCurrency());
        dto.setCreatedAt(saved.getCreatedAt());

        return dto;
    }

    public List<AccountDTO> getAllByUser(Long userId) {
        List<Account> accounts = accountRepository.findByUserId(userId);
        List<AccountDTO> dtos = new ArrayList<>();

        for (Account account : accounts) {
            AccountDTO dto = new AccountDTO();
            dto.setId(account.getId());
            dto.setName(account.getName());
            dto.setType(account.getType());
            dto.setBalance(account.getBalance());
            dto.setCurrency(account.getCurrency());
            dto.setCreatedAt(LocalDateTime.now());
            dtos.add(dto);
        }
        return dtos;
    }

    public AccountDTO getById(Long id, Long userId){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if(!account.getUser().getId().equals(userId)){
            throw new ResourceNotFoundException("Account not found");
        }

        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setName(account.getName());
        dto.setType(account.getType());
        dto.setBalance(account.getBalance());
        dto.setCurrency(account.getCurrency());
        dto.setCreatedAt(account.getCreatedAt());
        return dto;
    }

    public AccountDTO update(Long id, UpdateAccountRequest req, Long userId){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if(!account.getUser().getId().equals(userId)){
            throw new ResourceNotFoundException("Account not found");
        }

        account.setName(req.getName());
        account.setCurrency(req.getCurrency());

        Account saved = accountRepository.save(account);

        AccountDTO dto = new AccountDTO();
        dto.setId(saved.getId());
        dto.setName(saved.getName());
        dto.setType(saved.getType());
        dto.setBalance(saved.getBalance());
        dto.setCurrency(saved.getCurrency());
        dto.setCreatedAt(saved.getCreatedAt());
        return dto;
    }

    public void delete(Long id, Long userId) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if(!account.getUser().getId().equals(userId)){
            throw new ResourceNotFoundException("Account not found");
        }

        accountRepository.delete(account);

    }

}

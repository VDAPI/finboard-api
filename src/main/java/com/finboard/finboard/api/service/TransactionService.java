package com.finboard.finboard.api.service;

import com.finboard.finboard.api.dto.CreateTransactionRequest;
import com.finboard.finboard.api.dto.TransactionDTO;
import com.finboard.finboard.api.dto.UpdateTransactionRequest;
import com.finboard.finboard.api.entity.Account;
import com.finboard.finboard.api.entity.Category;
import com.finboard.finboard.api.entity.Transaction;
import com.finboard.finboard.api.entity.TransactionType;
import com.finboard.finboard.api.exception.ResourceNotFoundException;
import com.finboard.finboard.api.repository.AccountRepository;
import com.finboard.finboard.api.repository.CategoryRepository;
import com.finboard.finboard.api.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    TransactionService(TransactionRepository transactionRepository,
                       AccountRepository accountRepository,
                       CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
    }

    public TransactionDTO create(CreateTransactionRequest request, Long userId) {
        Account account = getOwnedAccount(request.getAccountId(), userId);
        Category category = getCategory(request.getCategoryId());

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setDescription(request.getDescription());
        transaction.setDate(request.getDate());
        transaction.setAccount(account);
        transaction.setCategory(category);

        applyToBalance(account, request.getType(), request.getAmount());
        accountRepository.save(account);

        Transaction saved = transactionRepository.save(transaction);
        return toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> getAll(Long userId, Long accountId, Long categoryId, LocalDate from, LocalDate to) {
        List<Transaction> transactions =
                transactionRepository.findByUserWithFilters(userId, accountId, categoryId, from, to);

        List<TransactionDTO> dtos = new ArrayList<>();
        for (Transaction transaction : transactions) {
            dtos.add(toDTO(transaction));
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public TransactionDTO getById(Long id, Long userId) {
        Transaction transaction = getOwnedTransaction(id, userId);
        return toDTO(transaction);
    }

    public TransactionDTO update(Long id, UpdateTransactionRequest request, Long userId) {
        Transaction transaction = getOwnedTransaction(id, userId);
        Category category = getCategory(request.getCategoryId());
        Account account = transaction.getAccount();

        // Reverse the old effect, then apply the new one so the balance stays correct.
        reverseFromBalance(account, transaction.getType(), transaction.getAmount());
        applyToBalance(account, request.getType(), request.getAmount());
        accountRepository.save(account);

        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setDescription(request.getDescription());
        transaction.setDate(request.getDate());
        transaction.setCategory(category);

        Transaction saved = transactionRepository.save(transaction);
        return toDTO(saved);
    }

    public void delete(Long id, Long userId) {
        Transaction transaction = getOwnedTransaction(id, userId);
        Account account = transaction.getAccount();

        reverseFromBalance(account, transaction.getType(), transaction.getAmount());
        accountRepository.save(account);

        transactionRepository.delete(transaction);
    }

    private Account getOwnedAccount(Long accountId, Long userId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        if (!account.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Account not found");
        }
        return account;
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    private Transaction getOwnedTransaction(Long id, Long userId) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        if (!transaction.getAccount().getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Transaction not found");
        }
        return transaction;
    }

    private void applyToBalance(Account account, TransactionType type, BigDecimal amount) {
        BigDecimal balance = account.getBalance();
        if (type == TransactionType.INCOME) {
            account.setBalance(balance.add(amount));
        } else {
            account.setBalance(balance.subtract(amount));
        }
    }

    private void reverseFromBalance(Account account, TransactionType type, BigDecimal amount) {
        BigDecimal balance = account.getBalance();
        if (type == TransactionType.INCOME) {
            account.setBalance(balance.subtract(amount));
        } else {
            account.setBalance(balance.add(amount));
        }
    }

    private TransactionDTO toDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType());
        dto.setDescription(transaction.getDescription());
        dto.setDate(transaction.getDate());
        dto.setAccountId(transaction.getAccount().getId());
        dto.setAccountName(transaction.getAccount().getName());
        dto.setCategoryId(transaction.getCategory().getId());
        dto.setCategoryName(transaction.getCategory().getName());
        dto.setCreatedAt(transaction.getCreatedAt());
        return dto;
    }
}

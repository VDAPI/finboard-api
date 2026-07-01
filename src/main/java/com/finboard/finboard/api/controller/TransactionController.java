package com.finboard.finboard.api.controller;

import com.finboard.finboard.api.dto.CreateTransactionRequest;
import com.finboard.finboard.api.dto.TransactionDTO;
import com.finboard.finboard.api.dto.UpdateTransactionRequest;
import com.finboard.finboard.api.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<TransactionDTO> getAll(
            @RequestParam(required = false) Long accountId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        Long userId = 1L;
        return transactionService.getAll(userId, accountId, categoryId, from, to);
    }

    @GetMapping("/{id}")
    public TransactionDTO getById(@PathVariable Long id) {
        Long userId = 1L;
        return transactionService.getById(id, userId);
    }

    @PostMapping
    public TransactionDTO create(@Valid @RequestBody CreateTransactionRequest request) {
        Long userId = 1L;
        return transactionService.create(request, userId);
    }

    @PutMapping("/{id}")
    public TransactionDTO update(@PathVariable Long id, @Valid @RequestBody UpdateTransactionRequest request) {
        Long userId = 1L;
        return transactionService.update(id, request, userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Long userId = 1L;
        transactionService.delete(id, userId);
    }
}

package com.finboard.finboard.api.dto;

import com.finboard.finboard.api.entity.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Long id;
    private BigDecimal amount;
    private TransactionType type;
    private String description;
    private LocalDate date;
    private Long accountId;
    private String accountName;
    private Long categoryId;
    private String categoryName;
    private LocalDateTime createdAt;
}

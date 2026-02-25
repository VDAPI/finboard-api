package com.finboard.finboard.api.dto;

import com.finboard.finboard.api.entity.AccountType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountDTO {
    private Long id;
    private String name;
    private AccountType type;
    private BigDecimal balance;
    private String currency;
    private LocalDateTime createdAt;
}

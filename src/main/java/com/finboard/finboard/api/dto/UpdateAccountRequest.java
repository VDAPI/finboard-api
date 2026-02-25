package com.finboard.finboard.api.dto;

import lombok.Data;

@Data
public class UpdateAccountRequest {
    private String name;
    private String currency;
}

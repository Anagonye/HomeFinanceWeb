package com.example.homefinanceweb.financialStatement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@ToString
public class FinStateModel {
    private String category;
    private BigDecimal totalAmount;
    private int quantity;
    private BigDecimal participation;
}

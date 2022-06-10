package com.example.homefinanceweb.financialStatement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class FinStateSumModel {
    private BigDecimal sum;
    private int quantity;

}

package com.example.homefinanceweb.budgetItem.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseDetails extends BudgetItemDetails {

    private int quantity;
    private BigDecimal participationInCategory;
    private BigDecimal totalValueCurrentMonth;
    private BigDecimal totalValueCurrentYear;
    private BigDecimal priceOfLatestSuchExpense;
    private BigDecimal percentageDifferenceToLatest;
    private BigDecimal averageValue;
    private BigDecimal percentageDifferenceToAverage;

    







}

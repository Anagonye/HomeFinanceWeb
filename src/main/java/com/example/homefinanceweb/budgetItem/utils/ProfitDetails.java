package com.example.homefinanceweb.budgetItem.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProfitDetails extends BudgetItemDetails {

    private int quantity;
    private BigDecimal participationInCategory;
    private BigDecimal totalValueCurrentMonth;
    private BigDecimal totalValueCurrentYear;
    private BigDecimal priceOfLatestSuchProfit;
    private BigDecimal percentageDifferenceToLatest;
    private BigDecimal averageValue;
    private BigDecimal percentageDifferenceToAverage;
}

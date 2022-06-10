package com.example.homefinanceweb.budgetItem.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class BalanceCounter {
    private  final ExpenseCalculator expenseCalculator;
    private  final ProfitCalculator profitCalculator;



    public BigDecimal getBalanceMonth(){
        return profitCalculator.sumValueCurrentMonth().subtract(expenseCalculator.sumOfExpensesCurrentMonth());
    }
    public BigDecimal getBalanceYear(){
        return profitCalculator.sumValueCurrentYear().subtract(expenseCalculator.sumValueCurrentYear());
    }
}

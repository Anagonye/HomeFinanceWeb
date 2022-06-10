package com.example.homefinanceweb.budgetItem.utils;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseInfos {
    private ExpenseTopModel topExpenseYearByValue;
    private ExpenseTopModel topExpenseYearByQuantity;
    private ExpenseTopModel topCategoryYearByValue;
    private ExpenseTopModel topCategoryYearByQuantity;
    private ExpenseTopModel topCategoryMonthByValue;
    private ExpenseTopModel topCategoryMonthByQuantity;
}

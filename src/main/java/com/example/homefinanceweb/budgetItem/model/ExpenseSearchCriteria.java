package com.example.homefinanceweb.budgetItem.model;

import com.example.homefinanceweb.appuser.AppUser;
import com.example.homefinanceweb.budgetItem.parameters.model.expense.ExpenseCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExpenseSearchCriteria {
    private String name;
    private ExpenseCategory expenseCategory;
    private LocalDateTime start;
    private LocalDateTime end;
    private AppUser appUser;

}

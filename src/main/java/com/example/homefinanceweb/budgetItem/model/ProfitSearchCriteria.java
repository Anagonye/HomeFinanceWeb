package com.example.homefinanceweb.budgetItem.model;

import com.example.homefinanceweb.appuser.AppUser;
import com.example.homefinanceweb.budgetItem.parameters.model.expense.ExpenseCategory;
import com.example.homefinanceweb.budgetItem.parameters.model.profit.ProfitCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ProfitSearchCriteria {
    private String name;
    private ProfitCategory profitCategory;
    private LocalDateTime start;
    private LocalDateTime end;
    private AppUser appUser;
}

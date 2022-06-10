package com.example.homefinanceweb.budgetItem.model;

import com.example.homefinanceweb.appuser.AppUser;
import com.example.homefinanceweb.budgetItem.model.BudgetItem;
import com.example.homefinanceweb.budgetItem.parameters.model.expense.ExpenseCategory;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Expense extends BudgetItem {
    @NotNull
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;

    public Expense(String name, BigDecimal value,  LocalDateTime savedAt, AppUser appUser, ExpenseCategory expenseCategory) {
        super(name, value,  savedAt, appUser);
        this.expenseCategory = expenseCategory;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", savedAt=" + savedAt +
                ", expenseCategory=" + expenseCategory +
                '}';
    }
}

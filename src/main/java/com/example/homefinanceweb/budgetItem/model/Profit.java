package com.example.homefinanceweb.budgetItem.model;

import com.example.homefinanceweb.appuser.AppUser;
import com.example.homefinanceweb.budgetItem.parameters.model.profit.ProfitCategory;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Profit extends BudgetItem {
    @NotNull
    @Enumerated(EnumType.STRING)
    private ProfitCategory profitCategory;

    public Profit(String name, BigDecimal value,  LocalDateTime savedAt, AppUser appUser, ProfitCategory profitCategory) {
        super(name, value,  savedAt, appUser);
        this.profitCategory = profitCategory;
    }
}

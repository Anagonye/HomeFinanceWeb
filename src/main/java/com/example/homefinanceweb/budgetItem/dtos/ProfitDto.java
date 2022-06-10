package com.example.homefinanceweb.budgetItem.dtos;

import com.example.homefinanceweb.budgetItem.parameters.model.profit.ProfitCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProfitDto extends BudgetItemDto {

    private ProfitCategory profitCategory;

    public ProfitDto(String name, BigDecimal value,  ProfitCategory profitCategory) {
        super(name, value);
        this.profitCategory = profitCategory;
    }

}

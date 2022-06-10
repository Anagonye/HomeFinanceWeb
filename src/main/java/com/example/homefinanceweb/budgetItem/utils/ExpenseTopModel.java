package com.example.homefinanceweb.budgetItem.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseTopModel {
    private String name;
    private BigDecimal value;



    public void empty(){
        this.setName("None");
        this.setValue(BigDecimal.ZERO);
    }

}

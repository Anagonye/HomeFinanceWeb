package com.example.homefinanceweb.budgetItem.parameters.model.profit;


import lombok.Getter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum ProfitCategory {
    SALARY("Salary"),
    DEBT_REPAYMENT("Debt repayment"),
    EXTRA_JOB("Extra job"),
    INVESTMENTS("Investments");


    private final String categoryName;

    ProfitCategory(String categoryName){
        this.categoryName = categoryName;
    }

    public static List<ProfitCategory> profitCategoryNames(){

        return new ArrayList<>(Arrays.asList(ProfitCategory.values()));
    }
}



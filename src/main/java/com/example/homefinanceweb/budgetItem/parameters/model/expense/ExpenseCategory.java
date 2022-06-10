package com.example.homefinanceweb.budgetItem.parameters.model.expense;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Getter
public enum ExpenseCategory {
    FOOD("Food"),
    BILL("Bill"),
    RENT("Rent"),
    ENTERTAINMENT("Entertainment"),
    TRANSPORT("Transport"),
    HEALTH("Health"),
    CLOTHES("Clothes"),
    GIFT("Gift"),
    INVESTMENT("Investments"),
    DEBT("Debt"),
    PERSONAL("Personal"),
    UTILITIES("Utilities"),
    OTHER("Other");

    private final String categoryName;

    ExpenseCategory(String categoryName){
        this.categoryName = categoryName;
    }

    public static List<ExpenseCategory> expenseCategoryNames(){

        return (List<ExpenseCategory>) new ArrayList(Arrays.asList(ExpenseCategory.values()));
    }












}

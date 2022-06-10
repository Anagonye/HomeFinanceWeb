package com.example.homefinanceweb.budgetItem.dtomappers;

import com.example.homefinanceweb.budgetItem.model.Expense;
import com.example.homefinanceweb.budgetItem.model.Profit;
import com.example.homefinanceweb.budgetItem.dtos.ExpenseDto;
import com.example.homefinanceweb.budgetItem.dtos.ProfitDto;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BudgetItemDtoMapper {

    public static ExpenseDto map(Expense expense){
        ExpenseDto dto = new ExpenseDto();
        dto.setId(expense.getId());
        dto.setName(expense.getName());
        dto.setValue(expense.getValue());
        dto.setExpenseCategory(expense.getExpenseCategory());
        dto.setSavedAt(expense.getSavedAt());
        dto.setAppUser(expense.getAppUser());

        return dto;

    }

    public static Expense map(ExpenseDto dto){
        Expense expense = new Expense();
        expense.setId(dto.getId());
        expense.setName(dto.getName());
        expense.setValue(dto.getValue());
        expense.setExpenseCategory(dto.getExpenseCategory());
        expense.setSavedAt(dto.getSavedAt());
        expense.setAppUser(dto.getAppUser());


        return expense;

    }

    public static ProfitDto map(Profit profit){
        ProfitDto dto = new ProfitDto();
        dto.setId(profit.getId());
        dto.setName(profit.getName());
        dto.setValue(profit.getValue());
        dto.setProfitCategory(profit.getProfitCategory());
        dto.setSavedAt(profit.getSavedAt());
        dto.setAppUser(profit.getAppUser());

        return dto;
    }

    public static Profit map(ProfitDto dto){
        Profit profit = new Profit();
        profit.setId(dto.getId());
        profit.setName(dto.getName());
        profit.setValue(dto.getValue());
        profit.setProfitCategory(dto.getProfitCategory());
        profit.setSavedAt(dto.getSavedAt());
        profit.setAppUser(dto.getAppUser());

        return profit;
    }

    public final Function<Profit, ProfitDto> mapperProfit = profit -> {
        ProfitDto dto = new ProfitDto();
        dto.setId(profit.getId());
        dto.setName(profit.getName());
        dto.setValue(profit.getValue());
        dto.setProfitCategory(profit.getProfitCategory());
        dto.setSavedAt(profit.getSavedAt());
        dto.setAppUser(profit.getAppUser());

        return dto;
    };
    @Bean
    public Function<Profit, ProfitDto> getMapper() {
        return mapperProfit;
    }


    public final Function<Expense, ExpenseDto> mapperExpense = expense -> {
        ExpenseDto dto = new ExpenseDto();
        dto.setId(expense.getId());
        dto.setName(expense.getName());
        dto.setValue(expense.getValue());
        dto.setExpenseCategory(expense.getExpenseCategory());
        dto.setSavedAt(expense.getSavedAt());
        dto.setAppUser(expense.getAppUser());


        return dto;
    };
    @Bean
    public Function<Expense, ExpenseDto> getMapperExpense() {
        return mapperExpense;
    }



}


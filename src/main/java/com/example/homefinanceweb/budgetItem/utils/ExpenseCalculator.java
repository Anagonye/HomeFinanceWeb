package com.example.homefinanceweb.budgetItem.utils;

import com.example.homefinanceweb.budgetItem.dtos.ExpenseDto;
import com.example.homefinanceweb.budgetItem.model.Expense;
import com.example.homefinanceweb.budgetItem.parameters.model.expense.ExpenseCategory;
import com.example.homefinanceweb.budgetItem.repository.ExpenseRepository;
import com.example.homefinanceweb.budgetItem.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ExpenseCalculator extends BICalculator<Expense, ExpenseDto, ExpenseRepository, ExpenseService> {


    public ExpenseCalculator(CalendarTool calendarTool, ExpenseService service) {
        super(calendarTool, service);
    }

    public  BigDecimal sumOfExpensesInCategoryCurrentMonth(ExpenseCategory expenseCategory){
        return sumOfExpensesInCategory(expenseCategory, calendarTool.currentMonthStart(), calendarTool.currentMonthEnd());
    }

    public BigDecimal sumOfExpensesInCategory(ExpenseCategory expenseCategory, LocalDateTime start, LocalDateTime end ){
        return service.findAllByLocalDateBetween(start, end)
                .stream()
                .filter(expenseDto -> expenseDto.getExpenseCategory().equals(expenseCategory))
                .map(ExpenseDto::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal sumOfExpensesCurrentMonth(){
        return sumOfExpenses(calendarTool.currentMonthStart(), calendarTool.currentMonthEnd());
    }



    /**
     * Returns the sum of the expenses in given period
     * As arguments, the beginning and the end of the search period
     */
    private BigDecimal sumOfExpenses(LocalDateTime start, LocalDateTime end){
        return sumValue(service.findAllByLocalDateBetween(start, end));
    }

}

package com.example.homefinanceweb.financialStatement.controller;


import com.example.homefinanceweb.budgetItem.dtos.ExpenseDto;
import com.example.homefinanceweb.budgetItem.dtos.ProfitDto;
import com.example.homefinanceweb.budgetItem.parameters.model.expense.ExpenseCategory;
import com.example.homefinanceweb.budgetItem.parameters.model.profit.ProfitCategory;
import com.example.homefinanceweb.budgetItem.service.ExpenseService;
import com.example.homefinanceweb.budgetItem.service.ProfitService;
import com.example.homefinanceweb.budgetItem.utils.ExpenseCalculator;
import com.example.homefinanceweb.budgetItem.utils.ProfitCalculator;
import com.example.homefinanceweb.financialStatement.FinStateCreator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.LocalDate;

@Controller
@AllArgsConstructor
public class FinancialStatementController {

    private final FinStateCreator finStateCreator;
    private final ExpenseService expenseService;
    private final ProfitService profitService;
    private final ProfitCalculator profitCalculator;
    private final ExpenseCalculator expenseCalculator;



    @GetMapping("/financialStatement")
    public String financialStatement(Model model, Principal principal){

        model.addAttribute("username", principal.getName());

        model.addAttribute("finStateExpenses", finStateCreator.finStateModelExpenses());
        model.addAttribute("finStateExpensesSum", finStateCreator.finStateSumModelExpenses());
        model.addAttribute("finStateProfits", finStateCreator.finStateModelProfits());
        model.addAttribute("finStateProfitsSum", finStateCreator.finStateSumModelProfits());

        model.addAttribute("newExpense", new ExpenseDto());
        model.addAttribute("newProfit", new ProfitDto());
        model.addAttribute("categoriesListExpense", ExpenseCategory.expenseCategoryNames());
        model.addAttribute("namesListExpense", expenseService.findAllNames());
        model.addAttribute("categoriesListProfit", ProfitCategory.profitCategoryNames());
        model.addAttribute("namesListProfit", profitService.findAllNames());
        model.addAttribute("sumOfExpensesCurrentMonth", expenseCalculator.sumValueCurrentMonth());
        model.addAttribute("sumOfProfitsCurrentMonth", profitCalculator.sumValueCurrentMonth());

        model.addAttribute("yearUrl", LocalDate.now().getYear());
        model.addAttribute("monthUrl", LocalDate.now().getMonthValue());


        return "finState";
    }
}

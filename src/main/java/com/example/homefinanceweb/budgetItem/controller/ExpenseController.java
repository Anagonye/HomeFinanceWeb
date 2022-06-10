package com.example.homefinanceweb.budgetItem.controller;

import com.example.homefinanceweb.budgetItem.model.ExpensePage;
import com.example.homefinanceweb.budgetItem.model.ExpenseSearchCriteria;
import com.example.homefinanceweb.budgetItem.dtos.ExpenseDto;
import com.example.homefinanceweb.budgetItem.dtos.ProfitDto;

import com.example.homefinanceweb.budgetItem.parameters.model.expense.ExpenseCategory;
import com.example.homefinanceweb.budgetItem.parameters.model.profit.ProfitCategory;
import com.example.homefinanceweb.budgetItem.service.ExpenseService;
import com.example.homefinanceweb.budgetItem.service.ProfitService;
import com.example.homefinanceweb.budgetItem.utils.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Controller
@AllArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseDetailsFinder expenseDetailsFinder;
    private final ExpenseCalculator expenseCalculator;
    private final ExpenseTopFinder expenseTopFinder;
    private final ProfitCalculator profitCalculator;
    private final ProfitService profitService;






    @PostMapping("/save/expense")
    public String saveExpense(@ModelAttribute("newExpense") ExpenseDto expenseDto, Principal principal){

        expenseService.add(expenseDto, principal.getName());

        return "redirect:/expenses";


    }

    @GetMapping("/expenses")
    public String expenseHome(Model model, Principal principal){
        model.addAttribute("expenses", expenseService.findLatest());
        model.addAttribute("Infos", expenseTopFinder.findExpenseInfos());
        setModelAttributes(model,principal);


        return "homeExpenses";
    }


    @GetMapping("/expenses/{id}")
    public String myExpenses(@PathVariable Long id, Principal principal, Model model){

        try {
            ExpenseDto detailedExpense = expenseService.findById(id).orElseThrow();
            model.addAttribute("detailedExpense", detailedExpense);
        }catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }



        setModelAttributes(model, principal);
        model.addAttribute("expenses", expenseService.findLatest());
        model.addAttribute("expenseDetails", expenseDetailsFinder.getExpenseDetails(id));


        return "expenseDetails";
    }



    @GetMapping("/expense/delete/{id}")
    public String deleteExpense(@PathVariable("id") Long id){
            expenseService.deleteById(id);

        return "redirect:/expenses";
    }

    @PostMapping("/expense/update/{id}")
    public String updatedExpense(@PathVariable("id") Long id, @ModelAttribute("detailedExpense") ExpenseDto expenseDto){
        expenseService.update(id, expenseDto);

        return "redirect:/expenses/"+id;
    }

    @GetMapping("/expenses/history/{year}/{month}")
    public String expenseHistory(
            @PathVariable("year") int year,
            @PathVariable("month") int month,
            Model model, Principal principal,
            @ModelAttribute("criteriaExpense") ExpenseSearchCriteria expenseSearchCriteria, HttpServletRequest request){

        ExpensePage expensePage = new ExpensePage();


        setModelAttributes(model,principal);
        setModelAttributesPage(model,year,month,expenseSearchCriteria,expensePage);
        model.addAttribute("Infos", expenseTopFinder.findExpenseInfos());
        model.addAttribute("today", LocalDateTime.now().toLocalDate());
        model.addAttribute("query", getQuery(request));


        return "expensehis";
    }
    @GetMapping("/expenses/history/{year}/{month}/expense/{id}")
    public String expenseHistoryDetails( @PathVariable("year") int year,
                                         @PathVariable("month") int month,
                                         @PathVariable("id") Long id,
                                         @ModelAttribute("criteriaExpense") ExpenseSearchCriteria expenseSearchCriteria,
                                         Model model, Principal principal,
                                         HttpServletRequest request){

        ExpensePage expensePage = new ExpensePage();

        setModelAttributes(model,principal);
        setModelAttributesPage(model,year,month,expenseSearchCriteria,expensePage);

        model.addAttribute("today", LocalDateTime.now().toLocalDate());
        model.addAttribute("detailedExpense", expenseService.findById(id).orElseThrow());
        model.addAttribute("expenseDetails", expenseDetailsFinder.getExpenseDetails(id));
        model.addAttribute("query", getQuery(request));


        return "expenseHistoryDetails";

    }


    private String getQuery(HttpServletRequest request){
        if(request.getQueryString()!=null) {
            if (!request.getQueryString().isEmpty()) {
                return "?" + request.getQueryString();
            }
        }
        return "";
    }

    private void setModelAttributes(Model model, Principal principal){
        model.addAttribute("username", principal.getName());
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
    }


    private void setModelAttributesPage(Model model, int year, int month, ExpenseSearchCriteria criteria, ExpensePage expensePage){
        LocalDateTime start = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime end = start.withDayOfMonth(start.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);
        criteria.setStart(start);
        criteria.setEnd(end);
        model.addAttribute("date", start);
        model.addAttribute("expenses", expenseService.getPaginatedExpenses(expensePage, criteria).getContent());
        model.addAttribute("yearUrl", year);
        model.addAttribute("monthUrl", month);
    }













}

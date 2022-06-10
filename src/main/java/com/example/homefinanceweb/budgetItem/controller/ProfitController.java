package com.example.homefinanceweb.budgetItem.controller;

import com.example.homefinanceweb.budgetItem.model.ProfitPage;
import com.example.homefinanceweb.budgetItem.model.ProfitSearchCriteria;
import com.example.homefinanceweb.budgetItem.dtos.ExpenseDto;
import com.example.homefinanceweb.budgetItem.dtos.ProfitDto;
import com.example.homefinanceweb.budgetItem.parameters.model.expense.ExpenseCategory;
import com.example.homefinanceweb.budgetItem.parameters.model.profit.ProfitCategory;
import com.example.homefinanceweb.budgetItem.service.ExpenseService;
import com.example.homefinanceweb.budgetItem.service.ProfitService;
import com.example.homefinanceweb.budgetItem.utils.BalanceCounter;
import com.example.homefinanceweb.budgetItem.utils.ExpenseCalculator;
import com.example.homefinanceweb.budgetItem.utils.ProfitCalculator;
import com.example.homefinanceweb.budgetItem.utils.ProfitDetailsFinder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class ProfitController {
    private final ProfitService profitService;
    private final ProfitCalculator profitCalculator;
    private final ExpenseCalculator expenseCalculator;
    private final ExpenseService expenseService;
    private final ProfitDetailsFinder profitDetailsFinder;
    private final BalanceCounter balanceCounter;

    @GetMapping("/profits")
    public String profitsHome(Principal principal, Model model){

        List<ProfitDto> latestProfits = profitService.findLatest();
        setModelAttributes(model, principal);
        setModelAttributesInfos(model);
        model.addAttribute("latestProfits", latestProfits);
        model.addAttribute("yearUrl", LocalDate.now().getYear());
        model.addAttribute("monthUrl", LocalDate.now().getMonthValue());


        return "homeProfits";
    }

    @PostMapping("/profits/save")
    public String addProfit(@ModelAttribute("newProfit") ProfitDto profitDto, Principal principal){

        profitService.add(profitDto, principal.getName());

        return "redirect:/profits";
    }
    @GetMapping("/profits/{id}")
    public String myProfits(@PathVariable Long id, Model model, Principal principal){
        setModelAttributes(model, principal);
        model.addAttribute("profits", profitService.findLatest());
        model.addAttribute("detailedProfit", profitService.findById(id).orElseThrow());
        model.addAttribute("profitDetails", profitDetailsFinder.getProfitDetails(id));
        model.addAttribute("yearUrl", LocalDate.now().getYear());
        model.addAttribute("monthUrl", LocalDate.now().getMonthValue());

        return "profitDetails";
    }
    @GetMapping("/profits/history/{year}/{month}")
    public String profitHistory(@PathVariable("year") int year,
                                @PathVariable("month") int month,
                                @ModelAttribute("criteriaProfit") ProfitSearchCriteria profitSearchCriteria,
                                Model model, Principal principal, HttpServletRequest request){

        ProfitPage profitPage = new ProfitPage();

        setModelAttributesPage(model,year,month,profitSearchCriteria,profitPage);
        setModelAttributes(model, principal);
        setModelAttributesInfos(model);

        model.addAttribute("query", getQuery(request));


        return "profitHistory";

    }

    @GetMapping("/profits/history/{year}/{month}/profit/{id}")
    public String profitHistoryDetails(@PathVariable("year") int year,
                                @PathVariable("month") int month,
                                @PathVariable("id") Long id,
                                       @ModelAttribute("criteriaProfit") ProfitSearchCriteria profitSearchCriteria,
                                Model model, Principal principal,
                                       HttpServletRequest request){

        ProfitPage profitPage = new ProfitPage();
        setModelAttributesPage(model,year,month,profitSearchCriteria, profitPage);
        setModelAttributes(model, principal);

        model.addAttribute("detailedProfit", profitService.findById(id).orElseThrow());
        model.addAttribute("query", getQuery(request));
        model.addAttribute("profitDetails", profitDetailsFinder.getProfitDetails(id));


        return "profitHistoryDetails";

    }

    @PostMapping("/profit/update/{id}")
    public String updatedExpense(@PathVariable("id") Long id, @ModelAttribute("detailedProfit") ProfitDto profitDto){
        profitService.update(id, profitDto);

        return "redirect:/profits/"+id;
    }
    @GetMapping("/profits/delete/{id}")
    public String deleteExpense(@PathVariable("id") Long id){
        expenseService.deleteById(id);

        return "redirect:/profits";
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

    }

    private void setModelAttributesInfos(Model model){
        model.addAttribute("sumOfProfitsCurrentYear", profitCalculator.sumValueCurrentYear());
        model.addAttribute("balanceMonth", balanceCounter.getBalanceMonth());
        model.addAttribute("balanceYear", balanceCounter.getBalanceYear());
        model.addAttribute("dailyProfit", profitCalculator.countDailyProfit());
    }

    private void setModelAttributesPage(Model model, int year, int month, ProfitSearchCriteria criteria, ProfitPage profitPage){
        LocalDateTime start = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime end = start.withDayOfMonth(start.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);
        criteria.setStart(start);
        criteria.setEnd(end);
        model.addAttribute("date", start);
        model.addAttribute("profits", profitService.getPaginatedProfits(profitPage, criteria).getContent());
        model.addAttribute("yearUrl", year);
        model.addAttribute("monthUrl", month);
    }



}

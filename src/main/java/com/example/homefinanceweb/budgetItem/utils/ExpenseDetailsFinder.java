package com.example.homefinanceweb.budgetItem.utils;

import com.example.homefinanceweb.budgetItem.service.ExpenseService;
import com.example.homefinanceweb.budgetItem.dtos.ExpenseDto;
import com.example.homefinanceweb.budgetItem.parameters.model.expense.ExpenseCategory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class ExpenseDetailsFinder {

    private final ExpenseService service;
    private final CalendarTool calendarTool;
    private final ExpenseCalculator expenseCalculator;


    public ExpenseDetails getExpenseDetails(Long id){
        ExpenseDetails expenseDetails = new ExpenseDetails();
        expenseDetails.setQuantity(
                getQuantity(
                        id,
                        calendarTool.currentYearStart(),
                        calendarTool.currentYearEnd()
                )
        );
        expenseDetails.setParticipationInCategory(
                getParticipationInTheCategory(
                        id,
                        calendarTool.currentYearStart(),
                        calendarTool.currentYearEnd())

        );
        expenseDetails.setTotalValueCurrentMonth(
                getTotal(
                        id,
                        calendarTool.currentMonthStart(),
                        calendarTool.currentMonthEnd())
        );
        expenseDetails.setTotalValueCurrentYear(
                getTotal(
                        id,
                        calendarTool.currentYearStart(),
                        calendarTool.currentYearEnd())
        );
        expenseDetails.setPriceOfLatestSuchExpense(
                findLatest(id).getValue()
        );
        expenseDetails.setPercentageDifferenceToLatest(
                getPercentageDiffToLatest(id).subtract(BigDecimal.valueOf(100))
        );
        expenseDetails.setAverageValue(
                getAverageValue(
                        id,
                        calendarTool.currentYearStart(),
                        calendarTool.currentYearEnd())
        );
        expenseDetails.setPercentageDifferenceToAverage(
                getPercentageDiffToAverage(
                        id,
                        calendarTool.currentYearStart(),
                        calendarTool.currentYearEnd()
                ).subtract(BigDecimal.valueOf(100))
        );







        return expenseDetails;
    }


    private int getQuantity(Long id, LocalDateTime start, LocalDateTime end){
        return expenseCalculator.countQuantity(findSimilarExpenses(id, start, end ));
    }

    /**
     * looking for similar expenses in given period
     * Similar definition: Same name and expense category;
     * @param id expense id in database
     * @param start start of period
     * @param end end of period
     * @return similar expenses list
     */
    private List<ExpenseDto> findSimilarExpenses(Long id, LocalDateTime start, LocalDateTime end){
        ExpenseDto expenseDto = service.findById(id).orElseThrow();

        return service.findAllByLocalDateBetween(start, end).stream().filter(expenseDto1 -> expenseDto1.equals(expenseDto)).toList();

    }

    private BigDecimal getParticipationInTheCategory(Long id, LocalDateTime start, LocalDateTime end){
        ExpenseDto expenseDto = service.findById(id).orElseThrow();
        int allExpensesInCategory = expenseCalculator.countQuantity(
                findAllExpensesOfCategory(
                        expenseDto.getExpenseCategory(),
                        start,
                        end)
        );
        int similarExpenses = expenseCalculator.countQuantity(
                findSimilarExpenses(id, start, end)
        );

        return expenseCalculator.percentageOf(BigDecimal.valueOf(similarExpenses), BigDecimal.valueOf(allExpensesInCategory));



    }

    private List<ExpenseDto> findAllExpensesOfCategory(ExpenseCategory expenseCategory, LocalDateTime start, LocalDateTime end){
        return service.findAllByLocalDateBetween(start, end)
                .stream()
                .filter(expenseDto -> expenseDto.getExpenseCategory().equals(expenseCategory))
                .toList();
    }

    private BigDecimal getTotal(Long id, LocalDateTime start, LocalDateTime end){
        return expenseCalculator.sumValue(
                findSimilarExpenses(id, start, end)
        );
    }

    /**
     * Loooking for latest similar expense
     * Similar definition: Same name and expense category;
     * @param id expense id in database of which predecessor we are looking for
     * @return latest similar expense
     */
    private ExpenseDto findLatest(Long id){
        ExpenseDto expenseDto = service.findById(id).orElseThrow();
        List<ExpenseDto> allExpenses = service.findAllOrderBySavedAtDesc()
                .stream()
                .filter(expenseDto1 -> expenseDto1.equals(expenseDto))
                .toList();
        ExpenseDto latest = expenseDto;
        for(ExpenseDto expense: allExpenses){
            if(expense.getSavedAt()
                    .isBefore(
                            expenseDto.getSavedAt()
                    )){
                latest = expense;
                break;
            }
        }

        return latest;

    }

    private BigDecimal getAverageValue(Long id, LocalDateTime start, LocalDateTime end){
        return expenseCalculator.averageValue(findSimilarExpenses(id, start, end));
    }

    private BigDecimal getPercentageDiffToLatest(Long id){
        ExpenseDto expenseDto = service.findById(id).orElseThrow();

        return expenseCalculator.percentageOf(
                expenseDto.getValue(),
                findLatest(id).getValue());
    }
    private BigDecimal getPercentageDiffToAverage(Long id, LocalDateTime start, LocalDateTime end){
        ExpenseDto expenseDto = service.findById(id).orElseThrow();
        return expenseCalculator.percentageOf(
                expenseDto.getValue(),
                getAverageValue(id,start, end));
    }





}

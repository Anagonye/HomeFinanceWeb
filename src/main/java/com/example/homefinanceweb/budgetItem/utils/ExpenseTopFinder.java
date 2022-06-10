package com.example.homefinanceweb.budgetItem.utils;

import com.example.homefinanceweb.budgetItem.service.ExpenseService;
import com.example.homefinanceweb.budgetItem.dtos.ExpenseDto;
import com.example.homefinanceweb.budgetItem.parameters.model.expense.ExpenseCategory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ExpenseTopFinder {
    private final ExpenseService expenseService;
    private final ExpenseCalculator calculator;
    private final CalendarTool calendarTool;




    public ExpenseInfos findExpenseInfos(){
        ExpenseInfos expenseInfos = new ExpenseInfos();
        expenseInfos.setTopExpenseYearByValue(getTopExpenseYearByValue());
        expenseInfos.setTopExpenseYearByQuantity(getTopExpenseYearByQuantity());
        expenseInfos.setTopCategoryMonthByValue(getTopCategoryMonthByValue());
        expenseInfos.setTopCategoryMonthByQuantity(getTopCategoryMonthByQuantity());
        expenseInfos.setTopCategoryYearByValue(getTopCategoryYearByValue());
        expenseInfos.setTopCategoryYearByQuantity(getTopCategoryYearByQuantity());

        return expenseInfos;
    }


    private ExpenseTopModel getTopExpenseYearByValue(){

        return ExpenseTopModelMapper.mapIfPresent(findTopExpenseByValue(
                calendarTool.currentYearStart(),
                calendarTool.currentYearEnd()));

    }

    private ExpenseTopModel getTopExpenseYearByQuantity(){
        return ExpenseTopModelMapper.mapIfPresent(findTopExpenseByQuantity(
                calendarTool.currentYearStart(),
                calendarTool.currentMonthEnd()));
    }

    private ExpenseTopModel getTopCategoryMonthByValue(){
        return findCategoryWithHighestTotalAmount(
                calendarTool.currentMonthStart(),
                calendarTool.currentMonthEnd());
    }

    private ExpenseTopModel getTopCategoryMonthByQuantity(){
        return findTopCategoryByQuantity(
                calendarTool.currentMonthStart(),
                calendarTool.currentMonthEnd());
    }


    private ExpenseTopModel getTopCategoryYearByValue(){
        return findCategoryWithHighestTotalAmount(
                calendarTool.currentYearStart(),
                calendarTool.currentYearEnd());
    }
    private ExpenseTopModel getTopCategoryYearByQuantity(){
        return findTopCategoryByQuantity(
                calendarTool.currentYearStart(),
                calendarTool.currentYearEnd());
    }















    /**
     * Return expense with the highest value in given period
     * @param start period beginning
     * @param end period end
     * @return expense with the highest value
     */
    private Optional<ExpenseDto> findTopExpenseByValue(LocalDateTime start, LocalDateTime end){


        return expenseService.findAllByLocalDateBetween(start,end)
                .stream()
                .max(Comparator.comparing(ExpenseDto::getValue));

    }




    //As arguments, the beginning and the end of the search period
    private Optional<ExpenseDto> findTopExpenseByQuantity(LocalDateTime start, LocalDateTime end){
        List<ExpenseDto> expenses = expenseService.findAllByLocalDateBetween(start, end);

        if(expenses.size() !=0 ) {
            Set<String> names;

            names = expenses.stream().map(ExpenseDto::getName).collect(Collectors.toCollection(LinkedHashSet::new));

            //Setting quantity to compare
            ExpenseDto expenseDto = findAllByName(names.iterator().next(), expenses).get(0);
            expenseDto.setQuantity(findAllByName(names.iterator().next(), expenses).size());

            ExpenseDto expenseWithMaxQuantity = expenseDto;


            for (String name : names) {
                if (findAllByName(name, expenses).size() > expenseWithMaxQuantity.getQuantity()) {
                    expenseWithMaxQuantity = findAllByName(name, expenses).get(0);
                    expenseWithMaxQuantity.setQuantity(findAllByName(name, expenses).size());
                }
            }
            return Optional.of(expenseWithMaxQuantity);
        }



        return Optional.empty();

    }


    private List<ExpenseDto> findAllByName(String name, List<ExpenseDto> expenseDtoList){
        return expenseDtoList.stream().filter(expenseDto -> expenseDto.getName().equals(name)).collect(Collectors.toList());
    }

    /**
     * Return category with the highest total amount
     * @param start period beginning
     * @param end period end
     * @return highest total amount of category in given period
     */
    private ExpenseTopModel findCategoryWithHighestTotalAmount(LocalDateTime start, LocalDateTime end){
        List<ExpenseCategory> categories = ExpenseCategory.expenseCategoryNames();

        BigDecimal max = BigDecimal.ZERO;
        ExpenseCategory withHighestTotalAmount = categories.get(0);

        for(ExpenseCategory category: categories){
            if(calculator.sumOfExpensesInCategory(category, start, end).compareTo(max) > 0){
                max = calculator.sumOfExpensesInCategory(category,start,end);
                withHighestTotalAmount = category;
            }
        }
        ExpenseTopModel result = new ExpenseTopModel();

        if(max.compareTo(BigDecimal.ZERO) > 0){
            result.setName(withHighestTotalAmount.getCategoryName());
            result.setValue(max);
        }
        else{
            result.empty();
        }


        return result;

    }



    /**
     *
     * @param start beginning of given period
     * @param end end of given period
     * @return ExpenseCategory with the highest quantity
     */
    private ExpenseTopModel findTopCategoryByQuantity(LocalDateTime start, LocalDateTime end){
        List<ExpenseCategory> categories = ExpenseCategory.expenseCategoryNames();
        List<ExpenseDto> expenses = expenseService.findAllByLocalDateBetween(start, end);


        //Setting category to compare
        List<ExpenseDto> expenseDtoList = findAllByCategory(categories.get(0), expenses);

        ExpenseCategory topCategory = categories.get(0);
        int max = expenseDtoList.size();

        for (ExpenseCategory category : categories ){
            if(findAllByCategory(category, expenses).size() > max ){
                topCategory = category;
                max = findAllByCategory(category, expenses).size();
            }
        }
        ExpenseTopModel result = new ExpenseTopModel();

        if(max>0){
            result.setName(topCategory.getCategoryName());
            result.setValue(BigDecimal.valueOf(max));
        }
        else{
            result.empty();
        }

        return result;

    }






    private List<ExpenseDto> findAllByCategory(ExpenseCategory expenseCategory, List<ExpenseDto> expenses){
        return expenses
                .stream()
                .filter(expenseDto -> expenseDto.getExpenseCategory().equals(expenseCategory))
                .toList();
    }


}



package com.example.homefinanceweb.budgetItem.utils;

import com.example.homefinanceweb.budgetItem.model.BudgetItem;
import com.example.homefinanceweb.budgetItem.repository.BudgetItemRepository;
import com.example.homefinanceweb.budgetItem.service.BudgetItemService;
import com.example.homefinanceweb.budgetItem.dtos.BudgetItemDto;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
public abstract class BICalculator<T extends BudgetItem, E extends BudgetItemDto, R extends BudgetItemRepository<T>, S extends BudgetItemService<T, R, E>>{

    protected CalendarTool calendarTool;
    S service;


    public BigDecimal sumValueCurrentMonth() {
        return sumValue(calendarTool.currentMonthStart(), calendarTool.currentMonthEnd());
    }
    public BigDecimal sumValueCurrentYear(){
        return sumValue(calendarTool.currentYearStart(), calendarTool.currentYearEnd());
    }

    private BigDecimal sumValue(LocalDateTime start, LocalDateTime end) {
        return service.findAllByLocalDateBetween(start,end).stream().map(BudgetItemDto::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal sumValue(List<E> budgetItems){
        return budgetItems.stream()
                .map(E::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public int countQuantity(List<E> budgetItems){
        return budgetItems.size();
    }

    /**
     *
     * @param a
     * @param b
     * @return Percentage a of o b
     */
    public BigDecimal percentageOf(BigDecimal a, BigDecimal b){
        if( b.compareTo(BigDecimal.ZERO)==0){
            return BigDecimal.ZERO;
        }
        else{
            return a.divide(b, new MathContext(4))
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.DOWN);
        }

    }

    public BigDecimal averageValue(List<E> budgetItems){
        return sumValue(budgetItems).divide(
                BigDecimal.valueOf(countQuantity(budgetItems)), new MathContext(4));
    }
}

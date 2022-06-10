package com.example.homefinanceweb.budgetItem.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Service
public class CalendarToolImpl implements CalendarTool{
    private final LocalDateTime now = LocalDateTime.now();


    @Override
    public  LocalDateTime currentMonthStart() {
        return now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
    }

    @Override
    public LocalDateTime currentMonthEnd() {
        return now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);
    }

    @Override
    public   LocalDateTime currentYearStart() {
       return now.withMonth(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
    }

    @Override
    public  LocalDateTime currentYearEnd() {
        return now.withMonth(12).withDayOfMonth(now.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);
    }
}

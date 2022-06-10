package com.example.homefinanceweb.budgetItem.utils;

import java.time.LocalDateTime;

public interface CalendarTool {

    LocalDateTime currentMonthStart();

    LocalDateTime currentMonthEnd();

    LocalDateTime currentYearStart();

    LocalDateTime currentYearEnd();
}

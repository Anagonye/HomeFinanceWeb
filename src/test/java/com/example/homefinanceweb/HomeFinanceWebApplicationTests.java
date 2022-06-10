package com.example.homefinanceweb;

import com.example.homefinanceweb.budgetItem.utils.CalendarTool;
import com.example.homefinanceweb.budgetItem.utils.CalendarToolImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class HomeFinanceWebApplicationTests {

    CalendarTool calendarTool = new CalendarToolImpl();

    @Test
    void contextLoads() {
        LocalDateTime currentMonthStart = LocalDateTime.of(2022, 4,1,0,0,0);
        assertTrue(calendarTool.currentMonthStart().isEqual(currentMonthStart));
        System.out.println(currentMonthStart);
        System.out.println(calendarTool.currentMonthStart());
    }

}

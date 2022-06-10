package com.example.homefinanceweb.budgetItem.utils;

import com.example.homefinanceweb.budgetItem.model.Profit;
import com.example.homefinanceweb.budgetItem.repository.ProfitRepository;
import com.example.homefinanceweb.budgetItem.service.ProfitService;
import com.example.homefinanceweb.budgetItem.dtos.ProfitDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class ProfitCalculator extends BICalculator<Profit, ProfitDto, ProfitRepository, ProfitService> {


    public ProfitCalculator(CalendarTool calendarTool, ProfitService service) {
        super(calendarTool, service);
    }

    public BigDecimal countDailyProfit(){
        return sumValueCurrentMonth().divide(BigDecimal.valueOf(30), new MathContext(4)).setScale(2, RoundingMode.DOWN);
    }


}

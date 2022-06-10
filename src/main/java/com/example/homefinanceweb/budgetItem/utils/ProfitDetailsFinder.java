package com.example.homefinanceweb.budgetItem.utils;

import com.example.homefinanceweb.budgetItem.service.ProfitService;
import com.example.homefinanceweb.budgetItem.dtos.ProfitDto;
import com.example.homefinanceweb.budgetItem.parameters.model.profit.ProfitCategory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProfitDetailsFinder {

    private final ProfitService service;
    private final CalendarTool calendarTool;
    private final ProfitCalculator profitCalculator;


    public ProfitDetails getProfitDetails(Long id){
        ProfitDetails profitDetails = new ProfitDetails();
        profitDetails.setQuantity(
                getQuantity(
                        id,
                        calendarTool.currentYearStart(),
                        calendarTool.currentYearEnd()
                )
        );
        profitDetails.setParticipationInCategory(
                getParticipationInTheCategory(
                        id,
                        calendarTool.currentYearStart(),
                        calendarTool.currentYearEnd())

        );
        profitDetails.setTotalValueCurrentMonth(
                getTotal(
                        id,
                        calendarTool.currentMonthStart(),
                        calendarTool.currentMonthEnd())
        );
        profitDetails.setTotalValueCurrentYear(
                getTotal(
                        id,
                        calendarTool.currentYearStart(),
                        calendarTool.currentYearEnd())
        );
        profitDetails.setPriceOfLatestSuchProfit(
                findLatest(id).getValue()
        );
        profitDetails.setPercentageDifferenceToLatest(
                getPercentageDiffToLatest(id).subtract(BigDecimal.valueOf(100))
        );
        profitDetails.setAverageValue(
                getAverageValue(
                        id,
                        calendarTool.currentYearStart(),
                        calendarTool.currentYearEnd())
        );
        profitDetails.setPercentageDifferenceToAverage(
                getPercentageDiffToAverage(
                        id,
                        calendarTool.currentYearStart(),
                        calendarTool.currentYearEnd()
                ).subtract(BigDecimal.valueOf(100))
        );







        return profitDetails;
    }


    private int getQuantity(Long id, LocalDateTime start, LocalDateTime end){
        return profitCalculator.countQuantity(findSimilarProfit(id, start, end ));
    }

    /**
     * looking for similar profit in given period
     * Similar definition: Same name and profit category;
     * @param id profit id in database
     * @param start start of period
     * @param end end of period
     * @return similar profit list
     */
    private List<ProfitDto> findSimilarProfit(Long id, LocalDateTime start, LocalDateTime end){
        ProfitDto profitDto = service.findById(id).orElseThrow();

        return service.findAllByLocalDateBetween(start, end).stream().filter(profitDto1 -> profitDto1.equals(profitDto)).toList();

    }

    private BigDecimal getParticipationInTheCategory(Long id, LocalDateTime start, LocalDateTime end){
        ProfitDto profitDto = service.findById(id).orElseThrow();
        int allExpensesInCategory = profitCalculator.countQuantity(
                findAllProfitOfCategory(
                        profitDto.getProfitCategory(),
                        start,
                        end)
        );
        int similarProfit = profitCalculator.countQuantity(
                findSimilarProfit(id, start, end)
        );

        return profitCalculator.percentageOf(BigDecimal.valueOf(similarProfit), BigDecimal.valueOf(allExpensesInCategory));



    }

    private List<ProfitDto> findAllProfitOfCategory(ProfitCategory profitCategory, LocalDateTime start, LocalDateTime end){
        return service.findAllByLocalDateBetween(start, end)
                .stream()
                .filter(profitDto -> profitDto.getProfitCategory().equals(profitCategory))
                .toList();
    }

    private BigDecimal getTotal(Long id, LocalDateTime start, LocalDateTime end){
        return profitCalculator.sumValue(
                findSimilarProfit(id, start, end)
        );
    }

    /**
     * Loooking for latest similar profit
     * Similar definition: Same name and profit category;
     * @param id profit id in database of which predecessor we are looking for
     * @return latest similar profit
     */
    private ProfitDto findLatest(Long id){
        ProfitDto profitDto = service.findById(id).orElseThrow();
        List<ProfitDto> allProfit = service.findAllOrderBySavedAtDesc()
                .stream()
                .filter(profitDto1 -> profitDto1.equals(profitDto))
                .toList();
        ProfitDto latest = profitDto;
        for(ProfitDto profit: allProfit){
            if(profit.getSavedAt()
                    .isBefore(
                            profitDto.getSavedAt()
                    )){
                latest = profit;
                break;
            }
        }

        return latest;

    }

    private BigDecimal getAverageValue(Long id, LocalDateTime start, LocalDateTime end){
        return profitCalculator.averageValue(findSimilarProfit(id, start, end));
    }

    private BigDecimal getPercentageDiffToLatest(Long id){
        ProfitDto profitDto = service.findById(id).orElseThrow();
        return profitCalculator.percentageOf(
                profitDto.getValue(),
                findLatest(id).getValue());
    }
    private BigDecimal getPercentageDiffToAverage(Long id, LocalDateTime start, LocalDateTime end){
        ProfitDto profitDto = service.findById(id).orElseThrow();
        return profitCalculator.percentageOf(
                profitDto.getValue(),
                getAverageValue(id,start, end));
    }
}

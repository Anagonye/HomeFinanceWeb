package com.example.homefinanceweb.financialStatement;

import com.example.homefinanceweb.budgetItem.dtos.BudgetItemDto;
import com.example.homefinanceweb.budgetItem.dtos.ExpenseDto;
import com.example.homefinanceweb.budgetItem.dtos.ProfitDto;
import com.example.homefinanceweb.budgetItem.parameters.model.expense.ExpenseCategory;
import com.example.homefinanceweb.budgetItem.parameters.model.profit.ProfitCategory;
import com.example.homefinanceweb.budgetItem.service.ExpenseService;
import com.example.homefinanceweb.budgetItem.service.ProfitService;
import com.example.homefinanceweb.financialStatement.model.FinStateModel;
import com.example.homefinanceweb.financialStatement.model.FinStateSumModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class FinStateCreator {
    private final ExpenseService expenseService;
    private final ProfitService profitService;




    public List<FinStateModel>  finStateModelExpenses(){
        List<ExpenseDto> all = expenseService.findALl();
        List<ExpenseCategory> categories = ExpenseCategory.expenseCategoryNames();
        List<FinStateModel> result = new ArrayList<>();

        for(ExpenseCategory category : categories){
            List<ExpenseDto> byCategory = all.stream()
                    .filter(expenseDto -> expenseDto.getExpenseCategory().equals(category))
                    .toList();
            result.add(new FinStateModel(category.getCategoryName(),
                    sumValue(byCategory),byCategory.size(),
                    percentageOf(
                            sumValue(byCategory),
                            sumValue(all)
                    )

            ));
        }
        return result;

    }

    public FinStateSumModel finStateSumModelExpenses(){
        List<ExpenseDto> all = expenseService.findALl();
        return new FinStateSumModel(
                sumValue(all),
                all.size()
        );
    }

    public List<FinStateModel> finStateModelProfits(){
        List<ProfitDto> all = profitService.findALl();
        List<ProfitCategory> categories = ProfitCategory.profitCategoryNames();
        List<FinStateModel> result = new ArrayList<>();

        for(ProfitCategory category : categories){
            List<ProfitDto> byCategory = all.stream()
                    .filter(profitDto -> profitDto.getProfitCategory().equals(category))
                    .toList();
            result.add(new FinStateModel(category.getCategoryName(),
                    sumValue(byCategory),byCategory.size(),
                    percentageOf(
                            sumValue(byCategory),
                            sumValue(all)
                    )

            ));
        }
        return result;

    }

    public FinStateSumModel finStateSumModelProfits(){
        List<ProfitDto> all = profitService.findALl();
        return new FinStateSumModel(
                sumValue(all),
                all.size()
        );
    }



    private BigDecimal sumValue(List<? extends BudgetItemDto> budgetItemDtoList){
        return budgetItemDtoList.stream()
                .map(BudgetItemDto::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    private BigDecimal percentageOf(BigDecimal a, BigDecimal b){

        if(b.compareTo(BigDecimal.ZERO)==0){
            return BigDecimal.ZERO;
        }

        return a.divide(b, new MathContext(4)).setScale(2, RoundingMode.DOWN).multiply(BigDecimal.valueOf(100));

    }
}

package com.example.homefinanceweb.budgetItem.utils;

import com.example.homefinanceweb.budgetItem.dtos.ExpenseDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpenseTopModelMapper {
    /**
     * If expense is present sets ExpenseTopModel name, and value otherwise returns empty ExpenseTopModel.
     * "Empty" means name = "None" and value = 0.
     * @param expenseDto
     * @return ExpenseTopModel
     */
    public static ExpenseTopModel mapIfPresent(Optional<ExpenseDto> expenseDto){
        ExpenseTopModel result = new ExpenseTopModel();
        if(expenseDto.isPresent()){
            result.setName(expenseDto.get().getName());
            result.setValue(expenseDto.get().getValue());
            return result;
        }
        else{
            result.empty();
        }
        return result;
    }
}

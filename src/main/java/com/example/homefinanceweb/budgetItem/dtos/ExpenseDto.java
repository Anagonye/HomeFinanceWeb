package com.example.homefinanceweb.budgetItem.dtos;

import com.example.homefinanceweb.budgetItem.parameters.model.expense.ExpenseCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExpenseDto extends BudgetItemDto{

   private ExpenseCategory expenseCategory;

   public ExpenseDto(String name, BigDecimal value, ExpenseCategory expenseCategory, LocalDate localDate) {
      super(name, value,  localDate);
      this.expenseCategory = expenseCategory;
   }


   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      if (!super.equals(o)) return false;
      ExpenseDto that = (ExpenseDto) o;
      return expenseCategory == that.expenseCategory;
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), expenseCategory);
   }
}

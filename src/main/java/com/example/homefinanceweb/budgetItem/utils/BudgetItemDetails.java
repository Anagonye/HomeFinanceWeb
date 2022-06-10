package com.example.homefinanceweb.budgetItem.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
public abstract class BudgetItemDetails {

    Long id;
    String name;
    BigDecimal value;
    String description;
    LocalDateTime savedAt;





}

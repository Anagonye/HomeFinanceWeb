package com.example.homefinanceweb.budgetItem.dtos;

import com.example.homefinanceweb.appuser.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public abstract class BudgetItemDto {

    private Long id;
    private String name;
    private BigDecimal value;
    //created for receive date from html input, should be converted to LocalDateTime before save in database
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDate;
    private LocalDateTime savedAt;
    private AppUser appUser;
    //created for store counted number of budgetItem
    private int quantity;

    public BudgetItemDto(String name, BigDecimal value) {
        this.name = name;
        this.value = value;


    }
    public BudgetItemDto(String name, BigDecimal value, LocalDate localDate) {
        this.name = name;
        this.value = value;
        this.localDate = localDate;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BudgetItemDto that = (BudgetItemDto) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

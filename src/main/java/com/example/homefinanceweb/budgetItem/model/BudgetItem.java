package com.example.homefinanceweb.budgetItem.model;

import com.example.homefinanceweb.appuser.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public abstract class BudgetItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull
    String name;
    @NotNull
    BigDecimal value;
    @NotNull
    LocalDateTime savedAt;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    @NotNull
    AppUser appUser;


    public BudgetItem(String name, BigDecimal value, LocalDateTime savedAt, AppUser appUser) {
        this.name = name;
        this.value = value;
        this.savedAt = savedAt;
        this.appUser = appUser;
    }


}

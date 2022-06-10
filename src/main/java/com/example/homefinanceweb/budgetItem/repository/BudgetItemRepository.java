package com.example.homefinanceweb.budgetItem.repository;

import com.example.homefinanceweb.appuser.AppUser;
import com.example.homefinanceweb.budgetItem.model.BudgetItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BudgetItemRepository<T extends BudgetItem> extends JpaRepository<T, Long> {

    List<T> findAllByAppUser(AppUser appUser);
    List<T> findTop5ByAppUserOrderBySavedAtDesc(@Param("appUser")AppUser appUser);
    List<T> findAllByAppUserAndSavedAtBetween(AppUser appUser, LocalDateTime start, LocalDateTime end);
    List<T> findAllByAppUserOrderBySavedAtDesc(AppUser appUser);
    Optional<T> findByAppUserAndId(AppUser appUser, Long id);
    void deleteByAppUserAndId(AppUser appUser, Long id);


}

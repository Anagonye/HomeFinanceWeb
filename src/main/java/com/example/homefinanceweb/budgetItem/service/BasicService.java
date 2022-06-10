package com.example.homefinanceweb.budgetItem.service;

import com.example.homefinanceweb.budgetItem.dtos.BudgetItemDto;
import com.example.homefinanceweb.budgetItem.model.BudgetItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BasicService<T extends BudgetItemDto, E extends BudgetItem> {
    void add(T budgetItemDto, String username);
    List<T> findALl();
    List<T> findLatest();
    List<T> findAllByLocalDateBetween(LocalDateTime start, LocalDateTime end);
    List<T> findAllOrderBySavedAtDesc();
    void deleteById(Long id);
    Optional<T> findById(Long id);
    Set<String> findAllNames();
    void update(Long targetId, T source);
}

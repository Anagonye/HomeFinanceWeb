package com.example.homefinanceweb.budgetItem.repository;

import com.example.homefinanceweb.budgetItem.model.Profit;
import com.example.homefinanceweb.budgetItem.repository.BudgetItemRepository;

import javax.transaction.Transactional;

@Transactional
public interface ProfitRepository extends BudgetItemRepository<Profit> {
}

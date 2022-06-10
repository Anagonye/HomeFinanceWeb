package com.example.homefinanceweb.budgetItem.repository;


import com.example.homefinanceweb.budgetItem.model.Expense;
import com.example.homefinanceweb.budgetItem.repository.BudgetItemRepository;

import javax.transaction.Transactional;


@Transactional
public interface ExpenseRepository extends BudgetItemRepository<Expense> {



}

package com.example.homefinanceweb.budgetItem.repository;

import com.example.homefinanceweb.budgetItem.model.ExpensePage;
import com.example.homefinanceweb.budgetItem.model.ExpenseSearchCriteria;
import com.example.homefinanceweb.budgetItem.model.Expense;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ExpenseCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ExpenseCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Expense> findAllByCriteria(ExpensePage expensePage, ExpenseSearchCriteria expenseSearchCriteria){
        //Specify result type
        CriteriaQuery<Expense> criteriaQuery = criteriaBuilder.createQuery(Expense.class);

        //Specify from which entity we want to retrieve this result
        Root<Expense> expenseRoot = criteriaQuery.from(Expense.class);

        //Filer result
        Predicate predicate = getPredicate(expenseSearchCriteria, expenseRoot);
        criteriaQuery.where(predicate);

        //Sort result
        setOrder(expensePage, criteriaQuery, expenseRoot);

        TypedQuery<Expense> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(expensePage.getPageNumber() * expensePage.getPageSize());
        typedQuery.setMaxResults(expensePage.getPageSize());

        Pageable pageable = getPageRequest(expensePage);

        long expensesCount = getExpensesCount(predicate);


        return new PageImpl<>(typedQuery.getResultList(), pageable, expensesCount);

    }




    private Predicate getPredicate(ExpenseSearchCriteria expenseSearchCriteria, Root<Expense> expenseRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if(Objects.nonNull(expenseSearchCriteria.getName())){
            predicates.add(
                    criteriaBuilder.equal(expenseRoot.get("name"), expenseSearchCriteria.getName())
            );
        }
        if(Objects.nonNull(expenseSearchCriteria.getExpenseCategory())){
            predicates.add(
                    criteriaBuilder.equal(expenseRoot.get("expenseCategory"), expenseSearchCriteria.getExpenseCategory())
            );
        }
        if(Objects.nonNull(expenseSearchCriteria.getStart()) && Objects.nonNull(expenseSearchCriteria.getEnd())){
            predicates.add(
                    criteriaBuilder.between(expenseRoot.get("savedAt"), expenseSearchCriteria.getStart(), expenseSearchCriteria.getEnd())
            );
        }
        if(Objects.nonNull(expenseSearchCriteria.getAppUser())){
            predicates.add(
                    criteriaBuilder.equal(expenseRoot.get("appUser"), expenseSearchCriteria.getAppUser())
            );
        }



        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(ExpensePage expensePage, CriteriaQuery<Expense> criteriaQuery, Root<Expense> expenseRoot) {

        if(expensePage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(expenseRoot.get(expensePage.getSortBy())));
        }
        else {
            criteriaQuery.orderBy(criteriaBuilder.desc(expenseRoot.get(expensePage.getSortBy())));
        }

    }

    private PageRequest getPageRequest(ExpensePage expensePage) {
        Sort sort = Sort.by(expensePage.getSortDirection(), expensePage.getSortBy());
        return PageRequest.of(expensePage.getPageNumber(), expensePage.getPageSize(), sort);
    }

    private long getExpensesCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Expense> countRoot = countQuery.from(Expense.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}

package com.example.homefinanceweb.budgetItem.repository;

import com.example.homefinanceweb.budgetItem.model.ProfitPage;
import com.example.homefinanceweb.budgetItem.model.ProfitSearchCriteria;
import com.example.homefinanceweb.budgetItem.model.Profit;
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
public class ProfitCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ProfitCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Profit> findAllByCriteria(ProfitPage profitPage, ProfitSearchCriteria profitSearchCriteria){
        //Specify result type
        CriteriaQuery<Profit> criteriaQuery = criteriaBuilder.createQuery(Profit.class);

        //Specify from which entity we want to retrieve this result
        Root<Profit> profitRoot = criteriaQuery.from(Profit.class);

        //Filer result
        Predicate predicate = getPredicate(profitSearchCriteria, profitRoot);
        criteriaQuery.where(predicate);

        //Sort result
        setOrder(profitPage, criteriaQuery, profitRoot);

        TypedQuery<Profit> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(profitPage.getPageNumber() * profitPage.getPageSize());
        typedQuery.setMaxResults(profitPage.getPageSize());

        Pageable pageable = getPageRequest(profitPage);

        long profitCount = getExpensesCount(predicate);


        return new PageImpl<>(typedQuery.getResultList(), pageable, profitCount);

    }




    private Predicate getPredicate(ProfitSearchCriteria profitSearchCriteria, Root<Profit> profitRoot) {
        List<Predicate> predicates = new ArrayList<>();


        if(Objects.nonNull(profitSearchCriteria.getName())){
            predicates.add(
                    criteriaBuilder.equal(profitRoot.get("name"), profitSearchCriteria.getName())
            );
        }
        if(Objects.nonNull(profitSearchCriteria.getProfitCategory())){
            predicates.add(
                    criteriaBuilder.equal(profitRoot.get("profitCategory"), profitSearchCriteria.getProfitCategory())
            );
        }
        if(Objects.nonNull(profitSearchCriteria.getStart()) && Objects.nonNull(profitSearchCriteria.getEnd())){
            predicates.add(
                    criteriaBuilder.between(profitRoot.get("savedAt"), profitSearchCriteria.getStart(), profitSearchCriteria.getEnd())
            );
        }
        if(Objects.nonNull(profitSearchCriteria.getAppUser())){
            predicates.add(
                    criteriaBuilder.equal(profitRoot.get("appUser"), profitSearchCriteria.getAppUser())
            );
        }



        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(ProfitPage profitPage, CriteriaQuery<Profit> criteriaQuery, Root<Profit> profitRoot) {

        if(profitPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(profitRoot.get(profitPage.getSortBy())));
        }
        else {
            criteriaQuery.orderBy(criteriaBuilder.desc(profitRoot.get(profitPage.getSortBy())));
        }

    }

    private PageRequest getPageRequest(ProfitPage profitPage) {
        Sort sort = Sort.by(profitPage.getSortDirection(), profitPage.getSortBy());
        return PageRequest.of(profitPage.getPageNumber(), profitPage.getPageSize(), sort);
    }

    private long getExpensesCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Profit> countRoot = countQuery.from(Profit.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}

package com.example.homefinanceweb.budgetItem.service;

import com.example.homefinanceweb.appuser.AppUser;
import com.example.homefinanceweb.appuser.AppUserService;
import com.example.homefinanceweb.budgetItem.model.ExpensePage;
import com.example.homefinanceweb.budgetItem.model.ExpenseSearchCriteria;
import com.example.homefinanceweb.budgetItem.dtomappers.BudgetItemDtoMapper;
import com.example.homefinanceweb.budgetItem.dtos.ExpenseDto;
import com.example.homefinanceweb.budgetItem.generator.DataTestGenerator;
import com.example.homefinanceweb.budgetItem.model.Expense;
import com.example.homefinanceweb.budgetItem.parameters.model.expense.ExpenseCategory;
import com.example.homefinanceweb.budgetItem.repository.ExpenseCriteriaRepository;
import com.example.homefinanceweb.budgetItem.repository.ExpenseRepository;
import com.example.homefinanceweb.security.utils.AuthUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

@Service
public class ExpenseService extends BudgetItemService<Expense, ExpenseRepository, ExpenseDto> {

    private final ExpenseCriteriaRepository expenseCriteriaRepository;
    private final Validator validator;
    private final AppUserService appUserService;

    public ExpenseService(ExpenseRepository repository, Function<Expense, ExpenseDto> dtoMapper, AuthUtils authUtils, ExpenseCriteriaRepository expenseCriteriaRepository, Validator validator, AppUserService appUserService ) {
        super(repository, dtoMapper,authUtils);
        this.expenseCriteriaRepository = expenseCriteriaRepository;
        this.validator = validator;
        this.appUserService = appUserService;
    }


    public void add(ExpenseDto expenseDto, String username){
        Expense expenseToSave = BudgetItemDtoMapper.map(expenseDto);
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(username);
        expenseToSave.setAppUser(appUser);
        LocalDateTime localDateTime;
        if(!Objects.isNull(expenseDto.getLocalDate())) {
            if (expenseDto.getLocalDate().isEqual(LocalDate.now())) {
                localDateTime = LocalDateTime.now();
            } else {
                localDateTime = expenseDto.getLocalDate().atStartOfDay().withSecond(1);
            }
        }else{
            localDateTime = LocalDateTime.now();
        }



        expenseToSave.setSavedAt(localDateTime);

        Set<ConstraintViolation<Expense>> errors = validator.validate(expenseToSave);
        if(!errors.isEmpty()){
            System.out.println(">Object cannot be added, error list:");
            errors.forEach(err ->
                    System.out.printf(">>> %s %s (%s)\n",
                            err.getPropertyPath(),
                            err.getMessage(),
                            err.getInvalidValue()));
        }else {

            repository.save(expenseToSave);
        }
    }

    public Page<ExpenseDto> getPaginatedExpenses(ExpensePage expensePage, ExpenseSearchCriteria expenseSearchCriteria){
        expenseSearchCriteria.setAppUser(authUtils.getCurrentLoggedInUser());
        return expenseCriteriaRepository.findAllByCriteria(expensePage, expenseSearchCriteria).map(BudgetItemDtoMapper::map);
    }

    @Transactional
    public void update(Long targetId, ExpenseDto source) {
        Optional<Expense> toUpdate = repository.findByAppUserAndId(authUtils.getCurrentLoggedInUser(), targetId);
        if(toUpdate.isPresent()){
            toUpdate.map(target -> setEntityFields(source, target));
        }

    }


    //Adding test expenses for test user
    @Bean
    CommandLineRunner commandLineRunnerGenerateTestExpenses(){
        List<ExpenseDto> testExpenses = DataTestGenerator.generatedExpenses();
        List<ExpenseCategory> categories = ExpenseCategory.expenseCategoryNames();
        ExpenseDto expenseDto = new ExpenseDto("Apple", BigDecimal.valueOf(5),  categories.get(0), LocalDate.now() );
        return args -> {
            for(ExpenseDto expense: testExpenses){
                add(expense, "test@test.pl");
            }
            add(expenseDto, "test@test.pl");
        };

    }

    private Expense setEntityFields(ExpenseDto source, Expense target){
        if(source.getName() != null){
            target.setName(source.getName());
        }
        if(source.getValue() != null){
            target.setValue(source.getValue());
        }
        if(source.getExpenseCategory() != null){
            target.setExpenseCategory(source.getExpenseCategory());
        }

        return target;
    }


}

package com.example.homefinanceweb.budgetItem.service;

import com.example.homefinanceweb.appuser.AppUser;
import com.example.homefinanceweb.appuser.AppUserService;
import com.example.homefinanceweb.budgetItem.model.ProfitPage;
import com.example.homefinanceweb.budgetItem.model.ProfitSearchCriteria;
import com.example.homefinanceweb.budgetItem.dtomappers.BudgetItemDtoMapper;
import com.example.homefinanceweb.budgetItem.dtos.ProfitDto;
import com.example.homefinanceweb.budgetItem.model.Profit;
import com.example.homefinanceweb.budgetItem.parameters.model.profit.ProfitCategory;
import com.example.homefinanceweb.budgetItem.repository.ProfitCriteriaRepository;
import com.example.homefinanceweb.budgetItem.repository.ProfitRepository;
import com.example.homefinanceweb.security.utils.AuthUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ProfitService extends BudgetItemService<Profit, ProfitRepository, ProfitDto> {


    private final ProfitCriteriaRepository profitCriteriaRepository;
    private final AppUserService appUserService;

    public ProfitService(ProfitRepository repository, Function<Profit, ProfitDto> function, AuthUtils authUtils, ProfitCriteriaRepository profitCriteriaRepository, AppUserService appUserService ) {
        super(repository, function, authUtils);
        this.profitCriteriaRepository = profitCriteriaRepository;
        this.appUserService = appUserService;


    }

    @Override
    public void add(ProfitDto budgetItemDto, String username) {
        Profit profitToSave = BudgetItemDtoMapper.map(budgetItemDto);
        System.out.println("dto name: " + budgetItemDto.getName());
        System.out.println(profitToSave.getName());
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(username);
        profitToSave.setAppUser(appUser);
        profitToSave.setSavedAt(LocalDateTime.now());
        repository.save(profitToSave);



    }
    public Page<Profit> getPaginatedProfits(ProfitPage profitPage, ProfitSearchCriteria profitSearchCriteria){
        profitSearchCriteria.setAppUser(authUtils.getCurrentLoggedInUser());
        return profitCriteriaRepository.findAllByCriteria(profitPage, profitSearchCriteria);
    }



    @Transactional
    public void update(Long targetId, ProfitDto source) {
        Optional<Profit> toUpdate = repository.findByAppUserAndId(authUtils.getCurrentLoggedInUser(), targetId);
        if(toUpdate.isPresent()){
            toUpdate.map(target -> setEntityFields(source, target));
        }

    }




    @Bean
    CommandLineRunner saveTestProfits(){
        ProfitDto testProfit = new ProfitDto("Main salary", BigDecimal.valueOf(5000), ProfitCategory.SALARY);
        return args -> add(testProfit, appUserService.loadUserByUsername("test@test.pl").getUsername());
    }

    private Profit setEntityFields(ProfitDto source, Profit target){
        if(source.getName() != null){
            target.setName(source.getName());
        }
        if(source.getValue() != null){
            target.setValue(source.getValue());
        }
        if(source.getProfitCategory() != null){
            target.setProfitCategory(source.getProfitCategory());
        }

        return target;
    }
}

package com.example.homefinanceweb.budgetItem.service;


import com.example.homefinanceweb.appuser.AppUser;
import com.example.homefinanceweb.appuser.AppUserService;
import com.example.homefinanceweb.budgetItem.dtos.BudgetItemDto;
import com.example.homefinanceweb.budgetItem.model.BudgetItem;
import com.example.homefinanceweb.budgetItem.repository.BudgetItemRepository;
import com.example.homefinanceweb.security.utils.AuthUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


public abstract class BudgetItemService <E extends BudgetItem, R extends BudgetItemRepository<E>, T extends BudgetItemDto> implements BasicService<T,E> {

    protected final R repository;
    private final Function<E, T> dtoMapper;
    protected final AuthUtils authUtils;


    public BudgetItemService(R repository, Function<E, T> dtoMapper, AuthUtils authUtils) {
        this.repository = repository;
        this.dtoMapper = dtoMapper;
        this.authUtils = authUtils;
    }

    @Override
    public List<T> findALl() {
        return repository.findAllByAppUser(authUtils.getCurrentLoggedInUser()).
                stream()
                .map(dtoMapper)
                .toList();
    }

    @Override
    public List<T> findLatest() {
        return repository.findTop5ByAppUserOrderBySavedAtDesc(authUtils.getCurrentLoggedInUser())
                .stream()
                .map(dtoMapper)
                .toList();
    }

    @Override
    public List<T> findAllByLocalDateBetween(LocalDateTime start, LocalDateTime end) {
        return repository.findAllByAppUserAndSavedAtBetween(authUtils.getCurrentLoggedInUser(), start, end)
                .stream().map(dtoMapper)
                .toList();
    }

    @Override
    public Optional<T> findById(Long id) {
        return repository.findByAppUserAndId(authUtils.getCurrentLoggedInUser(), id).map(dtoMapper);
    }

    @Override
    public List<T> findAllOrderBySavedAtDesc() {
        return repository.findAllByAppUserOrderBySavedAtDesc(authUtils.getCurrentLoggedInUser())
                .stream()
                .map(dtoMapper)
                .toList();
    }
    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteByAppUserAndId(authUtils.getCurrentLoggedInUser(), id);
    }

    @Override
    public Set<String> findAllNames() {
        return repository.findAllByAppUser(authUtils.getCurrentLoggedInUser())
                .stream()
                .map(E::getName)
                .collect(Collectors.toSet());
    }


}

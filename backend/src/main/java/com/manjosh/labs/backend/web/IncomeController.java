package com.manjosh.labs.backend.web;

import com.manjosh.labs.backend.domain.income.Income;
import com.manjosh.labs.backend.domain.income.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/incomes")
public class IncomeController {
    private final IncomeService incomeService;

    @PostMapping
    public ResponseEntity<Income> addIncome(@RequestBody final Income income) {
        return ResponseEntity.status(HttpStatus.CREATED).body(incomeService.addIncome(income));
    }
}

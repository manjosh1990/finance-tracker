package com.manjosh.labs.backend.web;

import com.manjosh.labs.backend.domain.income.Income;
import com.manjosh.labs.backend.domain.income.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping
    public ResponseEntity<Iterable<Income>> getIncomes() {
        return ResponseEntity.ok(incomeService.getExpensesCurrentMonthExpenseForCurrentProfile());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable final Long id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }
}

package com.manjosh.labs.backend.web;

import com.manjosh.labs.backend.domain.expense.Expense;
import com.manjosh.labs.backend.domain.expense.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody final Expense expense) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.addExpense(expense));
    }
}

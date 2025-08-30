package com.manjosh.labs.backend.web;

import com.manjosh.labs.backend.domain.FilteredInput;
import com.manjosh.labs.backend.domain.expense.ExpenseService;
import com.manjosh.labs.backend.domain.income.IncomeService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filter")
@RequiredArgsConstructor
public class FilterController {

    private final ExpenseService expenseService;
    private final IncomeService incomeService;

    @PostMapping
    public ResponseEntity<?> filterTransactions(@RequestBody final FilteredInput filteredInput) {
        final LocalDate startDate = filteredInput.startDate() != null
                ? filteredInput.startDate()
                : LocalDate.now().minusYears(10);
        final LocalDate endDate = filteredInput.endDate() != null ? filteredInput.endDate() : LocalDate.now();
        final String keyword = filteredInput.keyword() != null ? filteredInput.keyword() : "";
        final String sortField = filteredInput.sortField() != null ? filteredInput.sortField() : "transactionDate";
        final Sort.Direction direction =
                "desc".equalsIgnoreCase(filteredInput.sortOrder()) ? Sort.Direction.DESC : Sort.Direction.ASC;
        final Sort sort = Sort.by(direction, sortField);
        if ("expense".equalsIgnoreCase(filteredInput.type())) {
            return ResponseEntity.ok(expenseService.filterExpenses(startDate, endDate, keyword, sort));
        } else if ("income".equalsIgnoreCase(filteredInput.type())) {
            return ResponseEntity.ok(incomeService.filterIncomes(startDate, endDate, keyword, sort));
        } else {
            return ResponseEntity.badRequest().body("Invalid type");
        }
    }
}

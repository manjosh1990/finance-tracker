package com.manjosh.labs.backend.domain;

import com.manjosh.labs.backend.domain.expense.Expense;
import com.manjosh.labs.backend.domain.expense.ExpenseService;
import com.manjosh.labs.backend.domain.income.Income;
import com.manjosh.labs.backend.domain.income.IncomeService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final ExpenseService expenseService;
    private final IncomeService incomeService;

    public Map<String, Object> getDashboardData() {
        final Map<String, Object> dashboardData = new LinkedHashMap<>();
        final List<Income> incomes = incomeService.getRecentTopFiveIncomes();
        final List<Expense> expenses = expenseService.getRecentTopFiveExpenses();
        final List<RecentTransactions> recentTransactions = Stream.concat(
                        incomes.stream()
                                .map(income -> new RecentTransactions(
                                        income.id(),
                                        income.profileId(),
                                        income.icon(),
                                        income.name(),
                                        income.amount(),
                                        LocalDate.parse(income.transactionDate()),
                                        income.description(),
                                        "INCOME",
                                        income.createdAt(),
                                        income.updatedAt())),
                        expenses.stream()
                                .map(expense -> new RecentTransactions(
                                        expense.id(),
                                        expense.profileId(),
                                        expense.icon(),
                                        expense.name(),
                                        expense.amount(),
                                        LocalDate.parse(expense.transactionDate()),
                                        expense.description(),
                                        "EXPENSE",
                                        expense.createdAt(),
                                        expense.updatedAt())))
                .sorted((a, b) -> {
                    int cmp = b.transactionDate().compareTo(a.transactionDate());
                    if (cmp == 0 && a.createdTime() != null && b.createdTime() != null) {
                        cmp = b.createdTime().compareTo(a.createdTime());
                    }
                    return cmp;
                })
                .toList();
        BigDecimal totalIncome = incomeService.getTotalIncomeForCurrentProfile();
        BigDecimal totalExpense = expenseService.getTotalExpenseForCurrentProfile();
        dashboardData.put("totalBalance", totalIncome.subtract(totalExpense));
        dashboardData.put("totalIncome", totalIncome);
        dashboardData.put("totalExpense", totalExpense);
        dashboardData.put("recentIncomes", incomes);
        dashboardData.put("recentExpenses", expenses);
        dashboardData.put("recentTransactions", recentTransactions);
        return dashboardData;
    }
}

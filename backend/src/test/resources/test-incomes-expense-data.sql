TRUNCATE TABLE tbl_incomes CASCADE;
TRUNCATE TABLE tbl_expense CASCADE;

-- Insert test expense data
INSERT INTO tbl_expense (
    id, name, icon, transaction_date, description, amount, category_id, profile_id, created_at, updated_at
) VALUES
    (7, 'Groceries', 'grocery-icon', CURRENT_DATE, 'Weekly grocery shopping', 3000.00, 2, 1, NOW(), NOW()),
    (8, 'Electricity Bill', 'electricity-icon', CURRENT_DATE, 'Monthly electricity bill', 2500.00, 3, 1, NOW(), NOW());

-- Insert test income data
INSERT INTO tbl_incomes (
    id, name, icon, transaction_date, description, amount, category_id, profile_id, created_at, updated_at
) VALUES
    (13, 'Monthly Salary', 'salary-icon', CURRENT_DATE, 'Monthly salary credit', 75000.00, 3, 1, NOW(), NOW()),
    (14, 'Freelance Work', 'freelance-icon', CURRENT_DATE, 'Web development project', 15000.00, 5, 1, NOW(), NOW());

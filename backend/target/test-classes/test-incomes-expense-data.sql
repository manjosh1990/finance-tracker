TRUNCATE TABLE tbl_incomes CASCADE;
TRUNCATE TABLE tbl_expense CASCADE;

-- Set variables for dynamic dates
WITH date_vars AS (
    SELECT
        CURRENT_DATE - INTERVAL '5 days' AS groceries_date,
        CURRENT_DATE AS electricity_bill_date,
        DATE_TRUNC('month', CURRENT_DATE) AS salary_date,
        CURRENT_DATE - INTERVAL '2 days' AS freelance_date
)
-- Insert test expense data
INSERT INTO tbl_expense (
    id,
    name,
    icon,
    transaction_date,
    description,
    amount,
    category_id,
    profile_id,
    created_at,
    updated_at
)
SELECT
    7,
    'Groceries',
    'grocery-icon',
    groceries_date,
    'Weekly grocery shopping',
    3000.00,
    2,
    1,
    NOW(),
    NOW()
FROM date_vars
UNION ALL
SELECT
    8,
    'Electricity Bill',
    'electricity-icon',
    electricity_bill_date,
    'Monthly electricity bill',
    2500.00,
    3,
    1,
    NOW(),
    NOW()
FROM date_vars;

-- Insert test income data
WITH date_vars AS (
    SELECT
        DATE_TRUNC('month', CURRENT_DATE) AS salary_date,
        CURRENT_DATE - INTERVAL '2 days' AS freelance_date
)
INSERT INTO tbl_incomes (
    id,
    name,
    icon,
    transaction_date,
    description,
    amount,
    category_id,
    profile_id,
    created_at,
    updated_at
)
SELECT
    13,
    'Monthly Salary',
    'salary-icon',
    salary_date,
    'Monthly salary credit',
    75000.00,
    3,
    1,
    NOW(),
    NOW()
FROM date_vars
UNION ALL
SELECT
    14,
    'Freelance Work',
    'freelance-icon',
    freelance_date,
    'Web development project',
    15000.00,
    5,
    1,
    NOW(),
    NOW()
FROM date_vars;

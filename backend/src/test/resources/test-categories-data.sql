truncate table tbl_categories CASCADE;
INSERT INTO tbl_categories (id,name, type, icon, profile_id)
VALUES (2,'Groceries', 'EXPENSE', 'shopping_cart', 1),
 (3,'Salary', 'INCOME', 'account_balance_wallet', 1),
 (4,'Rent', 'EXPENSE', 'home', 1),
 (5,'Freelance', 'INCOME', 'work', 1),
 (6,'Entertainment', 'EXPENSE', 'movie', 1);
-- For the 1st chart (pie-chart) : Sum of amounts per category
-- This function find the sum of amounts per category with given period
-- The parameters in the dateEffect take the date of the choice
SELECT
    c.name AS category,
    DATE_TRUNC('month', t.dateEffect) AS period,
    SUM(s.amount) AS sumOfAmountSupply,
    SUM(t.amount) AS sumOfAmountTransfert
FROM "category" c
         INNER JOIN "supply" s ON c.id = s.id
         INNER JOIN "account" a ON a.id = s.id
         INNER JOIN "transfert" t ON t.id = s.id
WHERE t.dateEffect BETWEEN '2024-01-01' AND '2024-12-31'
GROUP BY c.name, DATE_TRUNC('month', t.dateEffect);

-- For the 2nd chart (chart of your choice) : Sum of expenses and income
-- This function the sum of expenses and income with given date
SELECT
    SUM(CASE WHEN t.amount < 0 THEN t.amount ELSE 0 END) AS totalExpensesTransfert,
    SUM(CASE WHEN t.amount >= 0 THEN t.amount ELSE 0 END) AS totalIncomeTransfert,
    SUM(CASE WHEN s.amount < 0 THEN s.amount ELSE 0 END) AS totalExpensesSupply,
    SUM(CASE WHEN s.amount >= 0 THEN s.amount ELSE 0 END) AS totalIncomeSupply
FROM "transfert" t
         INNER JOIN "account" a ON a.id = t.id
         INNER JOIN "supply" s ON s.id = a.id
WHERE t.dateEffect BETWEEN '2024-01-01' AND '2024-12-31';
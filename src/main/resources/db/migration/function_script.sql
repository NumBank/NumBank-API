-- F2
CREATE OR REPLACE FUNCTION moneyWithdrawal(
    idAccount INT,
    amount DOUBLE PRECISION,
    withDrawalDate TIMESTAMP
)
    RETURNS VARCHAR
AS $$
DECLARE
    soldePrincipal DOUBLE PRECISION;
    approveCredit DOUBLE PRECISION;
    balance DOUBLE PRECISION;
    netSalary DOUBLE PRECISION;
    lowInterestRate DECIMAL;
    highInterestRate DECIMAL;
    thresholdLittleDays INT;
    thresholdLargeDays INT;
    interest DECIMAL;
BEGIN
    -- Recover the account's principal balance and authorized credit
    SELECT SoldePrincipal, a.approveCredit AS approvedCredit INTO soldePrincipal, approveCredit
    FROM Account
             JOIN AutorizationOpen a ON Account.id = AutorizationOpen.id
    WHERE Account.id = idAccount;

    -- Check that the balance + authorized credit covers the amount to be withdrawn
    IF soldePrincipal + approveCredit >= amount THEN

        -- Authorize withdrawal and update main account balance
        UPDATE Account
        SET SoldePrincipal = soldePrincipal - amount
        WHERE id = idAccount;

        RETURN 'Cash withdrawal successfully completed.';
    ELSE
        RETURN 'Insufficient balance to make this withdrawal.';
    END IF;
END;
$$ LANGUAGE plpgsql;


-- F3
CREATE OR REPLACE FUNCTION viewBalance(idAccount INT, consultationDate TIMESTAMP)
    RETURNS TABLE (
                      soldePrincipal DOUBLE PRECISION,
                      loan DOUBLE PRECISION,
                      interestsLoan DECIMAL
                  )
AS $$
BEGIN
    -- Recovering the main account balance
    SELECT SoldePrincipal INTO soldePrincipal
    FROM Account
    WHERE id = idAccount;

    -- Recover the amount of loans on the account if it is authorized to be overdrawn
    SELECT COALESCE(SUM(amount), 0) INTO loan
    FROM "MoneyWithdrawal"
    WHERE id = idAccount
      AND withDrawalDate <= consultationDate;

    -- Calculate loan interest
    SELECT CASE
        WHEN loan < 0 THEN loan * (CASE WHEN consultationDate - (SELECT MAX(withDrawalDate) FROM "MoneyWithdrawal" WHERE id = idAccount) <= 7 THEN 0.01
            ELSE 0.02
            END)
        ELSE 0
        END INTO interestsLoan;

    RETURN QUERY SELECT soldePrincipal, loan, interestsLoan;
END;
$$ LANGUAGE plpgsql;

-- F4
-- Creation of a function to add account provisioning
CREATE OR REPLACE FUNCTION addSupply(
    idAccount INT,
    amount DOUBLE PRECISION,
    motif TEXT,
    dateEffect TIMESTAMP,
    saveDate TIMESTAMP
)
    RETURNS VOID
AS $$
BEGIN
    INSERT INTO "Supply" (id, motif, amount, dateEffect, saveDate)
    VALUES (idAccount, motif, amount, dateEffect, saveDate);
END;
$$ LANGUAGE plpgsql;


-- F5
-- Creation of the function for making a transfer question F5
CREATE OR REPLACE FUNCTION makeTransfert(
    amount DOUBLE PRECISION,
    motif TEXT,
    effectiveDate TIMESTAMP,
    accountIdSender INT,
    accountIdRecipient INT
)
    RETURNS VOID
AS $$
BEGIN
    -- Create transfert
    INSERT INTO "Transfert" (amount, label, dateEffect, accountIdSender, accountIdRecipient)
    VALUES (amount, motif, effectiveDate, accountIdSender, accountIdRecipient);
END;
$$ LANGUAGE plpgsql;

-- Creation of the function to cancel a planned transfer
CREATE OR REPLACE FUNCTION transfertCancel(idTransfert INT)
    RETURNS VOID
AS $$
BEGIN
    -- Update transfer status to "Cancelled".
    -- We set it as a boolean in our table,
    -- so we consider it false if it's not cancelled
    UPDATE "Transfert"
    SET Status = FALSE -- here
    WHERE id = idTransfert;
END;
$$ LANGUAGE plpgsql;

-- F6
-- Creating the Account Statement view
CREATE VIEW "AccountStatement" AS
SELECT
    ID_Compte,
    Motif AS Operation,
    DateEffet AS DateOperation,
    CASE
        WHEN amount >= 0 THEN 'Credit'
        ELSE 'Debit'
        END AS TypeOperation,
    ABS(Amount) AS AmountOperation,
    SoldePrincipal AS SoldePrincipal
FROM
    (
        -- Select credit operations
        SELECT
            accountIdSender AS idCompte,
            label AS motif,
            "transfert".dateeffect,
            Amount,
            LAG(SoldePrincipal, 1, 0) OVER (PARTITION BY accountIdSender ORDER BY DateEffet) AS SoldePrincipal
        FROM
            "transfert"
        WHERE
            amount > 0
        UNION ALL
        -- Select debit operations
        SELECT
            accountIdRecipient AS idCompte,
            label,
            "transfert".dateeffect,
            amount,
            LAG(SoldePrincipal, 1, 0) OVER (PARTITION BY accountIdRecipient ORDER BY dateEffect) AS SoldePrincipal
        FROM
            "transfert"
        WHERE
            amount < 0
    ) AS Operations;

-- gestion de portefeuille
-- F1
-- Creation of a table to record the categories assigned to each banking transaction

/* NOT REALLY REQUIRED
-- Create a function to add a new category
CREATE OR REPLACE FUNCTION addCategory(
    name VARCHAR(200),
    type VARCHAR(200)
)
RETURNS VOID
AS $$
BEGIN
    INSERT INTO Category (name, type)
    VALUES (name, type);
END;
$$ LANGUAGE plpgsql;
*/

-- Creation of a function to categorize a bank transaction
CREATE OR REPLACE FUNCTION categorizeOperation(
    idOperation INT,
    idCategorie INT,
    comment TEXT
)
    RETURNS VOID
AS $$
BEGIN
    INSERT INTO categorizeOperation (idOperation, idCategorie, comment)
    VALUES (idOperation, idCategorie, comment);
END;
$$ LANGUAGE plpgsql;


-- F2
-- For the 1st chart (pie-chart) : Sum of amounts per category
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
SELECT
    SUM(CASE WHEN o.amount < 0 THEN o.amount ELSE 0 END) AS totalExpenses,
    SUM(CASE WHEN o.amount >= 0 THEN o.amount ELSE 0 END) AS totalIncome
FROM "Operation" o
WHERE o.dateEffect BETWEEN :startDate AND :endDate;
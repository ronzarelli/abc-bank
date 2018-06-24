package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final double amount;
    private Date transactionDate;

    public Transaction(double amount, Date date) {
        this.amount = amount;
        this.transactionDate = date;
    }

	public Date getTransactionDate() {
		return transactionDate;
	}

	public double getAmount() {
		return amount;
	}

}

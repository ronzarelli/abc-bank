package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount, Date date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount,date));
        }
    }

public void withdraw(double amount, Date date) {
    if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
        transactions.add(new Transaction(-amount,date));
    }
}

public double interestEarned(Date asOfDate) {
//	double amount = 0.0;
	double runningInt = 0;
	boolean first = true;
	double priorAmount=0;
	Date priorDate = null, currDate = null;
	double numDays;

	for (Transaction t: transactions)
	{
		if (first) {
			priorAmount = t.getAmount();
			priorDate = t.getTransactionDate();
			first = false;
			continue;
		}

		long startTime = priorDate.getTime();
		currDate = t.getTransactionDate();
		long endTime = currDate.getTime();
		long diffTime = endTime - startTime;
		numDays = diffTime / (1000 * 60 * 60 * 24);
		switch(accountType){
			case CHECKING:
				runningInt += ( (priorAmount * 0.001) * numDays/365);
				break;
			case SAVINGS:
				if (priorAmount <= 1000)
					runningInt += ((priorAmount * 0.001) * numDays/365);
				else
					runningInt += ((1 + (priorAmount-1000) * 0.002) * numDays/365);
				break;
				//            case SUPER_SAVINGS:
				//                if (amount <= 4000)
				//                    return 20;
			case MAXI_SAVINGS:
				if (priorAmount <= 1000)
					runningInt += (priorAmount  * 0.02 * numDays/365);
				else if (priorAmount <= 2000)
					runningInt += ((20+(priorAmount-1000) * 0.05) * numDays/365);
				else
					runningInt += ((70 + (priorAmount-2000) * 0.1) * numDays/365);
				break;
			default:
				throw new IllegalArgumentException("nonexistent account type");
		}
		priorDate = currDate;
		priorAmount += t.getAmount();
	}
//	System.out.println("asof -"+asOfDate);
	numDays = (asOfDate.getTime() - priorDate.getTime()) / (1000*60*60*24);
//	System.out.println("priorAmount ="+priorAmount+" - numDays ="+numDays);
	if (numDays<0) 
		throw new IllegalArgumentException("interest calc: transactions before asOfDate");
	switch(accountType){
	case CHECKING:
		runningInt += ((priorAmount * 0.001) * numDays/365);
		break;
	case SAVINGS:
		if (priorAmount <= 1000)
			runningInt += ((priorAmount * 0.001) * numDays/365);
		else
			runningInt += ((1 + (priorAmount-1000) * 0.002) * numDays/365);
		break;
		//            case SUPER_SAVINGS:
		//                if (amount <= 4000)
		//                    return 20;
	case MAXI_SAVINGS:
		if (priorAmount <= 1000)
			runningInt += (priorAmount  * 0.02 * numDays/365);
		else if (priorAmount <= 2000)
			runningInt += ((20+(priorAmount-1000) * 0.05) * numDays/365);
		else
			runningInt += ((70 + (priorAmount-2000) * 0.1) * numDays/365);
		break;	
	}
	return runningInt;
}

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}

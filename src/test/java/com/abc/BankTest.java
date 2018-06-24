package com.abc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// main problem is nothing had dates, so I added dates everywhere (transactions and interest calc)
// also added a few test cases, and I could use a few more.
//
// would eventually refactor to use a "dollars and cents" amount class,
// along with using a separate method to calculate interest between
// last transaction date and as-of date
//
//  java dates are also known to be a pain, but they're not bad with sdf.

public class BankTest {
    private static final double DOUBLE_DELTA = 0.01;

    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = "2018-01-01";
    String dateString2 = "2017-07-01";
    String dateString3 = "2016-01-01";
    
    Date date, date2, date3;
    
    @Before
    public void initDates() throws ParseException {
    	date = sdf.parse(dateString);
    	date2 = sdf.parse(dateString2);
    	date3 = sdf.parse(dateString3);
    }
    
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0, date3);
        checkingAccount.deposit(100.0, date2);

        assertEquals(0.25, bank.totalInterestPaid(date), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0, date3);
        savingsAccount.deposit(1000.0, date2);

        assertEquals(5.008, bank.totalInterestPaid(date), DOUBLE_DELTA);
    }

    @Test
    public void savings_accountSmall() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(150.0, date3);
        savingsAccount.deposit(100.0, date2);

        assertEquals(0.350, bank.totalInterestPaid(date), DOUBLE_DELTA);
    }
    
    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(2000.0,date3);
        maxiSavingsAccount.withdraw(1500.0,date2);
        

        assertEquals(109.75, bank.totalInterestPaid(date), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_accountMed() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(1000.0,date3);
        maxiSavingsAccount.withdraw(500.0,date2);
        

        assertEquals(34.958, bank.totalInterestPaid(date), DOUBLE_DELTA);
    }
    
    @Test
    public void maxi_savings_accountSmall() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(300.0,date3);
        maxiSavingsAccount.withdraw(200.0,date2);
        
        assertEquals(9.984, bank.totalInterestPaid(date), DOUBLE_DELTA);
    }


}

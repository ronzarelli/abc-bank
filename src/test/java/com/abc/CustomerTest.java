package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;

public class CustomerTest {
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = "2018-01-01";
    String dateString2 = "2017-07-01";
    String dateString3 = "2017-01-01";
    
    Date date, date2, date3;
    
    @Before
    public void initDates() throws ParseException {
    	date = sdf.parse(dateString);
    	date2 = sdf.parse(dateString2);
    	date3 = sdf.parse(dateString2);
    }

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0,date3);
        savingsAccount.deposit(4000.0,date3);
        savingsAccount.withdraw(200.0,date3);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}

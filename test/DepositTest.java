import org.junit.Test;

import static org.junit.Assert.*;

public class DepositTest {

    @Test
    public void testConstructor() throws NoCustomerException, NonPositiveAmountException {
        Customer customer = new Customer("John Doe", "123456789");
        Deposit deposit = new Deposit(customer, 100);

        assertEquals(customer, deposit.getPerformer());
        assertNotNull(deposit.toString()); // Just checking that toString doesn't throw exceptions
        assertThrows(NoCustomerException.class, () -> new Deposit(null, 100));
        assertThrows(NonPositiveAmountException.class, () -> new Deposit(customer, 0));
        assertThrows(NonPositiveAmountException.class, () -> new Deposit(customer, -50));
    }

    @Test
    public void testDoTransaction() throws AccountHolderException, BalanceException, NoCustomerException, NonPositiveAmountException {
        Customer customer = new Customer("John Doe", "123456789");
        BankAccount account = new BankAccount(123, customer);
        Deposit deposit = new Deposit(customer, 100);

        deposit.performTransaction(account);

        assertEquals(100, account.getBalance(), 0.0);
        assertEquals(1, account.getTransactionHistory().size());
        assertTrue(account.getTransactionHistory().contains(deposit));
    }
}


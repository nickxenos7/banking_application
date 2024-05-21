import org.junit.Test;

import static org.junit.Assert.*;

public class WithdrawalTest {

    @Test
    public void testConstructor() throws NoCustomerException, NonPositiveAmountException {
        Customer customer = new Customer("John Doe", "123456789");
        Withdrawal withdrawal = new Withdrawal(customer, 50);

        assertEquals(customer, withdrawal.getPerformer());
        assertNotNull(withdrawal.toString()); // Just checking that toString doesn't throw exceptions
        assertThrows(NoCustomerException.class, () -> new Withdrawal(null, 50));
        assertThrows(NonPositiveAmountException.class, () -> new Withdrawal(customer, 0));
        assertThrows(NonPositiveAmountException.class, () -> new Withdrawal(customer, -30));
    }

    @Test
    public void testDoTransaction() throws AccountHolderException, BalanceException, NoCustomerException, NonPositiveAmountException {
        Customer customer = new Customer("John Doe", "123456789");
        BankAccount account = new BankAccount(123, customer);
        account.setBalance(100);
        Withdrawal withdrawal = new Withdrawal(customer, 50);

        withdrawal.performTransaction(account);

        assertEquals(50, account.getBalance(), 0.0);
        assertEquals(1, account.getTransactionHistory().size());
        assertTrue(account.getTransactionHistory().contains(withdrawal));
    }
}

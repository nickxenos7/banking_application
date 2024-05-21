import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionTest {

    @Test
    public void testConstructor() throws NoCustomerException, NonPositiveAmountException {
        Customer customer = new Customer("John Doe", "123456789");
        Transaction transaction = new TestTransaction(customer, 100);

        assertEquals(customer, transaction.getPerformer());
        assertNotNull(transaction.toString()); // Just checking that toString doesn't throw exceptions
        assertThrows(NoCustomerException.class, () -> new TestTransaction(null, 100));
        assertThrows(NonPositiveAmountException.class, () -> new TestTransaction(customer, 0));
        assertThrows(NonPositiveAmountException.class, () -> new TestTransaction(customer, -50));
    }

    @Test
    public void testPerformTransaction() throws AccountHolderException, BalanceException, NoCustomerException, NonPositiveAmountException {
        Customer customer = new Customer("John Doe", "123456789");
        BankAccount account = new BankAccount(123, customer);
        Transaction transaction = new TestTransaction(customer, 50);

        transaction.performTransaction(account);

        assertEquals(50, account.getBalance(), 0.0);
        assertEquals(1, account.getTransactionHistory().size());
        assertTrue(account.getTransactionHistory().contains(transaction));
    }

    // A simple extension of the abstract Transaction class for testing
    private static class TestTransaction extends Transaction {
        public TestTransaction(Customer performer, double transactionAmount) throws NoCustomerException, NonPositiveAmountException {
            super(performer, transactionAmount);
        }

        @Override
        protected void doTransaction(BankAccount account, double transactionAmount) throws AccountHolderException, BalanceException {
            account.setBalance(account.getBalance() + transactionAmount);
        }
    }
}

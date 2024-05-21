import org.junit.Test;
import static org.junit.Assert.*;

public class BankAccountTest {

    @Test
    public void testConstructor() throws NoCustomerException {
        Customer customer = new Customer("John Doe", "123456789");
        BankAccount account = new BankAccount(123, customer);

        assertEquals(123, account.getNumber());
        assertEquals(0, account.getBalance(), 0.0);
        assertNotNull(account.getTransactionHistory());
        assertTrue(account.getTransactionHistory().isEmpty());
        assertEquals(customer, account.isOwner(customer) ? customer : null);
    }

    @Test
    public void testAddTransactionToHistory() throws NoCustomerException, NonPositiveAmountException {
        Customer customer = new Customer("John Doe", "123456789");
        BankAccount account = new BankAccount(123, customer);

        Transaction transaction = new Deposit(customer, 100);
        account.addTransactionToHistory(transaction);

        assertEquals(1, account.getTransactionHistory().size());
        assertTrue(account.getTransactionHistory().contains(transaction));
    }

    @Test
    public void testSetBalance() throws NoCustomerException, BalanceException {
        BankAccount account = createDummyAccount();

        account.setBalance(500.50);
        assertEquals(500.50, account.getBalance(), 0.0);

        assertThrows(BalanceException.class, () -> account.setBalance(-100));
    }

    @Test
    public void testIsOwner() throws NoCustomerException {
        Customer owner = new Customer("John Doe", "123456789");
        BankAccount account = new BankAccount(123, owner);

        Customer notOwner = new Customer("Jane Doe", "987654321");

        assertTrue(account.isOwner(owner));
        assertFalse(account.isOwner(notOwner));
    }

    @Test
    public void testAddAccountHolder() throws NoCustomerException, AccountHolderException {
        BankAccount account = createDummyAccount();
        Customer newHolder = new Customer("Jane Doe", "987654321");

        account.addAccountHolder(newHolder);
        assertEquals(newHolder, account.isOwner(newHolder) ? newHolder : null);

        assertThrows(AccountHolderException.class, () -> account.addAccountHolder(newHolder));
        assertThrows(AccountHolderException.class, () -> account.addAccountHolder(null));
    }

    @Test
    public void testRemoveAccountHolder() throws NoCustomerException, AccountHolderException {
        Customer owner = new Customer("John Doe", "123456789");
        BankAccount account = new BankAccount(123, owner);

        Customer holderToRemove = new Customer("Jane Doe", "987654321");
        account.addAccountHolder(holderToRemove);

        account.removeAccountHolder(holderToRemove);
        assertFalse(account.isOwner(holderToRemove));

        assertThrows(AccountHolderException.class, () -> account.removeAccountHolder(holderToRemove));
        assertThrows(AccountHolderException.class, () -> account.removeAccountHolder(null));
    }

    // Helper method to create a dummy account for testing
    private BankAccount createDummyAccount() throws NoCustomerException {
        Customer customer = new Customer("John Doe", "123456789");
        return new BankAccount(123, customer);
    }
}

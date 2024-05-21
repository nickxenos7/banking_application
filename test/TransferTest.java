import org.junit.Test;

import static org.junit.Assert.*;

public class TransferTest {

    @Test
    public void testConstructor() throws NoCustomerException, NonPositiveAmountException {
        Customer customer = new Customer("John Doe", "123456789");
        BankAccount otherAccount = new BankAccount(456, new Customer("Jane Doe", "987654321"));

        Transfer transfer = new Transfer(customer, 50, otherAccount);

        assertEquals(customer, transfer.getPerformer());
        assertNotNull(transfer.toString()); // Just checking that toString doesn't throw exceptions
        assertThrows(NoCustomerException.class, () -> new Transfer(null, 50, otherAccount));
        assertThrows(NonPositiveAmountException.class, () -> new Transfer(customer, 0, otherAccount));
        assertThrows(NonPositiveAmountException.class, () -> new Transfer(customer, -30, otherAccount));
        assertThrows(NullPointerException.class, () -> new Transfer(customer, 50, null));
    }

    @Test
    public void testDoTransaction() throws AccountHolderException, BalanceException, NoCustomerException, NonPositiveAmountException {
        Customer sender = new Customer("John Doe", "123456789");
        Customer receiver = new Customer("Jane Doe", "987654321");
        BankAccount senderAccount = new BankAccount(123, sender);
        BankAccount receiverAccount = new BankAccount(456, receiver);
        receiverAccount.addAccountHolder(sender);
        senderAccount.setBalance(100);
        receiverAccount.setBalance(50);

        Transfer transfer = new Transfer(sender, 30, receiverAccount);

        transfer.performTransaction(senderAccount);

        assertEquals(70, senderAccount.getBalance(), 0.0);
        assertEquals(80, receiverAccount.getBalance(), 0.0);
        assertEquals(1, receiverAccount.getTransactionHistory().size());
        assertTrue(receiverAccount.getTransactionHistory().contains(transfer));
    }
}

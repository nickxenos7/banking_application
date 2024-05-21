import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class BankingApplicationTest {

    @Test
    public void testRegisterCustomer() throws DuplicateCustomerException {
        BankingApplication bankingApp = new BankingApplication();
        Customer customer = bankingApp.registerCustomer("John Doe", "123456789");

        assertEquals("John Doe", customer.getName());
        assertEquals("123456789", customer.getTaxNumber());
        assertEquals(customer, bankingApp.findCustomerByTaxNumber("123456789"));

        // Test duplicate customer
        assertThrows(DuplicateCustomerException.class, () -> bankingApp.registerCustomer("Jane Doe", "123456789"));
    }

    @Test
    public void testCreateBankAccount() throws NoCustomerException, DuplicateCustomerException {
        BankingApplication bankingApp = new BankingApplication();
        Customer customer = bankingApp.registerCustomer("John Doe", "123456789");
        BankAccount account = bankingApp.createBankAccount(customer);

        assertEquals(customer, account.isOwner(customer) ? customer : null);
        assertEquals(account, bankingApp.findBankAccountByNumber(account.getNumber()));

        // Test create account for non-existing customer
        Customer nonExistingCustomer = new Customer("Jane Doe", "987654321");
        assertThrows(NoCustomerException.class, () -> bankingApp.createBankAccount(nonExistingCustomer));
    }

    @Test
    public void testFindCustomerByTaxNumber() throws DuplicateCustomerException {
        BankingApplication bankingApp = new BankingApplication();
        Customer customer = bankingApp.registerCustomer("John Doe", "123456789");

        assertEquals(customer, bankingApp.findCustomerByTaxNumber("123456789"));
        assertNull(bankingApp.findCustomerByTaxNumber("987654321"));
    }

    @Test
    public void testFindBankAccountByNumber() throws NoCustomerException, DuplicateCustomerException {
        BankingApplication bankingApp = new BankingApplication();
        Customer customer = bankingApp.registerCustomer("John Doe", "123456789");
        BankAccount account = bankingApp.createBankAccount(customer);

        assertEquals(account, bankingApp.findBankAccountByNumber(account.getNumber()));
        assertNull(bankingApp.findBankAccountByNumber(999));
    }

    @Test
    public void testProduceCustomerReport() throws DuplicateCustomerException {
        BankingApplication bankingApp = new BankingApplication();
        bankingApp.registerCustomer("John Doe", "123456789");
        bankingApp.registerCustomer("Jane Doe", "987654321");

        String expectedReport = "Customer{name='John Doe', taxNumber='123456789'}\n" +
                "Customer{name='Jane Doe', taxNumber='987654321'}\n";

        assertEquals(expectedReport, bankingApp.produceCustomerReport());
    }

    @Test
    public void testProduceAccountsReport() throws DuplicateCustomerException, NoCustomerException {
        BankingApplication bankingApp = new BankingApplication();
        Customer customer = bankingApp.registerCustomer("John Doe", "123456789");
        BankAccount account1 = bankingApp.createBankAccount(customer);
        BankAccount account2 = bankingApp.createBankAccount(customer);

        Date now = new Date();

        String expectedReport = "BankAccount{number=0, dateOpened="+ now +", balance=0.0, holders=[Customer{name='John Doe', taxNumber='123456789'}, null]}\n" +
                "BankAccount{number=1, dateOpened="+ now +", balance=0.0, holders=[Customer{name='John Doe', taxNumber='123456789'}, null]}\n";

        assertEquals(expectedReport, bankingApp.produceAccountsReport());
    }

    @Test
    public void testProduceTransactionHistoryReport() throws DuplicateCustomerException, NoCustomerException, NonPositiveAmountException, AccountHolderException, BalanceException {
        BankingApplication bankingApp = new BankingApplication();
        Customer customer = bankingApp.registerCustomer("John Doe", "123456789");
        BankAccount account = bankingApp.createBankAccount(customer);

        Deposit deposit = new Deposit(customer, 100);
        Withdrawal withdrawal = new Withdrawal(customer, 50);

        BankAccount destination = bankingApp.createBankAccount(bankingApp.registerCustomer("Jane Doe", "987654321"));
        destination.addAccountHolder(customer);

        Transfer transfer = new Transfer(customer, 30, destination);

        deposit.performTransaction(account);
        withdrawal.performTransaction(account);
        transfer.performTransaction(account);

        Date now = new Date();
        String expectedReport = "Transaction{datePerformed="+ now +", performer=Customer{name='John Doe', taxNumber='123456789'}, transactionAmount=100.0} = Deposit{}\n" +
                "Transaction{datePerformed="+ now +", performer=Customer{name='John Doe', taxNumber='123456789'}, transactionAmount=50.0} = Withdrawal{}\n" +
                "Transaction{datePerformed="+ now +", performer=Customer{name='John Doe', taxNumber='123456789'}, transactionAmount=30.0} = Transfer{accountToTransferTo=BankAccount{number=1, dateOpened="+ now +", balance=30.0, holders=[Customer{name='Jane Doe', taxNumber='987654321'}, Customer{name='John Doe', taxNumber='123456789'}]}}\n";

        assertEquals(expectedReport, bankingApp.produceTransactionHistoryReport(account.getNumber()));
    }
}

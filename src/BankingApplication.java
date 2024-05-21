import java.util.ArrayList;
import java.util.List;

public class BankingApplication {
    private List<Customer> allCustomers;
    private List<BankAccount> allAccounts;

    public BankingApplication() {
        this.allCustomers = new ArrayList<>();
        this.allAccounts = new ArrayList<>();
    }

    public Customer registerCustomer(String name, String taxNumber) throws DuplicateCustomerException {
        for (int i = 0; i < this.allCustomers.size(); i++) {
            if (allCustomers.get(i).getTaxNumber().equals(taxNumber)) {
                throw new DuplicateCustomerException("A customer with this tax number already exists");
            }
        }

        Customer customer = new Customer(name, taxNumber);
        this.allCustomers.add(customer);

        return customer;
    }

    public BankAccount createBankAccount(Customer primaryHolder) throws NoCustomerException {
        if (!this.allCustomers.contains(primaryHolder)) {
            throw new NoCustomerException("The customer is not a member of this bank.");
        }

        BankAccount account = new BankAccount(this.allAccounts.size(), primaryHolder);
        this.allAccounts.add(account);

        return account;
    }

    public Customer findCustomerByTaxNumber(String taxNumber) {
        for (int i = 0; i < this.allCustomers.size(); i++) {
            if (allCustomers.get(i).getTaxNumber().equals(taxNumber)) {
                return allCustomers.get(i);
            }
        }

        return null;
    }

    public BankAccount findBankAccountByNumber(int number) {
        for (int i = 0; i < this.allAccounts.size(); i++) {
            if (allAccounts.get(i).getNumber() == number) {
                return allAccounts.get(i);
            }
        }

        return null;
    }

    public String produceCustomerReport() {
        String customerReport = "";
        for (int i = 0; i < this.allCustomers.size(); i++) {
            customerReport += this.allCustomers.get(i) + "\n";
        }

        return customerReport;
    }

    public String produceAccountsReport() {
        String accountsReport = "";
        for (int i = 0; i < this.allAccounts.size(); i++) {
            accountsReport += this.allAccounts.get(i) + "\n";
        }

        return accountsReport;
    }

    public String produceTransactionHistoryReport(int accountNumber) {
        String historyReport = "";
        BankAccount account = findBankAccountByNumber(accountNumber);
        if (account != null) {
            List<Transaction> transactionHistory = account.getTransactionHistory();
            for (int i = 0; i < transactionHistory.size(); i++) {
                historyReport += transactionHistory.get(i) + "\n";
            }
        }

        return historyReport;
    }
}

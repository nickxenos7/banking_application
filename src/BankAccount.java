import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BankAccount {
    private int number;
    private Date dateOpened;
    private double balance;
    private Customer[] holders;
    private List<Transaction> transactionHistory;

    public BankAccount(int number, Customer holder) throws NoCustomerException {
        if (holder == null) {
            throw new NoCustomerException("Must provide at least one account holder when creating a new account");
        }

        this.number = number;
        this.dateOpened = new Date();
        this.balance = 0;
        this.holders = new Customer[2];
        this.holders[0] = holder;
        this.transactionHistory = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public void addTransactionToHistory(Transaction transaction) {
        if (transaction == null) {
            throw new NullPointerException("Can't add null transaction to transactionHistory of a Bank Account.");
        }

        this.transactionHistory.add(transaction);
    }

    public List<Transaction> getTransactionHistory() {
        return this.transactionHistory;
    }

    public void setBalance(double newBalance) throws BalanceException {
        if (newBalance < 0) {
            throw new BalanceException("Can't set the balance to a negative number.");
        }

        this.balance = newBalance;
    }

    public boolean isOwner(Customer customer) {
        if (customer == null) {
            throw new NullPointerException("Can't check a null owner.");
        }

        return customer.equals(holders[0]) || customer.equals(holders[1]);
    }

    public void addAccountHolder(Customer newHolder) throws AccountHolderException {
        if (newHolder == null) {
            throw new AccountHolderException("The new account holder must not be null.");
        }

        if (newHolder.equals(holders[0]) || newHolder.equals(holders[1])) {
            throw new AccountHolderException("The new holder " + newHolder + " is already a holder of this account.");
        }

        if (holders[0] != null && holders[1] != null) {
            throw new AccountHolderException("This account already has the max number of holders. Remove one before adding another.");
        }

        if (holders[0] == null) {
            holders[0] = newHolder;
        } else {
            holders[1] = newHolder;
        }
    }

    public void removeAccountHolder(Customer holder) throws AccountHolderException {
        if (holder == null) {
            throw new AccountHolderException("The account holder to remove must not be null.");
        }

        if (!holder.equals(holders[0]) && !holder.equals(holders[1])) {
            throw new AccountHolderException("The holder to remove " + holder + " is not a holder of this account.");
        }

        if (holders[0] == null || holders[1] == null) {
            throw new AccountHolderException("This account already has the min number of holders. Can't remove the holder, before adding a new one.");
        }

        if (holder.equals(holders[0])) {
            holders[0] = null;
        } else {
            holders[1] = null;
        }
    }

    public String toString() {
        return "BankAccount{" +
                "number=" + number +
                ", dateOpened=" + dateOpened +
                ", balance=" + balance +
                ", holders=" + Arrays.toString(holders) +
                '}';
    }
}

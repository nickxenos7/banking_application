import java.util.Date;

public abstract class Transaction {

    private Date datePerformed;
    private Customer performer;
    private double transactionAmount;

    public Transaction(Customer performer, double transactionAmount) throws NoCustomerException, NonPositiveAmountException {
        if (performer == null) {
            throw new NoCustomerException("Must provide a customer when performing a transaction.");
        }

        if (transactionAmount <= 0) {
            throw new NonPositiveAmountException("Must transact a positive amount");
        }

        this.datePerformed = new Date();
        this.performer = performer;
        this.transactionAmount = transactionAmount;
    }

    public Customer getPerformer() {
        return performer;
    }

    public void performTransaction(BankAccount account) throws AccountHolderException, BalanceException {
        if (account == null) {
            throw new NullPointerException("Must provide a bank account to perform the transaction on.");
        }

        if (!account.isOwner(this.performer)) {
            throw new AccountHolderException(this.performer, account);
        }

        this.doTransaction(account, transactionAmount);
        account.addTransactionToHistory(this);
    }

    public String toString() {
        return "Transaction{" +
                "datePerformed=" + datePerformed +
                ", performer=" + performer +
                ", transactionAmount=" + transactionAmount +
                '}';
    }

    protected abstract void doTransaction(BankAccount account, double transactionAmount) throws AccountHolderException, BalanceException;
}

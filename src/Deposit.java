public class Deposit extends Transaction {

    public Deposit(Customer performer, double amountToDeposit) throws NoCustomerException, NonPositiveAmountException {
        super(performer, amountToDeposit);
    }

    public String toString() {
        return super.toString() + " = Deposit{}";
    }

    protected void doTransaction(BankAccount account, double transactionAmount) throws BalanceException {
        account.setBalance(account.getBalance() + transactionAmount);
    }
}

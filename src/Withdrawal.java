public class Withdrawal extends Transaction {

    public Withdrawal(Customer performer, double amountToWithdraw) throws NoCustomerException, NonPositiveAmountException {
        super(performer, amountToWithdraw);
    }

    public String toString() {
        return super.toString() + " = Withdrawal{}";
    }

    protected void doTransaction(BankAccount account, double transactionAmount) throws BalanceException {
        if (account.getBalance() < transactionAmount) {
            throw new BalanceException("Can't withdraw more than the current account balance.");
        }

        account.setBalance(account.getBalance() - transactionAmount);
    }
}

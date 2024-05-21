public class Transfer extends Transaction {

    private BankAccount accountToTransferTo;

    public Transfer(Customer performer, double amountToTransfer, BankAccount accountToTransferTo) throws NoCustomerException, NonPositiveAmountException {
        super(performer, amountToTransfer);

        if (accountToTransferTo == null) {
            throw new NullPointerException("Must provide an account to transfer to!");
        }

        this.accountToTransferTo = accountToTransferTo;
    }

    public String toString() {
        return super.toString() + " = Transfer{" +
                "accountToTransferTo=" + accountToTransferTo +
                '}';
    }

    protected void doTransaction(BankAccount account, double transactionAmount) throws AccountHolderException, BalanceException {
        if (!this.accountToTransferTo.isOwner(this.getPerformer())) {
            throw new AccountHolderException(this.getPerformer(), this.accountToTransferTo);
        }

        if (accountToTransferTo.equals(account)) {
            throw new AccountHolderException("You are transferring from the same account to the same account");
        }

        if (account.getBalance() < transactionAmount) {
            throw new BalanceException("Can't transfer more than the current account balance.");
        }

        account.setBalance(account.getBalance() - transactionAmount);
        accountToTransferTo.setBalance(accountToTransferTo.getBalance() + transactionAmount);
        accountToTransferTo.addTransactionToHistory(this);
    }
}

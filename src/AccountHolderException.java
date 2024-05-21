public class AccountHolderException extends Exception {

    public AccountHolderException(String msg) {
        super(msg);
    }

    public AccountHolderException(Customer customer, BankAccount account) {
        super("Customer with name " + customer.getName() + " is not the account holder for account " + account.getNumber());
    }
}

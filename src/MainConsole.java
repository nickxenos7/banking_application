import java.util.Scanner;

public class MainConsole {

    public static void main(String[] args) {
        BankingApplication app = new BankingApplication();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=============================" +
                    "\n\tPlease input command:" +
                    "\n=============================");
            String command = scanner.nextLine();
            processCommand(command, scanner, app);
        }
    }

    private static void processCommand(String command, Scanner scanner, BankingApplication app) {
        switch (command) {
            case "registerCustomer":
                registerCustomer(scanner, app);
                break;
            case "createBankAccount":
                createBankAccount(scanner, app);
                break;
            case "actions":
                actions(scanner, app);
                break;
            case "reports":
                reports(scanner, app);
                break;
            case "help":
                help();
                break;
            case "exit":
                exit();
                break;
            default:
                System.err.println("Unknown command " + command + ".");
                help();
                break;
        }
    }

    private static void registerCustomer(Scanner scanner, BankingApplication app) {
        try {
            System.out.println("Customer name:");
            String customerName = scanner.nextLine();
            System.out.println("Customer tax number:");
            String taxNumber = scanner.nextLine();

            Customer customer = app.registerCustomer(customerName, taxNumber);
            System.out.println(customer);
        } catch (Exception ex) {
            System.err.println("Exception while creating customer: " + ex.getMessage());
        }
    }

    private static void createBankAccount(Scanner scanner, BankingApplication app) {
        try {
            System.out.println("Primary owner tax number");
            String taxNumber = scanner.nextLine();
            Customer customer = app.findCustomerByTaxNumber(taxNumber);
            if (customer != null) {
                BankAccount account = app.createBankAccount(customer);
                System.out.println(account);
            } else {
                System.err.println("No customer exists with that tax number");
            }
        } catch (Exception ex) {
            System.err.println("Exception while creating bank account: " + ex.getMessage());
        }
    }

    private static void actions(Scanner scanner, BankingApplication app) {
        try{
            System.out.println("Customer tax number");
            String taxNumber = scanner.nextLine();
            Customer customer = app.findCustomerByTaxNumber(taxNumber);
            if (customer != null) {
                System.out.println("Number of bank account to perform actions on:");
                int bankAccountNumber = Integer.parseInt(scanner.nextLine());
                BankAccount account = app.findBankAccountByNumber(bankAccountNumber);
                if (account != null) {
                    String actionsHelp = "Here are the actions you can perform on this bank account:" +
                            "\n\taddHolder" +
                            "\n\tremoveHolder" +
                            "\n\twithdraw" +
                            "\n\tdeposit" +
                            "\n\ttransfer";
                    System.out.println(actionsHelp);
                    String actionsCommand = scanner.nextLine();
                    processActionsCommand(actionsCommand, scanner, app, customer, account);
                } else {
                    System.err.println("No account exists with that number");
                }
            } else {
                System.err.println("No customer exists with that tax number");
            }
        } catch (Exception ex) {
            System.err.println("Exception while performing actions on bank account: " + ex.getMessage());
        }
    }

    private static void processActionsCommand(String actionsCommand, Scanner scanner, BankingApplication app, Customer customer, BankAccount account) {
        switch (actionsCommand) {
            case "addHolder":
                addHolder(scanner, app, account);
                break;
            case "removeHolder":
                removeHolder(scanner, app, account);
                break;
            case "withdraw":
                withdraw(scanner, customer, account);
                break;
            case "deposit":
                deposit(scanner, customer, account);
                break;
            case "transfer":
                transfer(scanner, app, customer, account);
                break;
            default:
                System.err.println("Unknown actions command " + actionsCommand + ".");
                help();
                break;
        }
    }

    private static void addHolder(Scanner scanner, BankingApplication app, BankAccount account) {
        try {
            System.out.println("Tax number of holder pls:");
            String taxNumber = scanner.nextLine();
            Customer newHolder = app.findCustomerByTaxNumber(taxNumber);
            if (newHolder != null) {
                account.addAccountHolder(newHolder);
                System.out.println(account);
            } else {
                System.err.println("No customer exists with that tax number");
            }
        } catch (Exception ex) {
            System.err.println("Exception while adding holder on bank account: " + ex.getMessage());
        }
    }

    private static void removeHolder(Scanner scanner, BankingApplication app, BankAccount account) {
        try {
            System.out.println("Tax number of holder pls:");
            String taxNumber = scanner.nextLine();
            Customer newHolder = app.findCustomerByTaxNumber(taxNumber);
            if (newHolder != null) {
                account.removeAccountHolder(newHolder);
                System.out.println(account);
            } else {
                System.err.println("No customer exists with that tax number");
            }
        } catch (Exception ex) {
            System.err.println("Exception while removing holder on bank account: " + ex.getMessage());
        }
    }

    private static void withdraw(Scanner scanner, Customer customer, BankAccount account) {
        try {
            System.out.println("How much to withdraw?");
            double amountToWithdraw = Double.parseDouble(scanner.nextLine());
            Withdrawal withdrawal = new Withdrawal(customer, amountToWithdraw);
            withdrawal.performTransaction(account);
            System.out.println(account);
        } catch (Exception ex) {
            System.err.println("Exception while withdrawing from bank account: " + ex.getMessage());
        }
    }

    private static void deposit(Scanner scanner, Customer customer, BankAccount account) {
        try {
            System.out.println("How much to deposit?");
            double amountToDeposit = Double.parseDouble(scanner.nextLine());
            Deposit deposit = new Deposit(customer, amountToDeposit);
            deposit.performTransaction(account);
            System.out.println(account);
        } catch (Exception ex) {
            System.err.println("Exception while depositing from bank account: " + ex.getMessage());
        }
    }

    private static void transfer(Scanner scanner, BankingApplication app, Customer customer, BankAccount account) {
        try {
            System.out.println("How much to transfer?");
            double amountToTransfer = Double.parseDouble(scanner.nextLine());
            System.out.println("Account number of account to transfer to?");
            int accountNumberOfDestination = Integer.parseInt(scanner.nextLine());
            BankAccount destination = app.findBankAccountByNumber(accountNumberOfDestination);
            if (account != null) {
                Transfer transfer = new Transfer(customer, amountToTransfer, destination);
                transfer.performTransaction(account);
                System.out.println(account);
                System.out.println(destination);
            } else {
                System.err.println("No account exists with that number");
            }
        } catch (Exception ex) {
            System.err.println("Exception while transferring from bank account: " + ex.getMessage());
        }
    }

    private static void reports(Scanner scanner, BankingApplication app) {
        try{
            String reportsHelp = "Here are the reports you can get:" +
                    "\n\tcustomers" +
                    "\n\taccounts" +
                    "\n\thistory";
            System.out.println(reportsHelp);
            String reportsCommand = scanner.nextLine();
            processReportsCommand(reportsCommand, scanner, app);
        } catch (Exception ex) {
            System.err.println("Exception while performing actions on bank account: " + ex.getMessage());
        }
    }

    private static void processReportsCommand(String reportsCommand, Scanner scanner, BankingApplication app) {
        switch (reportsCommand) {
            case "customers":
                System.out.println(app.produceCustomerReport());
                break;
            case "accounts":
                System.out.println(app.produceAccountsReport());
                break;
            case "history":
                System.out.println("Account number of account to get history of?");
                int accountNumber = Integer.parseInt(scanner.nextLine());
                System.out.println(app.produceTransactionHistoryReport(accountNumber));
                break;
            default:
                System.err.println("Unknown reports command " + reportsCommand + ".");
                help();
                break;
        }
    }

    private static void help() {
        String commands = "Possible commands are:" +
                "\n\tregisterCustomer" +
                "\n\tcreateBankAccount" +
                "\n\tactions" +
                "\n\treports" +
                "\n\thelp (this command)" +
                "\n\texit";
        System.out.println(commands);
    }

    private static void exit() {
        System.out.println("Byeeeeee");
        System.exit(0);
    }
}

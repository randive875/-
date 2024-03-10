import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Transaction Class
class Transaction {
    private String type;
    private double amount;
    private LocalDateTime dateTime;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type='" + type + '\'' +
                ", amount=" + amount +
                ", dateTime=" + dateTime +
                '}';
    }
}

// User Class
class User {
    private String userId;
    private String pin;
    private List<Transaction> transactionHistory;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean authenticate(String userId, String pin) {
        return this.userId.equals(userId) && this.pin.equals(pin);
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}

// ATM Class
public class ATM {
    private User currentUser;
    private Scanner scanner;

    public ATM() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the ATM");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (authenticate(userId, pin)) {
            System.out.println("Authentication successful.");
            displayMenu();
        } else {
            System.out.println("Invalid User ID or PIN. Exiting...");
        }
    }

    private boolean authenticate(String userId, String pin) {
        // For simplicity, hardcoding user credentials
        if ("1234".equals(userId) && "4321".equals(pin)) {
            currentUser = new User(userId, pin);
            return true;
        } else {
            return false;
        }
    }

    private void displayMenu() {
        boolean quit = false;
        while (!quit) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Display Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    displayTransactionHistory();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }

    private void displayTransactionHistory() {
        System.out.println("\nTransaction History:");
        List<Transaction> transactions = currentUser.getTransactionHistory();
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        if (amount > 0) {
            currentUser.addTransaction(new Transaction("Withdrawal", amount));
            System.out.println("Withdrawal of $" + amount + " successful.");
        } else {
            System.out.println("Invalid amount. Please enter a positive value.");
        }
    }

    private void deposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        if (amount > 0) {
            currentUser.addTransaction(new Transaction("Deposit", amount));
            System.out.println("Deposit of $" + amount + " successful.");
        } else {
            System.out.println("Invalid amount. Please enter a positive value.");
        }
    }

    private void transfer() {
        System.out.print("Enter recipient's user ID: ");
        String recipientId = scanner.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        if (amount > 0) {
            currentUser.addTransaction(new Transaction("Transfer to " + recipientId, amount));
            System.out.println("Transfer of $" + amount + " to " + recipientId + " successful.");
        } else {
            System.out.println("Invalid amount. Please enter a positive value.");
        }
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}

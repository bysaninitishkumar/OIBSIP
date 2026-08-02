import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ATM {
    private final Scanner scanner;
    private final Bank bank;

    public ATM() {
        scanner = new Scanner(System.in);
        bank = new Bank();
    }

    public void run() {
        System.out.println("===== ATM LOGIN =====");
        int attempts = 0;

        while (attempts < 3) {
            System.out.print("Enter User ID: ");
            int userId = readInt();
            System.out.print("Enter PIN: ");
            int pin = readInt();

            if (bank.authenticate(userId, pin)) {
                showMenu();
                return;
            }

            attempts++;
            System.out.println("Invalid User ID or PIN. Please try again.");

            if (attempts == 3) {
                System.out.println("Access denied. Maximum login attempts exceeded.");
                return;
            }
        }
    }

    private void showMenu() {
        int choice;
        do {
            System.out.println();
            System.out.println("===== ATM MENU =====");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
                choice = -1;
            }

            switch (choice) {
                case 1:
                    displayTransactionHistory();
                    break;
                case 2:
                    performWithdraw();
                    break;
                case 3:
                    performDeposit();
                    break;
                case 4:
                    performTransfer();
                    break;
                case 5:
                    System.out.println("Thank you for using our ATM.");
                    System.out.println("Have a nice day!");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (choice != 5);
    }

    private void displayTransactionHistory() {
        ArrayList<Transaction> history = bank.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No transactions performed in this session.");
            return;
        }

        System.out.println("===== TRANSACTION HISTORY =====");
        for (Transaction transaction : history) {
            System.out.println(transaction);
        }
    }

    private void performWithdraw() {
        System.out.print("Enter withdrawal amount: ");
        double amount = readDouble();

        if (!bank.withdraw(amount)) {
            System.out.println("Insufficient Funds");
            return;
        }

        System.out.println("Withdrawal successful.");
        System.out.println("Updated Balance: ₹" + bank.getBalance());
    }

    private void performDeposit() {
        System.out.print("Enter deposit amount: ");
        double amount = readDouble();

        if (!bank.deposit(amount)) {
            System.out.println("Invalid amount.");
            return;
        }

        System.out.println("Deposit successful.");
        System.out.println("Updated Balance: ₹" + bank.getBalance());
    }

    private void performTransfer() {
        System.out.print("Enter recipient account ID: ");
        int recipientId = readInt();
        System.out.print("Enter transfer amount: ");
        double amount = readDouble();

        if (!bank.transfer(recipientId, amount)) {
            System.out.println("Insufficient Funds or invalid recipient account.");
            return;
        }

        System.out.println("Transfer successful.");
        System.out.println("Updated Balance: ₹" + bank.getBalance());
    }

    private int readInt() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        }
    }

    private double readDouble() {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid amount.");
                scanner.nextLine();
            }
        }
    }
}

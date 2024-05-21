import java.util.Scanner;

public class BankingApplication {
  

    // Variables to store account balance
    private double balance;

    // Constructor to initialize the balance
    public BankingApplication() {
        balance = 0.0;
    }

    // Method to check balance
    public void checkBalance() {
        System.out.println("Current balance: Rs." + balance);
    }

    // Method to deposit money
    public void deposit(double amount) {
        if (amount > 0) {
            balance = balance + amount;
            System.out.println("Successfully deposited Rs." + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Method to withdraw money
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Successfully withdrew Rs." + amount);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }

    // Main method to run the banking application
    public static void main(String[] args) {
        System.out.println("WELCOME TO OUR BANK!!");
        Scanner scanner = new Scanner(System.in);
        BankingApplication bank = new BankingApplication();
        boolean exit = false;

        while (!exit) {
            System.out.println("\nBanking Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bank.checkBalance();
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    bank.deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    bank.withdraw(withdrawAmount);
                    break;
                case 4:
                    exit = true;
                    System.out.println("Thank you for using the banking application.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
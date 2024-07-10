package javaapplications;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Config {
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private String dbName;

    public Config() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter database URL: ");
        this.dbUrl = scanner.nextLine();
        System.out.print("Enter database name: ");
        this.dbName = scanner.nextLine();
        System.out.print("Enter database username: ");
        this.dbUsername = scanner.nextLine();
        System.out.print("Enter database password: ");
        this.dbPassword = scanner.nextLine();
    }

    public String getDbUrl() {
        return dbUrl + "/" + dbName;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }
}

class DatabaseConnection {
    private static Config config;
    private static Connection connection;

    public static void setConfig(Config config) {
        DatabaseConnection.config = config;
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(config.getDbUrl(), config.getDbUsername(), config.getDbPassword());
                System.out.println("Connection established successfully.");
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
                throw new SQLException("Unable to establish a connection.");
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

public class BankingApplication {
    public static void main(String[] args) {
        Config config = new Config();
        DatabaseConnection.setConfig(config);
        SwingUtilities.invokeLater(() -> new FirstFrame());
    }
}

class FirstFrame implements ActionListener {
    JFrame frame1;
    JButton loginButton, registerButton;
    JPanel panel = new JPanel();

    public FirstFrame() {
        frame1 = new JFrame("Login Page");
        frame1.setSize(600, 600);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label1 = new JLabel("Welcome to our Bank!");
        JLabel label2 = new JLabel("Are you an existing User? Yes -> Please Login");
        loginButton = new JButton("Login");
        JLabel label3 = new JLabel("No -> Register Yourself!");
        registerButton = new JButton("Register");
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

        panel.setLayout(new GridLayout(7, 1, 10, 10));
        panel.add(label1);
        panel.add(label2);
        panel.add(loginButton);
        panel.add(label3);
        panel.add(registerButton);
        frame1.add(panel);
        frame1.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            frame1.dispose();
            new Login();
        } else if (e.getSource() == registerButton) {
            frame1.dispose();
            new Register();
        }
    }
}

class Login implements ActionListener {
    JFrame frame;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;

    public Login() {
        frame = new JFrame("Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");

        loginButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (authenticate(username, password)) {
                frame.dispose();
                new MainFrame(username);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.");
            }
        }
    }

    private boolean authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}

class Register implements ActionListener {
    JFrame frame;
    JTextField usernameField;
    JPasswordField passwordField, confirmPasswordField;
    JButton registerButton;

    public Register() {
        frame = new JFrame("Register");
        frame.setSize(300, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        confirmPasswordField = new JPasswordField(15);
        registerButton = new JButton("Register");

        registerButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Confirm Password:"));
        panel.add(confirmPasswordField);
        panel.add(registerButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            if (password.equals(confirmPassword)) {
                if (registerUser(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Registration successful. Your account number is: " + getAccountNumber(username));
                    frame.dispose();
                    new Login();
                } else {
                    JOptionPane.showMessageDialog(frame, "Username already exists.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Passwords do not match.");
            }
        }
    }

    private boolean registerUser(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();

            String accountNumber = generateAccountNumber();
            String accountQuery = "INSERT INTO accounts (username, account_number) VALUES (?, ?)";
            try (PreparedStatement accountStmt = connection.prepareStatement(accountQuery)) {
                accountStmt.setString(1, username);
                accountStmt.setString(2, accountNumber);
                accountStmt.executeUpdate();
            }

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private String getAccountNumber(String username) {
        String query = "SELECT account_number FROM accounts WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("account_number");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private String generateAccountNumber() {
        Random random = new Random();
        return "AC" + System.currentTimeMillis() + random.nextInt(1000);
    }
}

class MainFrame implements ActionListener {
    JFrame frame;
    JButton[] buttons = new JButton[5];
    JPanel panel = new JPanel();
    JLabel label1, label2, label3;
    double balance = 0;
    ArrayList<String> transactions = new ArrayList<>();
    String username;
    String accountNumber;

    public MainFrame(String username) {
        this.username = username;
        this.balance = getBalance();
        this.transactions = getTransactions();
        this.accountNumber = getAccountNumber();

        frame = new JFrame("Main Account Page");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label1 = new JLabel("Welcome to your Account, " + username);
        label2 = new JLabel("Your Balance: " + balance);
        label3 = new JLabel("Account Number: " + accountNumber);

        String[] buttonLabels = {"Deposit", "Withdraw", "Check Balance", "Transactions", "Close"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].addActionListener(this);
        }

        panel.setLayout(new GridLayout(8, 1, 10, 10));
        panel.add(label1);
        panel.add(label3);
        panel.add(label2);
        for (JButton button : buttons) {
            panel.add(button);
        }

        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttons[0]) {
            new Deposit(this);
        } else if (e.getSource() == buttons[1]) {
            new Withdraw(this);
        } else if (e.getSource() == buttons[2]) {
            new CheckBalance(balance);
        } else if (e.getSource() == buttons[3]) {
            new Transaction(transactions);
        } else if (e.getSource() == buttons[4]) {
            DatabaseConnection.closeConnection();
            System.exit(0);
        }
    }

    public void depositUpdateBalance(double amount, String type) {
        balance += amount;
        transactions.add(type + ": " + amount);
        updateBalanceInDB();
        addTransaction(type, amount);
        label2.setText("Your Balance: " + balance);
    }

    public void withdrawUpdateBalance(double amount, String type) {
        balance -= amount;
        transactions.add(type + ": " + amount);
        updateBalanceInDB();
        addTransaction(type, amount);
        label2.setText("Your Balance: " + balance);
    }

    private void updateBalanceInDB() {
        String query = "UPDATE accounts SET balance = ? WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, balance);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addTransaction(String type, double amount) {
        String query = "INSERT INTO transactions (username, type, amount) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, type);
            preparedStatement.setDouble(3, amount);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private double getBalance() {
        String query = "SELECT balance FROM accounts WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("balance");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0.0;
    }

    private ArrayList<String> getTransactions() {
        String query = "SELECT type, amount FROM transactions WHERE username = ?";
        ArrayList<String> transactionList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String type = resultSet.getString("type");
                double amount = resultSet.getDouble("amount");
                transactionList.add(type + ": " + amount);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return transactionList;
    }

    private String getAccountNumber() {
        String query = "SELECT account_number FROM accounts WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("account_number");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

class Deposit implements ActionListener {
    JFrame frame;
    JTextField amountField;
    JButton depositButton;
    MainFrame mainFrameInstance;

    public Deposit(MainFrame mainFrameInstance) {
        this.mainFrameInstance = mainFrameInstance;
        frame = new JFrame("Deposit");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        amountField = new JTextField(15);
        depositButton = new JButton("Deposit");

        depositButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(depositButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == depositButton) {
            double amount = Double.parseDouble(amountField.getText());
            mainFrameInstance.depositUpdateBalance(amount, "Deposit");
            frame.dispose();
        }
    }
}

class Withdraw implements ActionListener {
    JFrame frame;
    JTextField amountField;
    JButton withdrawButton;
    MainFrame mainFrameInstance;

    public Withdraw(MainFrame mainFrameInstance) {
        this.mainFrameInstance = mainFrameInstance;
        frame = new JFrame("Withdraw");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        amountField = new JTextField(15);
        withdrawButton = new JButton("Withdraw");

        withdrawButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(withdrawButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == withdrawButton) {
            double amount = Double.parseDouble(amountField.getText());
            mainFrameInstance.withdrawUpdateBalance(amount, "Withdraw");
            frame.dispose();
        }
    }
}

class CheckBalance {
    JFrame frame;

    public CheckBalance(double balance) {
        frame = new JFrame("Balance");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Current Balance: Rs. " + balance));

        frame.add(panel);
        frame.setVisible(true);
    }
}

class Transaction {
    JFrame frame;

    public Transaction(ArrayList<String> transactions) {
        frame = new JFrame("Transactions");
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (String transaction : transactions) {
            panel.add(new JLabel(transaction));
        }

        frame.add(panel);
        frame.setVisible(true);
    }
}

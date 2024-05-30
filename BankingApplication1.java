import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BankingApplication1 {
       public static void main(String[] args)
    { 
          new FirstFrame();
    }
}
class FirstFrame implements ActionListener{
    JFrame frame1;
    JButton Login, Register;
    JLabel label1,label2,label3;
    JPanel panel= new JPanel();
    public FirstFrame(){
        frame1 = new JFrame("Login Page");
        frame1.setSize(600, 600);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
       label1= new JLabel("Welcome to our Bank!");
       label2= new JLabel("Are you existing User?\n Yes-> Please Login");
       Login = new JButton("Login"); 
       label3= new JLabel(" No-> Register Yourself!");
       Register = new JButton("Register");
       Login.addActionListener(this);
       Register.addActionListener(this);
       
       panel.setLayout(new GridLayout(7, 1, 10, 10)); 
       panel.add(label1);
       panel.add(label2);
       panel.add(Login);
       panel.add(label3);
       panel.add(Register);
       frame1.add(panel);
       frame1.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Login) {
            new Login();
        } else if (e.getSource() == Register) {
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

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
            if ("Yash".equals(username) && "1234".equals(password)) {
                frame.dispose();
                new mainFrame(); 
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.");
            }
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
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
               
                    JOptionPane.showMessageDialog(frame, "Registration successful.");
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
    
        

class mainFrame implements ActionListener{
    JFrame frame1;
    JLabel label1,label2;
    JButton[] buttons = new JButton[5];
    JPanel panel= new JPanel();

       double balance = 1000.00; 
         ArrayList<String> transactions = new ArrayList<>(); 
    
         
        public mainFrame(){
          
        frame1 = new JFrame("Banking Application");
        frame1.setSize(600, 600);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    
         label1= new JLabel("Welcome to our Bank!");
         label2= new JLabel("Choose the operation you want to perform");
         
 
        buttons[0] = new JButton("Check Balance ");
        buttons[1] = new JButton("Deposit");
        buttons[2] = new JButton("Withdrawl");
        buttons[3] = new JButton("Transactions");
        buttons[4] = new JButton("Close");
    
        
 
        panel.setLayout(new GridLayout(7, 1, 10, 10)); 
        panel.add(label1);
        panel.add(label2);
        for (JButton button : buttons) {
            panel.add(button);
            button.addActionListener(this); 
        }
        
    
        frame1.add(panel);
        frame1.setVisible(true);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttons[0]) {
            new CheckBalance(balance);;
        } else if (e.getSource() == buttons[1]) {
            new Deposit(this);
        } else if (e.getSource() == buttons[2]) {
            new Withdrawl(this);
        } else if (e.getSource() == buttons[3]) {
             new Transaction(transactions); 
        } else if (e.getSource() == buttons[4]) {
             new Close(this); 
        }
    }
     public void depositupdateBalance(double amount, String type) {
        balance += amount;
        transactions.add(type + ": Rs." + amount + ", New Balance: Rs." + balance);
    }
     public void withdrawupdateBalance(double amount, String type) {
        balance -= amount;
        transactions.add(type + ": Rs." + amount + ", New Balance: Rs." + balance);
    }
}


class CheckBalance implements ActionListener {
    JFrame frame2;
    JLabel balanceLabel;
    JButton closeButton, HomeButton;
    JPanel panel;
     
    
    
    public CheckBalance(double balance) {
       
        

        frame2 = new JFrame("Check Balance");
        frame2.setSize(300, 150);
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        

        balanceLabel = new JLabel("Your current balance is: Rs." + balance);
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        closeButton = new JButton("Close");
        HomeButton = new JButton("Home");
        mainFrame mainFrameInstance = null;
        
        
        closeButton.addActionListener(e -> new Close(mainFrameInstance, frame2));
        HomeButton.addActionListener(this);
        

        panel = new JPanel();
        panel.add(balanceLabel);
        panel.add(closeButton);
        panel.add(HomeButton);
        

        frame2.add(panel);
        frame2.setVisible(true);
        
    }
         @Override
    public void actionPerformed(ActionEvent e) {
         if (e.getSource() == closeButton) {
            new Close(this);
        } else if (e.getSource() == HomeButton)  {
            frame2.dispose();
        }
    }
    
    }




class Deposit implements ActionListener {
    JFrame frame2;
    JLabel label;
    JTextField textField;
    JButton submitButton, cancelButton;
    JPanel panel;
     mainFrame mainFrameInstance;
    
    public Deposit(mainFrame mainFrameInstance) {
        this.mainFrameInstance = mainFrameInstance;
        

        frame2 = new JFrame("Deposit");
        frame2.setSize(300, 150);
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        

        label = new JLabel("Enter amount to deposit:");
        textField = new JTextField(15);
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");
        

        submitButton.addActionListener(this);
        cancelButton.addActionListener(this);
        

        panel = new JPanel();
        panel.add(label);
        panel.add(textField);
        panel.add(submitButton);
        panel.add(cancelButton);
        

        frame2.add(panel);
        frame2.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String amountStr = textField.getText();
            try {
                double amount = Double.parseDouble(textField.getText());
            if (amount > 0) {
                mainFrameInstance.depositupdateBalance(amount, "Deposit");
                JOptionPane.showMessageDialog(frame2, "Deposit successful. New balance: Rs." + mainFrameInstance.balance);
                frame2.dispose(); 
            } else {
                JOptionPane.showMessageDialog(frame2, "Please enter a positive amount.");
            }
        }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame2, "Invalid amount. Please enter a number.");
            }
        } else if (e.getSource() == cancelButton) {
            frame2.dispose();
        } 
    }
}


class Withdrawl implements ActionListener {
    JFrame frame2;
    JLabel label;
    JTextField textField;
    JButton submitButton, cancelButton;
    JPanel panel;
    mainFrame mainFrameInstance;
     
    public Withdrawl(mainFrame mainFrameInstance) {
         this.mainFrameInstance = mainFrameInstance;
         

        frame2 = new JFrame("Withdrawl");
        frame2.setSize(300, 150);
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        

        label = new JLabel("Enter amount to withdrawl:");
        textField = new JTextField(15);
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");
        

        submitButton.addActionListener(this);
        cancelButton.addActionListener(this);
        

        panel = new JPanel();
        panel.add(label);
        panel.add(textField);
        panel.add(submitButton);
        panel.add(cancelButton);

        

        frame2.add(panel);
        frame2.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {

            try {
                double amount = Double.parseDouble(textField.getText());
            if (amount > 0) {
                mainFrameInstance.withdrawupdateBalance(amount, "Withdrawl");
                JOptionPane.showMessageDialog(frame2, "Withdrawn successful. New balance: Rs." + mainFrameInstance.balance);
                frame2.dispose(); 
            } else {
                JOptionPane.showMessageDialog(frame2, "Please enter a positive amount.");
            }
        }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame2, "Invalid amount. Please enter a number.");
            }
        } else if (e.getSource() == cancelButton) {
            frame2.dispose(); 
        } 
    }
}

class Transaction implements ActionListener {
    JFrame frame2;
    JPanel panel;
    JTextArea transactionArea;
    JButton closeButton, HomeButton;
public Transaction(ArrayList<String> transactions) {
     
        frame2 = new JFrame("Transactions");
        frame2.setSize(400, 300);
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
     
        transactionArea = new JTextArea();
        transactionArea.setEditable(false);
        
     
        for (String transaction : transactions) {
            transactionArea.append(transaction + "\n");
        }
        
        closeButton = new JButton("Close");
        HomeButton = new JButton("Home");
        mainFrame mainFrameInstance = null;
        
     
        
        closeButton.addActionListener(e -> new Close(mainFrameInstance, frame2));
        HomeButton.addActionListener(this);
        
       
        panel = new JPanel();
        panel.add(new JScrollPane(transactionArea));
        panel.add(closeButton);
        panel.add(HomeButton);
        
       
        frame2.add(panel);
        frame2.setVisible(true);
        
        
    }
           @Override
    public void actionPerformed(ActionEvent e) {
         if (e.getSource() == closeButton) {
          new Close(this);
        } else if (e.getSource() == HomeButton)  {
            frame2.dispose();
        }
    }
    
    }

class Close implements ActionListener {
    
    JFrame frame;
    mainFrame mainFrameInstance;
    JButton yesButton, noButton;
    JFrame parentFrame;
    
    public Close(mainFrame mainFrameInstance) {
        this.mainFrameInstance = mainFrameInstance;
        
   
        frame = new JFrame("Close Application");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        

        JLabel confirmLabel = new JLabel("Are you sure you want to close the application?");
        confirmLabel.setHorizontalAlignment(SwingConstants.CENTER);
        yesButton = new JButton("Yes");
        noButton = new JButton("No");
        

        yesButton.addActionListener(this);
        noButton.addActionListener(this);
        
  
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10)); 
        panel.add(confirmLabel);
        panel.add(yesButton);
        panel.add(noButton);
        
   
        frame.add(panel);
        
  
        frame.setVisible(true);
    }
    
    
     public Close(mainFrame mainFrameInstance, JFrame parentFrame) {
        this(mainFrameInstance);
        this.parentFrame = parentFrame;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
         if (e.getSource() == yesButton) {
            System.exit(0);
        } else if (e.getSource() == noButton) {
            frame.dispose();
        }
    }
}
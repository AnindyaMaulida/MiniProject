import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class JaketGUI {
    private static List<User> registeredUsers = new ArrayList<>();
    private static User currentUser;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                membuatAndShowGUI();
            }
        });
    }

    private static void membuatAndShowGUI() {
        // membuat and set up the main frame
        JFrame frame = new JFrame("Jacket Order App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new GridBagLayout());

        // membuat components
        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");

        // Add action listeners to buttons
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRegisterDialog(frame);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showLoginDialog(frame);
            }
        });

        // Add components ke frame
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        // frame.add(welcomeLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(registerButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(loginButton, gbc);

        // Show the frame
        frame.setVisible(true);
    }

    private static void showRegisterDialog(JFrame parentFrame) {
        // membuat the register dialog
        JDialog registerDialog = new JDialog(parentFrame, "Register", true);
        registerDialog.setSize(500, 400);
        registerDialog.setLayout(new GridBagLayout());

        // membuat components
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton registerButton = new JButton("Register");

        // Add action listener ke register button
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(registerDialog, "Please enter username and password.");
                } else if (isUsernameTaken(username)) {
                    JOptionPane.showMessageDialog(registerDialog, "Username is already taken. Please choose another one.");
                } else {
                    registerUser(username, password);
                    JOptionPane.showMessageDialog(registerDialog, "Registration successful!");
                    registerDialog.dispose();
                }
            }
        });

        // membuat panel and add components
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(registerButton, gbc);

        // Add panel ke dialog
        registerDialog.add(panel);

        // Show the dialog
        registerDialog.setVisible(true);
    }

    private static void showLoginDialog(JFrame parentFrame) {
        // membuat login dialog
        JDialog loginDialog = new JDialog(parentFrame, "Login", true);
        loginDialog.setSize(500, 400);
        loginDialog.setLayout(new GridBagLayout());

        // Membuat objek JLabel dan JTextField untuk masukan username dan password
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        // Add action listener ke login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(loginDialog, "Please enter username and password.");
                } else if (isValidCredentials(username, password)) {
                    currentUser = getUserByUsername(username);
                    JOptionPane.showMessageDialog(loginDialog, "Login successful!");
                    loginDialog.dispose();
                    showOrderWindow();
                } else {
                    JOptionPane.showMessageDialog(loginDialog, "Invalid username or password.");
                }
            }
        });

        // membuat panel and add components
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        // Add panel ke dialog
        loginDialog.add(panel);

        // Show the dialog
        loginDialog.setVisible(true);
    }

    private static void showOrderWindow() {
        // membuat and set up the order frame
        JFrame orderFrame = new JFrame("Pemesanan");
        orderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        orderFrame.setSize(500, 400);
        orderFrame.setLayout(new GridBagLayout());

        // membuat components
        JLabel welcomeLabel = new JLabel("Selamat Datang " + currentUser.getUsername() + "!");
        JButton priceListButton = new JButton("Price List");
        JButton orderButton = new JButton("Pesan Jaket");

        // Add action listeners to buttons
        priceListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPriceListDialog(orderFrame);
            }
        });

        orderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showOrderDialog(orderFrame);
            }
        });

        // Add components ke frame
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        orderFrame.add(welcomeLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        orderFrame.add(priceListButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        orderFrame.add(orderButton, gbc);

        // Show the frame
        orderFrame.setVisible(true);
        
    }

    private static void showPriceListDialog(JFrame parentFrame) {
        // membuat the price list dialog
        JDialog priceListDialog = new JDialog(parentFrame, "Daftar Harga Jaket", true);
        priceListDialog.setSize(500, 400);
        priceListDialog.setLayout(new GridBagLayout());

        // membuat components
        JTextArea priceListArea = new JTextArea("Daftar Harga Jaket :\n\n" +
                "1. Jaket A\n" +
                "Rp 100.000,-\n\n" +
                "2. Jaket B\n" +
                "Rp 120.000,-\n\n" +
                "3. Jaket C\n" +
                "Rp 160.000,-");
        JButton backButton = new JButton("Back");

        // Add action listener ke back button
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                priceListDialog.dispose();
            }
        });

        // membuat panel and add components
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(priceListArea, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(backButton, gbc);

        // Add panel ke dialog
        priceListDialog.add(panel);

        // Show the dialog
        priceListDialog.setVisible(true);
    }
    
    private static void showOrderDialog(JFrame parentFrame) {
        // membuat the order dialog
        JDialog orderDialog = new JDialog(parentFrame, "Pembelian Jaket", true);
        orderDialog.setSize(500, 400);
        orderDialog.setLayout(new GridBagLayout());
    
        // membuat components
        JLabel jacketLabel = new JLabel("Pilih Nomor Jaket:");
        JComboBox<String> jacketComboBox = new JComboBox<>(new String[]{"1", "2", "3"});
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField(5);
        JButton confirmButton = new JButton("Confirm");
        JButton backButton = new JButton("Back"); // Add back button
    
        // Add action listener ke confirm button
    confirmButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String jacketNumber = (String) jacketComboBox.getSelectedItem();
            String quantityText = quantityField.getText();

            // Validate quantity input
            if (quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(orderDialog, "Please enter a quantity.");
                return;
            }

            //pengecekan input quantity
            int quantity;

            //jika input quantity bukan bilangan integer
            try {
                quantity = Integer.parseInt(quantityText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(orderDialog, "Quantity tidak valid.");
                return;
            }

            //jika input quanityty kurang dari sama dengan 0
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(orderDialog, "Quantity harus lebih dari 0.");
                return;
            }

            double price = getPriceByJacketNumber(jacketNumber);
            double total = price * quantity;

            showOrderDetailDialog(orderDialog, jacketNumber, quantity, price, total);

            // mengkosongkan input 
            jacketComboBox.setSelectedIndex(0);
            quantityField.setText("");
        }
    });

    
        // Add action listener ke back button
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                orderDialog.dispose(); // Close the order dialog
            }
        });
    
        // membuat panel and add components
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(jacketLabel, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(jacketComboBox, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(quantityLabel, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(quantityField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(confirmButton, gbc);
    
        gbc.gridy = 3;
        panel.add(backButton, gbc); // Add back button
    
        // Add panel ke dialog
        orderDialog.add(panel);
    
        // Show the dialog
        orderDialog.setVisible(true);
    }
    
    private static void showOrderDetailDialog(JDialog orderDialog, String jacketNumber, int quantity, double price, double total) {
        // membuat the order detail dialog
        JDialog orderDetailDialog = new JDialog(orderDialog, "Detail Pesanan", true);
        orderDetailDialog.setSize(500, 400);
        orderDetailDialog.setLayout(new GridBagLayout());

        // membuat hasil pesanan dan total harga
        JTextArea titleLabel = new JTextArea("Berikut Hasil pesanan jaket atas nama " + currentUser.getUsername());
        JTextArea detailLabel = new JTextArea(currentUser.getUsername() + " memesan :\n " +
                jacketNumber + " . Jaket " + getJacketNameByNumber(jacketNumber) + " dengan jumlah " + quantity + "\n" +
                "Total Harga: Rp " + total);
        JButton okButton = new JButton("OK");

        // Add action listener ke OK button
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                membuatAndShowGUI(); // Tampilkan frame utama kembali
            }
        });

        JButton logoutButton = new JButton("Log Out");

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                orderDetailDialog.dispose(); // Tutup dialog orderDetailDialog
                currentUser = null; // Set currentUser menjadi null
                membuatAndShowGUI(); // Tampilkan frame utama kembali
            }
        });
        
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 0;
        gbcButton.gridy = 0;
        gbcButton.insets = new Insets(10, 10, 10, 10);
        buttonPanel.add(okButton, gbcButton);

        gbcButton.gridx = 1;
        gbcButton.gridy = 0;
        buttonPanel.add(logoutButton, gbcButton);


        // membuat panel and add components
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(titleLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(detailLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(okButton, gbc);

        // Add panel ke dialog
        orderDetailDialog.add(panel);

        // Show the dialog
        orderDetailDialog.setVisible(true);
    }

    private static boolean isUsernameTaken(String username) {
        for (User user : registeredUsers) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private static void registerUser(String username, String password) {
        User user = new User(username, password);
        registeredUsers.add(user);
    }

    private static boolean isValidCredentials(String username, String password) {
        for (User user : registeredUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private static User getUserByUsername(String username) {
        for (User user : registeredUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private static double getPriceByJacketNumber(String jacketNumber) {
        switch (jacketNumber) {
            case "1":
                return 100000;
            case "2":
                return 120000;
            case "3":
                return 160000;
            default:
                return 0;
        }
    }

    private static String getJacketNameByNumber(String jacketNumber) {
        switch (jacketNumber) {
            case "1":
                return "A";
            case "2":
                return "B";
            case "3":
                return "C";
            default:
                return "";
        }
    }

    private static class User {
        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}


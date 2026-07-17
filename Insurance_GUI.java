/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.insurance_gui;

/**
 *
 * @author user
 */

import javax.swing.*;
import java.awt.*;



/* ================= BASE USER ================= */
class User {
    protected String name, role;
    public User(String name, String role) {
        this.name = name;
        this.role = role;
    }
}

/* ================= MAIN CLASS ================= */
public class Insurance_GUI {
    public static void main(String[] args) {
      // Show splash
JFrame splash = new JFrame("Insurance Management System");
splash.setSize(600, 400);           // size of your splash
splash.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
splash.setLocationRelativeTo(null);
    
// Load background image
ImageIcon bg = new ImageIcon("C:/Users/user/Downloads/Aa.png");
Image img = bg.getImage().getScaledInstance(splash.getWidth(), splash.getHeight(), Image.SCALE_SMOOTH);
JLabel background = new JLabel(new ImageIcon(img));

// Add image to frame
splash.setContentPane(background); // sets the background as content pane
splash.setVisible(true);

// Wait for 3 seconds
try {
    Thread.sleep(3000);
} catch (InterruptedException e) {
    e.printStackTrace();
}

// Close splash and open main menu
splash.dispose();
new MainMenu();

    }
}

/* ================= MAIN MENU ================= */
class MainMenu {

    JFrame frame;

    MainMenu() {
        frame = new JFrame("Insurance Management System");
        frame.setSize(500, 400);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // ================= BACKGROUND IMAGE =================
        ImageIcon bgIcon = new ImageIcon("C:/Users/user/Downloads/A2.png");
        Image img = bgIcon.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        bgIcon = new ImageIcon(img);

        JLabel background = new JLabel(bgIcon);
        background.setLayout(new BorderLayout());
        frame.setContentPane(background);

        // ================= HEADER / TITLE =================
        JLabel title = new JLabel("INSURANCE MANAGEMENT SYSTEM", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 25));
        title.setBorder(BorderFactory.createEmptyBorder(44, 0, 0, 0));
        title.setForeground(new Color(0, 0, 139)); 

        JPanel header = new JPanel();
        header.setOpaque(false); // make header transparent
        header.add(title);

        frame.add(header, BorderLayout.NORTH);

        // ================= BUTTONS =================
        JButton adminBtn = new JButton("ADMIN");
        JButton agentBtn = new JButton("AGENT");
        JButton customerBtn = new JButton("CUSTOMER");
        JButton exitBtn = new JButton("EXIT");

        style(adminBtn);
        style(agentBtn);
        style(customerBtn);
        style(exitBtn);

        // Panel with vertical buttons, left-aligned
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(50, 120, 50, 30)); // padding

      Dimension buttonSize = new Dimension(260, 50);

adminBtn.setPreferredSize(buttonSize);
agentBtn.setPreferredSize(buttonSize);
customerBtn.setPreferredSize(buttonSize);
exitBtn.setPreferredSize(buttonSize);

adminBtn.setMaximumSize(buttonSize);
agentBtn.setMaximumSize(buttonSize);
customerBtn.setMaximumSize(buttonSize);
exitBtn.setMaximumSize(buttonSize);


        // Add buttons with spacing
        center.add(adminBtn);
        center.add(Box.createRigidArea(new Dimension(0, 20)));
        center.add(agentBtn);
        center.add(Box.createRigidArea(new Dimension(0, 20)));
        center.add(customerBtn);
        center.add(Box.createRigidArea(new Dimension(0, 20)));
        center.add(exitBtn);

        frame.add(center, BorderLayout.CENTER);

        // ================= VISIBLE =================
        frame.setResizable(false);
        frame.setVisible(true);

        // ================= ACTION LISTENERS =================
     adminBtn.addActionListener(e -> showLoginPanel("Admin"));
     agentBtn.addActionListener(e -> showLoginPanel("Agent"));
     customerBtn.addActionListener(e -> showLoginPanel("Customer"));


        exitBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private void style(JButton b) {
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));
        b.setFocusPainted(false);
        b.setBackground(new Color(0, 51, 102));
        b.setForeground(Color.WHITE);
    }
private void showLoginPanel(String role) {

    JFrame loginFrame = new JFrame(role + " Login");
    loginFrame.setSize(400, 250);
    loginFrame.setLocationRelativeTo(null);
    loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // ===== Background Image =====
    ImageIcon bgIcon = new ImageIcon("C:/Users/user/Downloads/A2.png");
    Image img = bgIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
    JLabel background = new JLabel(new ImageIcon(img));
    background.setLayout(new BorderLayout());
    loginFrame.setContentPane(background);

    // ===== Form Panel (Transparent) =====
    JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
    panel.setOpaque(false);
    panel.setBorder(BorderFactory.createEmptyBorder(40, 50, 30, 50));

    JLabel userLbl = new JLabel();
    JLabel passLbl = new JLabel();

    userLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
    passLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));

    JTextField userTxt = new JTextField();
    JPasswordField passTxt = new JPasswordField();
    JTextField contactTxt = new JTextField();
    JTextField addrTxt = new JTextField();

    JButton loginBtn = new JButton("Login");
    JButton cancelBtn = new JButton("Cancel");
    
    loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
    cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
    
    loginBtn.setBackground(new Color(0, 51, 102));
    loginBtn.setForeground(Color.WHITE);
    cancelBtn.setBackground(new Color(200, 0, 0));
    cancelBtn.setForeground(Color.WHITE);

    // ===== Fields based on role =====
    if (role.equalsIgnoreCase("Admin") || role.equalsIgnoreCase("Agent")) {

        userLbl.setText("Username:");
        passLbl.setText("Password:");

        panel.add(userLbl); panel.add(userTxt);
        panel.add(passLbl); panel.add(passTxt);

    } else { // Customer

        userLbl.setText("Name:");
        passLbl.setText("Contact:");

        panel.add(userLbl); panel.add(userTxt);
        panel.add(passLbl); panel.add(contactTxt);

        JLabel addrLbl = new JLabel("Address:");
        addrLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(addrLbl); panel.add(addrTxt);
    }
panel.add(new JLabel());
panel.add(new JLabel());
    panel.add(loginBtn);
    panel.add(cancelBtn);

    background.add(panel, BorderLayout.CENTER);

    loginFrame.setResizable(false);
    loginFrame.setVisible(true);

    // ===== Login Logic =====
    loginBtn.addActionListener(e -> {

        if (role.equalsIgnoreCase("Admin")) {
            if (userTxt.getText().equals("admin") && new String(passTxt.getPassword()).equals("123")) {
                loginFrame.dispose();
                new Admin("Admin");
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid Admin Login");
            }
        }

        else if (role.equalsIgnoreCase("Agent")) {
            if (userTxt.getText().equals("agent") && new String(passTxt.getPassword()).equals("123")) {
                loginFrame.dispose();
                new Agent("Agent");
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid Agent Login");
            }
        }

        else { // Customer
            if (!userTxt.getText().isEmpty() && !contactTxt.getText().isEmpty() && !addrTxt.getText().isEmpty()) {
                loginFrame.dispose();
                new Customer(userTxt.getText());
            } else {
                JOptionPane.showMessageDialog(loginFrame, "All fields are required");
            }
        }
    });

    cancelBtn.addActionListener(e -> loginFrame.dispose());
}


}

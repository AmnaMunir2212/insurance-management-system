/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.insurance_gui;

/**
 *
 * @author user
 */
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.table.DefaultTableModel;

public class Customer extends User {

    private static final String CUSTOMER_FILE = "customers.txt";
    private static final String POLICY_FILE = "policies.txt";

    JFrame frame;
    JTextArea output;

    String id, contact, address;

    // GUI constructor
    public Customer(String name) {
        super(name, "Customer");
        CustomerMenu();
    }

    // File-data constructor (NO GUI)
    public Customer(String[] data) {
        super(data[1], "Customer");
        this.id = data[0];
        this.contact = data[2];
        this.address = data[3];
    }

    // ---------------- GUI ----------------
   private void CustomerMenu() {
    frame = new JFrame("Customer Panel - " + name);
    frame.setSize(800, 480);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setResizable(false);

    // ===== Background =====
JPanel bg = new JPanel() {
    Image img = new ImageIcon("C:/Users/user/Downloads/A2.png").getImage();
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }
};    bg.setLayout(new BorderLayout());
    frame.setContentPane(bg);

    // ===== Title =====
    JLabel title = new JLabel("CUSTOMER PANEL", JLabel.CENTER);
    title.setFont(new Font("Segoe UI", Font.BOLD, 26));
    title.setForeground(new Color(0, 51, 102));
    title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
    JPanel header = new JPanel();
    header.setOpaque(false);
    header.add(title);
    bg.add(header, BorderLayout.NORTH);

    // ===== Output Area =====
    output = new JTextArea();
    output.setEditable(false);
    output.setFont(new Font("Segoe UI", Font.BOLD, 14));
    output.setLineWrap(true);
    output.setWrapStyleWord(true);
output.setBackground(new Color(255, 255, 255, 220));
output.setOpaque(true);

    JScrollPane sp = new JScrollPane(output);
sp.setPreferredSize(new Dimension(500, 130));

sp.setOpaque(true);
sp.getViewport().setOpaque(true);
sp.getViewport().setBackground(Color.WHITE);


    JPanel outputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    outputPanel.setOpaque(false);
    outputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
    outputPanel.add(sp);

    // ===== Buttons =====
JPanel buttonsWrapper = new JPanel(new BorderLayout());
buttonsWrapper.setOpaque(false);
buttonsWrapper.setBorder(BorderFactory.createEmptyBorder(25, 120, 40, 120));

// ===== Top 2x2 buttons =====
JPanel topButtons = new JPanel(new GridLayout(2, 2, 25, 20));
topButtons.setOpaque(false);

JButton viewBtn    = createButton("View Policies");
JButton updateBtn  = createButton("Update Info");
JButton applyBtn   = createButton("Apply Policy");
JButton paymentBtn = createButton("Check Payment");
Dimension bigBtn = new Dimension(200, 45); 

topButtons.add(viewBtn);
topButtons.add(updateBtn);
topButtons.add(applyBtn);
topButtons.add(paymentBtn);

// ===== Logout (centered) =====
JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
logoutPanel.setOpaque(false);

JButton logoutBtn = createButton("Logout");
logoutBtn.setPreferredSize(new Dimension(200, 35)); 
logoutPanel.add(logoutBtn);

// ===== Add to wrapper =====
buttonsWrapper.add(topButtons, BorderLayout.CENTER);
buttonsWrapper.add(logoutPanel, BorderLayout.SOUTH);

    // ===== Main Center Wrapper =====
    JPanel mainCenter = new JPanel(new BorderLayout());
    mainCenter.setOpaque(false);
    mainCenter.add(outputPanel, BorderLayout.NORTH);
    mainCenter.add(buttonsWrapper, BorderLayout.CENTER);

    bg.add(mainCenter, BorderLayout.CENTER);

    
    // ===== Button Actions =====
    viewBtn.addActionListener(e -> viewPolicies());
    updateBtn.addActionListener(e -> updateInfo());
    applyBtn.addActionListener(e -> applyPolicy());
    paymentBtn.addActionListener(e -> checkPayment());
    logoutBtn.addActionListener(e -> frame.dispose());
    new MainMenu();
    
    frame.setVisible(true);
}

// ===== Helper for Buttons =====
private JButton createButton(String text) {
    JButton b = new JButton(text);
    b.setFont(new Font("Segoe UI", Font.BOLD, 14));
    b.setBackground(new Color(0, 51, 102));
    b.setForeground(Color.WHITE);
    b.setFocusPainted(false);
    b.setAlignmentX(Component.CENTER_ALIGNMENT);
    b.setBorder(BorderFactory.createEmptyBorder(14, 35, 14, 35));
    return b;
}


    // ---------------- 1. View Policies ----------------
    private void viewPolicies() {
        output.setText("");
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(POLICY_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p[1].equalsIgnoreCase(name)) {
                    found = true;
                    output.append(
                            "Policy ID: " + p[0] + "\n" +
                                    "Type: " + p[2] + "\n" +
                                    "Coverage: " + p[3] + "\n" +
                                    "Premium: " + p[4] + "\n" +
                                    "Status: " + p[5] + "\n----------------------\n");
                }
            }
        } catch (Exception e) {
            output.setText("Error reading policies file.");
        }

        if (!found) output.setText("No policies found.");
    }

    // ---------------- 2. Update Info ----------------
    private void updateInfo() {

    JFrame f = new JFrame("Update Personal Info");
    f.setSize(420, 260);
    f.setLocationRelativeTo(null);
    f.setResizable(false);

    JPanel bgPanel = new JPanel() {
        Image bg = new ImageIcon("C:/Users/user/Downloads/A2.png").getImage();
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    };
    bgPanel.setLayout(new BoxLayout(bgPanel, BoxLayout.Y_AXIS));
    bgPanel.setBorder(BorderFactory.createEmptyBorder(30,30,20,30));
    f.setContentPane(bgPanel);

    JLabel lbl1 = new JLabel("New Contact:");
    lbl1.setFont(new Font("Segoe UI", Font.BOLD, 14));
    JTextField txtContact = new JTextField(contact);
    txtContact.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

    JLabel lbl2 = new JLabel("New Address:");
    lbl2.setFont(new Font("Segoe UI", Font.BOLD, 14));
    JTextField txtAddress = new JTextField(address);
    txtAddress.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

    JButton btn = createButton("Update");
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);

    bgPanel.add(lbl1);
    bgPanel.add(txtContact);
    bgPanel.add(Box.createRigidArea(new Dimension(0,10)));
    bgPanel.add(lbl2);
    bgPanel.add(txtAddress);
    bgPanel.add(Box.createRigidArea(new Dimension(0,20)));
    bgPanel.add(btn);

    btn.addActionListener(e -> {
        String newContact = txtContact.getText();
        String newAddress = txtAddress.getText();

        try {
            BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_FILE));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d[1].equalsIgnoreCase(name)) {
                    sb.append(d[0]).append(",").append(name).append(",")
                      .append(newContact).append(",").append(newAddress).append("\n");
                    contact = newContact;
                    address = newAddress;
                } else sb.append(line).append("\n");
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMER_FILE));
            bw.write(sb.toString());
            bw.close();

            output.setText("Information Updated Successfully!");
            f.dispose();

        } catch (Exception ex) {
            output.setText("Error updating info.");
        }
    });

    f.setVisible(true);
}
    
    // ---------------- 3. Apply Policy ----------------
private void applyPolicy() {

    JFrame f = new JFrame("Apply Policy");
    f.setSize(420, 280);
    f.setLocationRelativeTo(null);
    f.setResizable(false);

    JPanel bgPanel = new JPanel() {
        Image bg = new ImageIcon("C:/Users/user/Downloads/A2.png").getImage();
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    };
    bgPanel.setLayout(new BoxLayout(bgPanel, BoxLayout.Y_AXIS));
    bgPanel.setBorder(BorderFactory.createEmptyBorder(30,30,20,30));
    f.setContentPane(bgPanel);

    JLabel l1 = new JLabel("Policy Type");
    l1.setFont(new Font("Segoe UI", Font.BOLD, 14));
    JComboBox<String> st = new JComboBox<>(new String[]{"Health", "Life", "Car"});
   

    JLabel l2 = new JLabel("Coverage");
    l2.setFont(new Font("Segoe UI", Font.BOLD, 14));
    JTextField coverage = new JTextField();
    coverage.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

    JLabel l3 = new JLabel("Premium");
    l3.setFont(new Font("Segoe UI", Font.BOLD, 14));
    JTextField premium = new JTextField();
    premium.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

    JButton btn = createButton("Submit Policy");
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);

    bgPanel.add(l1); bgPanel.add(st);
    bgPanel.add(Box.createRigidArea(new Dimension(0,10)));
    bgPanel.add(l2); bgPanel.add(coverage);
    bgPanel.add(Box.createRigidArea(new Dimension(0,10)));
    bgPanel.add(l3); bgPanel.add(premium);
    bgPanel.add(Box.createRigidArea(new Dimension(0,20)));
    bgPanel.add(btn);

    btn.addActionListener(e -> {
        String pid = generatePolicyID();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(POLICY_FILE, true))) {
            bw.write(pid + "," + name + "," + st.getSelectedItem() + "," +
                     coverage.getText() + "," + premium.getText() + ",Pending\n");
            output.setText("Policy Applied\nPolicy ID: " + pid);
            f.dispose();
        } catch (Exception ex) {
            output.setText("Error applying policy");
        }
    });

    f.setVisible(true);
}

    private String generatePolicyID() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(POLICY_FILE))) {
            while (br.readLine() != null) count++;
        } catch (Exception e) {}
        return "P" + String.format("%03d", count + 1);
    }

    // ---------------- 4. Payment Status ----------------
private void checkPayment() {

    JFrame f = new JFrame("Payment Status");
    f.setSize(520, 300);
    f.setLocationRelativeTo(null);
    f.setResizable(false);

    JPanel bg = new JPanel() {
        Image img = new ImageIcon("C:/Users/user/Downloads/A2.png").getImage();
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    };
    bg.setLayout(new BorderLayout(10,10));
    bg.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    f.setContentPane(bg);

    // ===== Title =====
    JLabel title = new JLabel("Payment Status", JLabel.CENTER);
    title.setFont(new Font("Segoe UI", Font.BOLD, 20));
    title.setForeground(new Color(0,51,102));
    bg.add(title, BorderLayout.NORTH);

    // ===== Table =====
    String[] cols = {"Policy ID", "Premium", "Status"};
    DefaultTableModel model = new DefaultTableModel(cols, 0);

    try (BufferedReader br = new BufferedReader(new FileReader(POLICY_FILE))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] p = line.split(",");
            if (p[1].equalsIgnoreCase(name)) {
                model.addRow(new Object[]{p[0], p[4], p[5]});
            }
        }
    } catch (Exception e) {}

    JTable table = new JTable(model);
    table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    table.setRowHeight(24);
    table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

    JScrollPane sp = new JScrollPane(table);
    bg.add(sp, BorderLayout.CENTER);

    // ===== Close Button =====
    JButton close = createButton("Close");
    close.setPreferredSize(new Dimension(120,35));
    close.addActionListener(e -> f.dispose());

    JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
    bottom.setOpaque(false);
    bottom.add(close);

    bg.add(bottom, BorderLayout.SOUTH);

    f.setVisible(true);
}
}

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

/* ================= LINKED LIST ================= */
class Node {
    String data;
    Node next;
    Node(String d) { data = d; }
}

class MyLinkedList {
    Node head;

    void add(String d) {
        Node n = new Node(d);
        if (head == null) head = n;
        else {
            Node t = head;
            while (t.next != null) t = t.next;
            t.next = n;
        }
    }

    void removeIf(String key) {
        while (head != null && head.data.startsWith(key + ",")) {
            head = head.next;
        }
        Node t = head;
        while (t != null && t.next != null) {
            if (t.next.data.startsWith(key + ",")) {
                t.next = t.next.next;
            } else t = t.next;
        }
    }
}

/* ================= STACK ================= */
class StackNode {
    String data;
    StackNode next;
    StackNode(String d)
    {
        data = d;
    }
}

class MyStack {
    StackNode top;

    void push(String d) {
        StackNode n = new StackNode(d);
        n.next = top;
        top = n;
    }

    String pop() {
        if (top == null) return null;
        String v = top.data;
        top = top.next;
        return v;
    }
}

/* ================= ADMIN ================= */
public class Admin extends User {

    static final String CUSTOMER_FILE = "customers.txt";
    static final String POLICY_FILE   = "policies.txt";

    JTextArea output = new JTextArea();
    MyStack undoStack = new MyStack();

    public Admin(String name) {
        super(name, "Admin");
        showAdminMenu();
    }

    /* ================= GUI ================= */
   private void showAdminMenu() {
    JFrame f = new JFrame("Admin Panel");
    f.setSize(750, 500);
    f.setLocationRelativeTo(null);
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // ===== BACKGROUND IMAGE =====
    ImageIcon bgIcon = new ImageIcon("C:/Users/user/Downloads/A2.png");
    Image img = bgIcon.getImage().getScaledInstance(750, 500, Image.SCALE_SMOOTH);
    JLabel background = new JLabel(new ImageIcon(img));
    background.setLayout(new BorderLayout());
    f.setContentPane(background);

    // ===== HEADER =====
    JLabel title = new JLabel("ADMIN PANEL", JLabel.CENTER);
    title.setFont(new Font("Segoe UI", Font.BOLD, 26));
    title.setForeground(new Color(0, 51, 102)); // dark blue
    title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

    JPanel header = new JPanel();
    header.setOpaque(false);
    header.add(title);

    background.add(header, BorderLayout.NORTH);

   // ===== OUTPUT AREA =====
output.setEditable(false);
output.setFont(new Font("Segoe UI", Font.BOLD, 15));
output.setBackground(Color.WHITE); 
output.setOpaque(true);
output.setBorder(BorderFactory.createLineBorder(new Color(0, 51, 102), 2));

JScrollPane scroll = new JScrollPane(output);
scroll.setPreferredSize(new Dimension(150, 120));

scroll.setOpaque(true);
scroll.getViewport().setOpaque(true);
scroll.getViewport().setBackground(Color.WHITE);
scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

   
background.add(scroll, BorderLayout.CENTER);

    // ===== BUTTON PANEL =====
    JPanel panel = new JPanel(new GridLayout(4, 2, 15, 15));
    panel.setOpaque(false);
    panel.setBorder(BorderFactory.createEmptyBorder(15, 40, 20, 40));

    JButton add     = createButton("Add Customer");
    JButton del     = createButton("Delete Customer");
    JButton undo    = createButton("Undo Delete");
    JButton view    = createButton("View Customers");
    JButton pol     = createButton("View Policies");
    JButton searchC = createButton("Search Customer");
    JButton searchP = createButton("Search Policy");
    JButton logout  = createButton("Logout");

    panel.add(add);
    panel.add(del);
    panel.add(undo);
    panel.add(view);
    panel.add(pol);
    panel.add(searchC);
    panel.add(searchP);
    panel.add(logout);

    background.add(panel, BorderLayout.SOUTH);

    // ===== ACTIONS =====
    add.addActionListener(e -> addCustomer());
    del.addActionListener(e -> deleteCustomer());
    undo.addActionListener(e -> undoDelete());
    view.addActionListener(e -> viewCustomers());
    pol.addActionListener(e -> viewPolicies());
    searchC.addActionListener(e -> searchCustomer());
    searchP.addActionListener(e -> searchPolicy());
    logout.addActionListener(e -> f.dispose());
    new MainMenu();

    f.setResizable(false);
    f.setVisible(true);
}
private JButton createButton(String text) {
    JButton b = new JButton(text);
    b.setFont(new Font("Segoe UI", Font.BOLD, 15));
    b.setFocusPainted(false);
    b.setBackground(new Color(0, 51, 102)); // same as Main Menu
    b.setForeground(Color.WHITE);
    b.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
    return b;
}

/* ================= BACKEND ================= */

    private String generateCustomerID() {
        int c = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
            while (br.readLine() != null) c++;
        } catch (Exception e) {}
        return String.format("%03d", c + 1);
    }

  private void addCustomer() {

    JFrame form = new JFrame("Add Customer");
    form.setSize(330, 260);
    form.setLocationRelativeTo(null);
    form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // ===== BACKGROUND IMAGE =====
    ImageIcon bgIcon = new ImageIcon("C:/Users/user/Downloads/A2.png");
    Image img = bgIcon.getImage().getScaledInstance(320, 220, Image.SCALE_SMOOTH);
    JLabel background = new JLabel(new ImageIcon(img));
    background.setLayout(new BorderLayout());
    form.setContentPane(background);

    // ===== HEADER =====
    JLabel title = new JLabel("ADD CUSTOMER", JLabel.CENTER);
    title.setFont(new Font("Segoe UI", Font.BOLD, 20));
    title.setForeground(new Color(0, 51, 102));
    title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));

    JPanel header = new JPanel();
    header.setOpaque(false);
    header.add(title);
    background.add(header, BorderLayout.NORTH);

  // ===== FORM PANEL =====
JPanel panel = new JPanel(new GridLayout(3, 2, 15, 15));
panel.setOpaque(false);
panel.setBorder(BorderFactory.createEmptyBorder(15, 25, 10, 25));

JLabel nameLbl = new JLabel("Name:");
JLabel contactLbl = new JLabel("Contact:");
JLabel addrLbl = new JLabel("Address:");

Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
nameLbl.setFont(labelFont);
contactLbl.setFont(labelFont);
addrLbl.setFont(labelFont);

nameLbl.setForeground(Color.BLACK);
contactLbl.setForeground(Color.BLACK);
addrLbl.setForeground(Color.BLACK);

JTextField nameTxt = new JTextField();
JTextField contactTxt = new JTextField();
JTextField addrTxt = new JTextField();

Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);
nameTxt.setFont(fieldFont);
contactTxt.setFont(fieldFont);
addrTxt.setFont(fieldFont);

// Add to panel
panel.add(nameLbl);
panel.add(nameTxt);
panel.add(contactLbl);
panel.add(contactTxt);
panel.add(addrLbl);
panel.add(addrTxt);

background.add(panel, BorderLayout.CENTER);

    // ===== BUTTON =====
    JButton addBtn = new JButton("Add Customer");
    addBtn.setBackground(new Color(0, 51, 102));
    addBtn.setForeground(Color.WHITE);
    addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    addBtn.setFocusPainted(false);

    JPanel btnPanel = new JPanel();
    btnPanel.setOpaque(false);
    btnPanel.add(addBtn);
    background.add(btnPanel, BorderLayout.SOUTH);

    // ===== ACTION =====
    addBtn.addActionListener(e -> {
        String name = nameTxt.getText().trim();
        String contact = contactTxt.getText().trim();
        String addr = addrTxt.getText().trim();

        if (name.isEmpty() || contact.isEmpty() || addr.isEmpty()) {
            JOptionPane.showMessageDialog(
                    form,
                    "All fields are required",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String id = generateCustomerID();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMER_FILE, true))) {
            bw.write(id + "," + name + "," + contact + "," + addr);
            bw.newLine();

            JOptionPane.showMessageDialog(
        form,
        "Customer Added Successfully!\nCustomer ID: " + id,
        "Success",
        JOptionPane.INFORMATION_MESSAGE
);

            form.dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    form,
                    "Error adding customer",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    });

    form.setResizable(false);
    form.setVisible(true);
}

 

    private void deleteCustomer() {

    JFrame f = new JFrame("Delete Customer");
    f.setSize(360, 220);
    f.setLocationRelativeTo(null);
    f.setResizable(false);

    JPanel bg = new JPanel() {
        Image img = new ImageIcon("C:/Users/user/Downloads/A2.png").getImage();
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    };
    bg.setLayout(new BoxLayout(bg, BoxLayout.Y_AXIS));
    bg.setBorder(BorderFactory.createEmptyBorder(40,40,20,40));
    f.setContentPane(bg);

    JPanel row1 = new JPanel();
    row1.setOpaque(false);
    JLabel lbl = new JLabel("Customer ID:");
    lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
    JTextField id = new JTextField(15);
    row1.add(lbl);
    row1.add(id);

    JButton btn = createButton("Delete");

    bg.add(row1);
    bg.add(Box.createRigidArea(new Dimension(0,20)));
    bg.add(btn);

    btn.addActionListener(e -> {
        String cid = id.getText().trim();
        if(cid.isEmpty()){
            output.setText("Enter Customer ID");
            return;
        }

        MyLinkedList list = new MyLinkedList();

        try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(cid + ",")) undoStack.push(line);
                else list.add(line);
            }
        } catch (Exception ex) {}

        list.removeIf(cid);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMER_FILE))) {
            Node t = list.head;
            while (t != null) {
                bw.write(t.data);
                bw.newLine();
                t = t.next;
            }
            output.setText("Customer Deleted Successfully");
        } catch (Exception ex) {
            output.setText("Delete Failed");
        }

        f.dispose();
    });

    f.setVisible(true);
}

private void undoDelete() {

    JFrame f = new JFrame("Undo Delete");
    f.setSize(300,180);
    f.setLocationRelativeTo(null);
    f.setResizable(false);

    JPanel bg = new JPanel() {
        Image img = new ImageIcon("C:/Users/user/Downloads/A2.png").getImage();
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img,0,0,getWidth(),getHeight(),this);
        }
    };
    bg.setLayout(new FlowLayout(FlowLayout.CENTER,20,60));
    f.setContentPane(bg);

    JButton btn = createButton("Undo Last Delete");
    bg.add(btn);

    btn.addActionListener(e -> {
        String r = undoStack.pop();
        if (r == null) {
            output.setText("Nothing to Undo");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMER_FILE, true))) {
            bw.write(r);
            bw.newLine();
            output.setText("Undo Successful");
        } catch (Exception ex) {
            output.setText("Undo Failed");
        }

        f.dispose();
    });

    f.setVisible(true);
}
  private JButton btn(String t){
    JButton b = new JButton(t);
    b.setFont(new Font("Segoe UI", Font.BOLD, 14));
    b.setBackground(new Color(0,51,102));
    b.setForeground(Color.WHITE);
    b.setFocusPainted(false);

    b.setBorder(BorderFactory.createEmptyBorder(20, 35, 20, 35));

    // important for BoxLayout
    b.setAlignmentX(Component.CENTER_ALIGNMENT);

    return b;
}

  private void viewPolicies() {

    StringBuilder sb = new StringBuilder();
    sb.append("========== ALL POLICIES ==========\n\n");

    boolean found = false;

    try (BufferedReader br = new BufferedReader(new FileReader(POLICY_FILE))) {
        String line;

        while ((line = br.readLine()) != null) {

            String[] p2 = line.split(",", -1);
            if (p2.length >= 6) {
                found = true;

                sb.append("Policy ID: ").append(p2[0]).append("\n");
                sb.append("Customer: ").append(p2[1]).append("\n");
                sb.append("Type: ").append(p2[2]).append("\n");
                sb.append("Coverage: ").append(p2[3]).append("\n");
                sb.append("Premium: ").append(p2[4]).append("\n");
                sb.append("Status: ").append(p2[5]).append("\n");
                sb.append("--------------------------------\n");
            }
        }

    } catch (Exception ex) {
        sb.append("Error reading policy file");
    }

    if (!found) {
        sb.append("No policies available.");
    }

    output.setText(sb.toString());
}

  
private void viewCustomers() {

    StringBuilder sb = new StringBuilder();
    sb.append("========== ALL CUSTOMERS ==========\n\n");

    boolean found = false;

    try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
        String line;

        while ((line = br.readLine()) != null) {

            String[] c = line.split(",", -1);
            if (c.length >= 4) {
                found = true;

                sb.append("Customer ID: ").append(c[0]).append("\n");
                sb.append("Name: ").append(c[1]).append("\n");
                sb.append("Contact: ").append(c[2]).append("\n");
                sb.append("Address: ").append(c[3]).append("\n");
                sb.append("--------------------------------\n");
            }
        }

    } catch (Exception ex) {
        sb.append("Error reading customer file");
    }

    if (!found) {
        sb.append("No customers available.");
    }

    output.setText(sb.toString());
}

   private void searchCustomer() {

    JFrame f = new JFrame("Search Customer");
    f.setSize(360,220);
    f.setLocationRelativeTo(null);
    f.setResizable(false);

    JPanel bg = new JPanel() {
        Image img = new ImageIcon("C:/Users/user/Downloads/A2.png").getImage();
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(img,0,0,getWidth(),getHeight(),this);
        }
    };
    bg.setLayout(new BoxLayout(bg,BoxLayout.Y_AXIS));
    bg.setBorder(BorderFactory.createEmptyBorder(40,40,20,40));
    f.setContentPane(bg);

    JPanel row1 = new JPanel();
    row1.setOpaque(false);
    JLabel lbl = new JLabel("Customer ID:");
    lbl.setFont(new Font("Segoe UI",Font.BOLD,14));
    JTextField id = new JTextField(15);
    row1.add(lbl);
    row1.add(id);

    JButton btn = createButton("Search");

    bg.add(row1);
    bg.add(Box.createRigidArea(new Dimension(0,20)));
    bg.add(btn);

    btn.addActionListener(e -> {
        String cid = id.getText().trim();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
            String l;
            while ((l = br.readLine()) != null) {
                if (l.startsWith(cid + ",")) {
                    output.setText(l);
                    found = true;
                    break;
                }
            }
        } catch (Exception ex) {}

        if (!found) output.setText("Customer Not Found");

        f.dispose();
    });

    f.setVisible(true);
}

private void searchPolicy() {

    JFrame f = new JFrame("Search Policy");
    f.setSize(380,200);
    f.setLocationRelativeTo(null);
    f.setResizable(false);

    JPanel bg = new JPanel() {
        Image img = new ImageIcon("C:/Users/user/Downloads/A2.png").getImage();
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(img,0,0,getWidth(),getHeight(),this);
        }
    };
    bg.setLayout(new BoxLayout(bg,BoxLayout.Y_AXIS));
    bg.setBorder(BorderFactory.createEmptyBorder(30,30,20,30));
    f.setContentPane(bg);

    JPanel row1 = new JPanel();
    row1.setOpaque(false);
    JLabel lbl = new JLabel("Policy ID:");
    lbl.setFont(new Font("Segoe UI",Font.BOLD,14));
    JTextField id = new JTextField(15);
    row1.add(lbl);
    row1.add(id);

    JButton btn = createButton("Search");

    bg.add(row1);
    bg.add(Box.createRigidArea(new Dimension(0,20)));
    bg.add(btn);

    btn.addActionListener(e -> {
        String pid = id.getText().trim();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(POLICY_FILE))) {
            String l;
            while ((l = br.readLine()) != null) {
                String[] p = l.split(",");
                if (p[0].equalsIgnoreCase(pid)) {
                    output.setText(
                        "Policy ID: " + p[0] +
                        "\nCustomer: " + p[1] +
                        "\nType: " + p[2] +
                        "\nAmount: " + p[3]
                    );
                    found = true;
                    break;
                }
            }
        } catch (Exception ex) {}

        if (!found) output.setText("Policy Not Found");

        f.dispose();
    });
    f.setVisible(true);
}

}





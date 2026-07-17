/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.insurance_gui;

/**
 *
 * @author user
 */
import java.awt.BorderLayout;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

/* ================= QUEUE ================= */
class MyQueue {
    static class Node {
        String data; Node next;
        Node(String d){ data=d; }
    }
    private Node front, rear;
    public void enqueue(String d){
        Node n=new Node(d);
        if(rear==null){ front=rear=n; return; }
        rear.next=n; rear=n;
    }
    public String dequeue(){
        if(front==null) return null;
        String d=front.data;
        front=front.next;
        if(front==null) rear=null;
        return d;
    }
    public boolean isEmpty(){ return front==null; }
}

/* ================= BST ================= */
class PolicyBST {
    class Node { String id;
    Node l,r; 
    Node(String i)
    {
        id=i;
    }
    }
    Node root;
    void insert(String id){ root=insertRec(root,id); }
    Node insertRec(Node r,String id){
        if(r==null) return new Node(id);
        if(id.compareTo(r.id)<0) r.l=insertRec(r.l,id);
        else r.r=insertRec(r.r,id);
        return r;
    }
}

/* ================= AGENT ================= */
public class Agent extends User {

    static final String POLICY_FILE ="policies.txt";
    static final String CUSTOMER_FILE = "customers.txt";

    JTextArea output = new JTextArea();
    PolicyBST tree = new PolicyBST();

    public Agent(String name){
        super(name,"Agent");
        showAgentMenu();
    }

    /* ================= GUI ================= */
    public void showAgentMenu() {

    JFrame f = new JFrame("Agent Panel");
    f.setSize(800,580);
    f.setLocationRelativeTo(null);
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    JLabel bg = setBackground(f);

    JLabel title = new JLabel("AGENT PANEL", JLabel.CENTER);
    title.setFont(new Font("Segoe UI", Font.BOLD, 26));
    title.setForeground(new Color(0,51,102));
    title.setBorder(BorderFactory.createEmptyBorder(20,0,10,0));

    JPanel header = new JPanel();
    header.setOpaque(false);
    header.add(title);

    bg.add(header, BorderLayout.NORTH);

    output.setEditable(false);
    output.setFont(new Font("Segoe UI", Font.BOLD, 14));
    output.setBackground(new Color(255,255,255,200));
    output.setBackground(Color.WHITE); 
    output.setOpaque(true);

    JScrollPane sp = new JScrollPane(output);
    sp.setPreferredSize(new Dimension(490, 190));

sp.setOpaque(true);
sp.getViewport().setOpaque(true);
sp.getViewport().setBackground(Color.WHITE);

// ===== CENTER WRAPPER =====
JPanel mainCenter = new JPanel(new BorderLayout());
mainCenter.setOpaque(false);

// Output panel (centered)
JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
center.setOpaque(false);
center.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
center.add(sp);

// Buttons panel (below output)
JPanel buttons = new JPanel(new GridLayout(5,1,15,15));
buttons.setOpaque(false);
buttons.setBorder(BorderFactory.createEmptyBorder(10,100,5,100)); // controls horizontal centering

JButton view = btn("View Customer Policies");
JButton update = btn("Update Policy Status");
JButton searchC = btn("Search Customer");
JButton searchP = btn("Search Policy");
JButton approve = btn("Approve / Reject");
JButton logout  = btn("Logout");

buttons.add(view);
buttons.add(update);
buttons.add(searchC);
buttons.add(searchP);
buttons.add(approve);
buttons.add(logout);

// Attach inside center wrapper
mainCenter.add(center, BorderLayout.NORTH);
mainCenter.add(buttons, BorderLayout.CENTER);

bg.add(mainCenter, BorderLayout.CENTER);

    view.addActionListener(e->viewCustomerPolicies());
    update.addActionListener(e->updatePolicyStatus());
   searchC.addActionListener(e->searchCustomer());
    searchP.addActionListener(e->searchPolicy());
    approve.addActionListener(e->approveRejectPolicy());
    logout.addActionListener(e -> f.dispose());
    new MainMenu();
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

    /* ================= 1. VIEW POLICIES ================= */
 private void viewCustomerPolicies() {

    JFrame f = new JFrame("View All Policies");
    f.setSize(420,260);
    f.setLocationRelativeTo(null);

    JLabel bg = new JLabel(new ImageIcon(
            new ImageIcon("C:/Users/user/Downloads/A2.png")
            .getImage().getScaledInstance(420,260,Image.SCALE_SMOOTH)
    ));
    bg.setLayout(new BorderLayout());
    f.setContentPane(bg);

    JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
    p.setOpaque(false);
    p.setBorder(BorderFactory.createEmptyBorder(80, 50, 40, 50));

    JButton btn = btn("View All Policies");
    btn.setBorder(BorderFactory.createEmptyBorder(14, 35, 14, 35));

    p.add(btn);
    bg.add(p, BorderLayout.CENTER);

    btn.addActionListener(e -> {

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

        if (!found)
            sb.append("No policies available.");

        output.setText(sb.toString());
        f.dispose();
    });

    f.setVisible(true);
}

    /* ================= 2. UPDATE STATUS ================= */
private void updatePolicyStatus() {

    // Frame setup
    JFrame f = new JFrame("Update Policy Status");
    f.setSize(420, 280);
    f.setLocationRelativeTo(null);
    f.setResizable(false);
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // ===== Custom JPanel with Background Image =====
    JPanel bgPanel = new JPanel() {
        Image bg = new ImageIcon("C:/Users/user/Downloads/A2.png").getImage();

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    };
    bgPanel.setLayout(new GridBagLayout());
    f.setContentPane(bgPanel);

    GridBagConstraints g = new GridBagConstraints();
    g.insets = new Insets(10, 10, 10, 10);
    g.fill = GridBagConstraints.HORIZONTAL;

    // Components
    JLabel lblPolicy = new JLabel("Policy ID:");
    lblPolicy.setForeground(Color.BLACK);
    lblPolicy.setFont(new Font("Segoe UI", Font.BOLD, 14));
    
    JTextField pid = new JTextField(15);

    JLabel lblStatus = new JLabel("New Status:");
    lblStatus.setForeground(Color.BLACK);
    lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 14));
    
    lblStatus.setPreferredSize(new Dimension(100, 30)); 
    JComboBox<String> st = new JComboBox<>(new String[]{"Active", "Expired", "Rejected"});

    JButton btnUpdate = btn("Update Status"); 
     btnUpdate.setPreferredSize(new Dimension(77, 25));

    // ===== Add components to panel =====
    g.gridx = 0; g.gridy = 0;
    bgPanel.add(lblPolicy, g);
    g.gridx = 1;
    bgPanel.add(pid, g);

    g.gridx = 0; g.gridy = 1;
    bgPanel.add(lblStatus, g);
    g.gridx = 1;
    bgPanel.add(st, g);

    g.gridx = 0; g.gridy = 2;
    g.gridwidth = 2;
    g.anchor = GridBagConstraints.CENTER;
    bgPanel.add(btnUpdate, g);

    // ===== Button Action =====
    btnUpdate.addActionListener(e -> {
        String policyId = pid.getText().trim();
        if (policyId.isEmpty()) {
            JOptionPane.showMessageDialog(f, "Enter Policy ID");
            return;
        }

        MyQueue q = new MyQueue(), 
                up = new MyQueue();
        boolean found = false;
        String name = "";

        try (BufferedReader br = new BufferedReader(new FileReader(POLICY_FILE))) {
            br.lines().forEach(q::enqueue);
        } catch (Exception ignored) {}

        while (!q.isEmpty()) {
            String line = q.dequeue();
            String[] p2 = line.split(",", -1);
            if (p2[0].equals(policyId)) {
                p2[5] = st.getSelectedItem().toString();
                name = p2[1];
                line = String.join(",", p2);
                found = true;
            }
            up.enqueue(line);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(POLICY_FILE))) {
            while (!up.isEmpty()) bw.write(up.dequeue() + "\n");
        } catch (Exception ignored) {}

        JOptionPane.showMessageDialog(f, found ? "Policy Updated" : "Policy not found");

        if (found) {
            output.setText("Policy ID: " + policyId +
                           "\nCustomer Name: " + name +
                           "\nUpdated Status: " + st.getSelectedItem());
            f.dispose();
        }
    });

    f.setVisible(true);
}

    /* ================= 3. SEARCH CUSTOMER ================= */
private void searchCustomer() {

    JFrame f = new JFrame("Search Customer");
    f.setSize(360, 220);
    f.setLocationRelativeTo(null);
    f.setResizable(false);

    // ===== Background Panel =====
    JPanel bgPanel = new JPanel() {
        Image bg = new ImageIcon("C:/Users/user/Downloads/A2.png").getImage();
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    };
    bgPanel.setLayout(new BoxLayout(bgPanel, BoxLayout.Y_AXIS));
    bgPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 20, 40));
    f.setContentPane(bgPanel);

    // ===== Customer ID Row =====
    JPanel row1 = new JPanel();
    row1.setOpaque(false);
    row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
    JLabel lbl = new JLabel("Customer ID:");
    lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
    JTextField id = new JTextField();
    id.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    id.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
    row1.add(lbl);
    row1.add(Box.createRigidArea(new Dimension(10, 0)));
    row1.add(id);

    // ===== Button Row =====
    JPanel row2 = new JPanel();
    row2.setOpaque(false);
    row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
    JButton btn = btn("Search");
    btn.setPreferredSize(new Dimension(100, 30));
    row2.add(btn); // aligned left

    // ===== Add rows to panel =====
    bgPanel.add(row1);
    bgPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    bgPanel.add(row2);

    // ===== Button Action =====
    btn.addActionListener(e -> {
        String cid = id.getText().trim();
        if (cid.isEmpty()) {
            JOptionPane.showMessageDialog(f, "Enter Customer ID");
            return;
        }

        ArrayList<Customer> list = SearchUtility.loadCustomers();
        SearchUtility.mergeSortCustomers(list, 0, list.size() - 1);
        Customer c = SearchUtility.binarySearchCustomer(list, cid);

        output.setText(c != null
                ? "ID: " + c.id +
                  "\nName: " + c.name +
                  "\nContact: " + c.contact +
                  "\nAddress: " + c.address
                : "Customer not found");

        f.dispose();
    });

    f.setVisible(true);
}

/* ================= SEARCH POLICY ================= */
private void searchPolicy() {

    JFrame f = new JFrame("Search Policy"); 
    f.setSize(420, 180);
    f.setLocationRelativeTo(null);
    f.setResizable(false);

    // Background Panel
    JPanel bgPanel = new JPanel() {
        Image bg = new ImageIcon("C:/Users/user/Downloads/A2.png").getImage();
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    };
    bgPanel.setLayout(new BoxLayout(bgPanel, BoxLayout.Y_AXIS));
    bgPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));
    f.setContentPane(bgPanel);

    // Policy ID row
    JPanel row1 = new JPanel();
    row1.setOpaque(false);
    row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
    JLabel lbl = new JLabel("Policy ID:");
    lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
    JTextField id = new JTextField();
    id.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    id.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
    row1.add(lbl);
    row1.add(Box.createRigidArea(new Dimension(10,0)));
    row1.add(id);

    // Button row
    JPanel row2 = new JPanel();
    row2.setOpaque(false);
    row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
    JButton btn = btn("Search");
    btn.setMaximumSize(new Dimension(120, 30)); // keeps it small
    row2.add(Box.createHorizontalGlue()); // aligns button to right
    row2.add(btn);

    // Add rows
    bgPanel.add(row1);
    bgPanel.add(Box.createRigidArea(new Dimension(0,20)));
    bgPanel.add(row2);

    // Action
    btn.addActionListener(e -> {
        String pid = id.getText().trim();
        if (pid.isEmpty()) {
            JOptionPane.showMessageDialog(f, "Enter Policy ID");
            return;
        }

        ArrayList<Policy> list = SearchUtility.loadPolicies();
        if (list.isEmpty()) {
            output.setText("No policies available.");
            f.dispose();
            return;
        }

        // Ensure sorted before binary search
        SearchUtility.mergeSortPolicies(list, 0, list.size()-1);
        Policy p = SearchUtility.binarySearchPolicy(list, pid);

        output.setText(p != null
                ? "Customer: " + p.customerName +
                  "\nType: " + p.type +
                  "\nCoverage: " + p.coverage +
                  "\nPremium: " + p.premium +
                  "\nStatus: " + p.status
                : "Policy not found");

        f.dispose();
    });

    f.setVisible(true);
}


    /* ================= 5. APPROVE / REJECT ================= */
private void approveRejectPolicy() {

    JFrame f = new JFrame("Approve / Reject Policy");
    f.setSize(430, 220);
    f.setLocationRelativeTo(null);
    f.setResizable(false);

    // ===== Background Panel =====
    JPanel bgPanel = new JPanel() {
        Image bg = new ImageIcon("C:/Users/user/Downloads/A2.png").getImage();
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    };
    bgPanel.setLayout(new BoxLayout(bgPanel, BoxLayout.Y_AXIS));
    bgPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));
    f.setContentPane(bgPanel);

    // ===== Policy ID Row =====
    JPanel row1 = new JPanel();
    row1.setOpaque(false);
    row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
    JLabel lblId = new JLabel("Policy ID:");
    lblId.setFont(new Font("Segoe UI", Font.BOLD, 14));
    JTextField pid = new JTextField();
    pid.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    pid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28)); // fixes height
    row1.add(lblId);
    row1.add(Box.createRigidArea(new Dimension(10,0)));
    row1.add(pid);

    // ===== Decision Row =====
    JPanel row2 = new JPanel();
    row2.setOpaque(false);
    row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
    JLabel lblDecision = new JLabel("Decision:");
    lblDecision.setFont(new Font("Segoe UI", Font.BOLD, 14));
    JComboBox<String> decision = new JComboBox<>(new String[]{"Approve","Reject"});
    decision.setMaximumSize(new Dimension(120, 28));
    row2.add(lblDecision);
    row2.add(Box.createRigidArea(new Dimension(10,0)));
    row2.add(decision);

    // ===== Button Row =====
    JPanel row3 = new JPanel();
    row3.setOpaque(false);
    row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
    JButton btnSave = btn("Save Decision");
    btnSave.setPreferredSize(new Dimension(120, 30));
    row3.add(btnSave); 

    // ===== Add rows to background panel =====
    bgPanel.add(row1);
    bgPanel.add(Box.createRigidArea(new Dimension(0,10)));
    bgPanel.add(row2);
    bgPanel.add(Box.createRigidArea(new Dimension(0,20)));
    bgPanel.add(row3);

    // ===== Button Action =====
    btnSave.addActionListener(e -> {
        String policyId = pid.getText().trim();
        if (policyId.isEmpty()) {
            JOptionPane.showMessageDialog(f,"Enter Policy ID");
            return;
        }

        String d = decision.getSelectedItem().toString();
        MyQueue q = new MyQueue();
        try (BufferedReader br = new BufferedReader(new FileReader(POLICY_FILE))) {
            String l;
            while ((l = br.readLine()) != null) q.enqueue(l);
        } catch (Exception ex) {}

        MyQueue u = new MyQueue();
        boolean found = false;

        while (!q.isEmpty()) {
            String l = q.dequeue();
            String[] p1 = l.split(",",-1);

            if (p1[0].equals(policyId) && p1[5].equalsIgnoreCase("Pending")) {
                p1[5] = d.equals("Approve") ? "Active" : "Rejected";
                l = String.join(",", p1);
                found = true;
            }
            u.enqueue(l);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(POLICY_FILE))) {
            while (!u.isEmpty()) {
                bw.write(u.dequeue());
                bw.newLine();
            }
        } catch (Exception ex) {}

        output.setText(found ? "Decision saved successfully" : "Policy not pending or not found");
        f.dispose();
    });

    f.setVisible(true);
}

private JLabel setBackground(JFrame f) {
    ImageIcon bg = new ImageIcon("C:/Users/user/Downloads/A2.png"); 
    Image img = bg.getImage().getScaledInstance(800, 550, Image.SCALE_SMOOTH);
    JLabel background = new JLabel(new ImageIcon(img));

    background.setLayout(new BorderLayout());
    f.setContentPane(background);
    return background;
}

}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.insurance_gui;

/**
 *
 * @author user
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SearchUtility{

    /* ================= CUSTOMERS ================= */

    public static ArrayList<Customer> loadCustomers() {
        ArrayList<Customer> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            while ((line = br.readLine()) != null)
                list.add(new Customer(line.split(",")));
        } catch (IOException e) {
            System.out.println("Error reading customers file");
        }
        return list;
    }

    public static void mergeSortCustomers(ArrayList<Customer> list, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSortCustomers(list, l, m);
            mergeSortCustomers(list, m + 1, r);
            mergeCustomers(list, l, m, r);
        }
    }

    public static void mergeCustomers(ArrayList<Customer> list, int l, int m, int r) {
        ArrayList<Customer> temp = new ArrayList<>();
        int i = l, j = m + 1;

        while (i <= m && j <= r) {
            if (list.get(i).id.compareTo(list.get(j).id) <= 0)
                temp.add(list.get(i++));
            else temp.add(list.get(j++));
        }
        while (i <= m) temp.add(list.get(i++));
        while (j <= r) temp.add(list.get(j++));
        for (int k = 0; k < temp.size(); k++)
            list.set(l + k, temp.get(k));
    }

    public static Customer binarySearchCustomer(ArrayList<Customer> list, String id) {
        int low = 0, high = list.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = list.get(mid).id.compareTo(id);
            if (cmp == 0) return list.get(mid);
            else if (cmp < 0) low = mid + 1;
            else high = mid - 1;
        }
        return null;
    }

    /* ================= POLICIES ================= */

   public static ArrayList<Policy> loadPolicies() {
    ArrayList<Policy> list = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader("policies.txt"))) {
        String line;

        while ((line = br.readLine()) != null) {

            line = line.trim();

            // Skip empty lines
            if (line.isEmpty()) continue;

            String[] data = line.split(",");

            
            if (data.length < 6) {
                System.out.println("Skipping corrupted line: " + line);
                continue;
            }

            list.add(new Policy(data));
        }

    } catch (IOException e) {
        System.out.println("Error reading policies file");
    }

    return list;
}

    public static void mergeSortPolicies(ArrayList<Policy> list, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSortPolicies(list, l, m);
            mergeSortPolicies(list, m + 1, r);
            mergePolicies(list, l, m, r);
        }
    }

    public static void mergePolicies(ArrayList<Policy> list, int l, int m, int r) {
        ArrayList<Policy> temp = new ArrayList<>();
        int i = l, j = m + 1;

        while (i <= m && j <= r) {
            if (list.get(i).policyId.compareTo(list.get(j).policyId) <= 0)
                temp.add(list.get(i++));
            else temp.add(list.get(j++));
        }
        while (i <= m) temp.add(list.get(i++));
        while (j <= r) temp.add(list.get(j++));

        for (int k = 0; k < temp.size(); k++)
            list.set(l + k, temp.get(k));
    }

    public static Policy binarySearchPolicy(ArrayList<Policy> list, String id) {
        int low = 0, high = list.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = list.get(mid).policyId.compareTo(id);
            if (cmp == 0) return list.get(mid);
            else if (cmp < 0) low = mid + 1;
            else high = mid - 1;
        }
        return null;
    }
}

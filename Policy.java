/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.insurance_gui;

/**
 *
 * @author user
 */
public class Policy {
    String policyId, customerName, type, status;
    double coverage, premium;
  Policy(String[] d) {
        this.policyId     = safe(d, 0);
        this.customerName = safe(d, 1);
        this.type         = safe(d, 2);
        this.coverage     = parseDoubleSafe(d, 3);
        this.premium      = parseDoubleSafe(d, 4);
        this.status       = safe(d, 5);
    }
   // Prevents ArrayIndexOutOfBounds + null crashes
    private String safe(String[] d, int i) {
        if (i >= d.length || d[i] == null || d[i].trim().isEmpty())
            return "N/A";
        return d[i];
    }
    // Prevents NumberFormatException
    private double parseDoubleSafe(String[] d, int i) {
        try {
            if (i >= d.length || d[i] == null || d[i].trim().isEmpty())
                return 0.0;
            return Double.parseDouble(d[i].trim());
        } catch (Exception e) {
            return 0.0;
        }
    }
 }


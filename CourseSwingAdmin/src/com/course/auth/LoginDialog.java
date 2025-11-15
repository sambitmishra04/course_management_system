package com.course.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginDialog extends JDialog {
    private boolean succeeded = false;
    private JPasswordField passwordField;

    // change this to your desired password (simple)
    private static final String ADMIN_PASSWORD = "admin123";

    public LoginDialog(Frame parent) {
        super(parent, "Admin Login", true);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;

        JLabel lb = new JLabel("Password:");
        cs.gridx = 0; cs.gridy = 0; cs.gridwidth = 1;
        panel.add(lb, cs);

        passwordField = new JPasswordField(20);
        cs.gridx = 1; cs.gridy = 0; cs.gridwidth = 2;
        panel.add(passwordField, cs);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(panel, BorderLayout.CENTER);

        JPanel bp = new JPanel();
        JButton btnLogin = new JButton("Login");
        JButton btnCancel = new JButton("Cancel");
        bp.add(btnLogin);
        bp.add(btnCancel);

        btnLogin.addActionListener(e -> {
            String pass = new String(passwordField.getPassword());
            if (ADMIN_PASSWORD.equals(pass)) {
                succeeded = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(LoginDialog.this,
                        "Invalid password",
                        "Login",
                        JOptionPane.ERROR_MESSAGE);
                // clear password
                passwordField.setText("");
                succeeded = false;
            }
        });

        btnCancel.addActionListener(e -> {
            succeeded = false;
            dispose();
        });

        getContentPane().add(bp, BorderLayout.PAGE_END);
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);

        // respond to Enter
        getRootPane().setDefaultButton(btnLogin);
    }

    public boolean isSucceeded() {
        return succeeded;
    }
}

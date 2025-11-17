package swings;

import javax.swing.*;
import java.awt.*;
public class d_SwingTable {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JTable Demo");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        String[][] data = {
                {"1", "Prabin", "22"},
                {"2", "Rahul", "24"},
                {"3", "Sneha", "21"},
                {"4", "Mayank", "23"},
                {"5", "Shreya", "20"}
        };
        String[] columnNames = {"ID", "Name", "Age"};

        JTable table = new JTable(data, columnNames);
        JScrollPane tablePane = new JScrollPane(table);
        frame.add(tablePane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
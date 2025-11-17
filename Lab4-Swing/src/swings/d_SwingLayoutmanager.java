package swings;

import javax.swing.*;
import java.awt.*;

public class d_SwingLayoutmanager {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Layout Manager Demo");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // FlowLayout Panel
        JPanel flowPanel = new JPanel();
        flowPanel.setBorder(BorderFactory.createTitledBorder("FlowLayout"));
        flowPanel.setLayout(new FlowLayout());
        flowPanel.add(new JButton("A"));
        flowPanel.add(new JButton("B"));
        flowPanel.add(new JButton("C"));

        // BorderLayout Panel
        JPanel borderPanel = new JPanel();
        borderPanel.setBorder(BorderFactory.createTitledBorder("BorderLayout"));
        borderPanel.setLayout(new BorderLayout());
        borderPanel.add(new JButton("North"), BorderLayout.NORTH);
        borderPanel.add(new JButton("South"), BorderLayout.SOUTH);
        borderPanel.add(new JButton("East"), BorderLayout.EAST);
        borderPanel.add(new JButton("West"), BorderLayout.WEST);
        borderPanel.add(new JButton("Center"), BorderLayout.CENTER);

        // GridLayout Panel
        JPanel gridPanel = new JPanel();
        gridPanel.setBorder(BorderFactory.createTitledBorder("GridLayout"));
        gridPanel.setLayout(new GridLayout(2, 2));   // 2 rows, 2 columns
        gridPanel.add(new JButton("1"));
        gridPanel.add(new JButton("2"));
        gridPanel.add(new JButton("3"));
        gridPanel.add(new JButton("4"));

        // Main container using GridLayout to arrange the three layout panels
        frame.setLayout(new GridLayout(1, 3));
        frame.add(flowPanel);
        frame.add(borderPanel);
        frame.add(gridPanel);

        frame.setVisible(true);
    }
}

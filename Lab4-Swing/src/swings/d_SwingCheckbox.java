package swings;

import javax.swing.*;
class d_SwingCheckbox {
    public static void main(String[] args) {
        JFrame f = new JFrame("Language Selection");
        JLabel l1 = new JLabel("Select known Languages");
        l1.setBounds(100, 50, 200, 30);
        f.add(l1);

        JCheckBox c2 = new JCheckBox("Hindi");
        c2.setBounds(100, 100, 100, 30);
        f.add(c2);

        JCheckBox c3 = new JCheckBox("English");
        c3.setBounds(100, 150, 100, 30);
        f.add(c3);

        JCheckBox c4 = new JCheckBox("Marathi");
        c4.setBounds(100, 200, 100, 30);
        f.add(c4);

        f.setSize(400, 400);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
package swings;

import javax.swing.JButton;
import javax.swing.JFrame;

class d_SwingButtons {
    public static void main(String[] args) {
        JFrame f = new JFrame("Swing Buttons Example");
        JButton b1 = new JButton("OK");
        b1.setBounds(100, 50, 80, 30);
        f.add(b1);

        JButton b2 = new JButton("SUBMIT");
        b2.setBounds(100, 100, 100, 30);
        f.add(b2);

        JButton b3 = new JButton("CANCEL");
        b3.setBounds(100, 150, 100, 30);
        f.add(b3);

        f.setSize(400, 300);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
     
}

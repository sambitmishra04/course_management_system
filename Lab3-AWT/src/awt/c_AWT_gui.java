package awt;
import java.awt.*;
import java.awt.event.*;

class c_AWT_gui extends Frame implements ActionListener {
    Label label;
    Button button;
    Checkbox check;
    
    c_AWT_gui() {
        setLayout(new FlowLayout());

        label = new Label("Select the checkbox and click the button");
        add(label);

        check = new Checkbox("I agree");
        add(check);

        button = new Button("Submit");
        add(button);

        button.addActionListener(this);

        setSize(320, 180);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (check.getState())
            label.setText("Thank you!");
        else
            label.setText("Please check the box first");
    }

    public static void main(String[] args) {
        new c_AWT_gui();
    }
}


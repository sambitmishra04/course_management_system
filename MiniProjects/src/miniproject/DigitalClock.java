package miniproject;

import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.*;

public class DigitalClock extends Frame implements Runnable {
    private final Label timeLabel = new Label("", Label.CENTER);
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
    private Thread thread;
    private volatile boolean running = true;

    public DigitalClock() {
        setTitle("Digital Clock");
        setLayout(new BorderLayout());
        timeLabel.setFont(new Font("Monospaced", Font.BOLD, 64));
        add(timeLabel, BorderLayout.CENTER);
        setSize(420, 180);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                running = false;
                if (thread != null) thread.interrupt();
                dispose();
                System.exit(0);
            }
        });
        setVisible(true);
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (running) {
            timeLabel.setText(LocalTime.now().format(fmt));
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        }
    }

    public static void main(String[] args) {
        new DigitalClock();
    }
}

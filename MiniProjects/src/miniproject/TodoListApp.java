package miniproject;

import java.awt.*;
import java.awt.event.*;

public class TodoListApp extends Frame {
    private List taskList;
    private TextField taskInput;
    private Button addButton;
    private Button deleteButton;
    private Button clearButton;

    public TodoListApp() {
        setTitle("To-Do List App");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Panel with title and input
        Panel topPanel = new Panel();
        topPanel.setLayout(new BorderLayout());

        Label titleLabel = new Label("My To-Do List", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);
        topPanel.add(titleLabel, BorderLayout.NORTH);

        Panel inputPanel = new Panel();
        inputPanel.setLayout(new FlowLayout());

        Label inputLabel = new Label("Task:");
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        taskInput = new TextField(20);
        taskInput.setFont(new Font("Arial", Font.PLAIN, 14));
        addButton = new Button("Add Task");
        addButton.setFont(new Font("Arial", Font.PLAIN, 14));

        inputPanel.add(inputLabel);
        inputPanel.add(taskInput);
        inputPanel.add(addButton);

        topPanel.add(inputPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Task List in the center
        taskList = new List(10, false);
        taskList.setFont(new Font("Arial", Font.PLAIN, 16));
        add(taskList, BorderLayout.CENTER);

        // Bottom panel with buttons
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout());

        deleteButton = new Button("Delete Selected");
        clearButton = new Button("Clear All");
        Button exitButton = new Button("Exit");

        deleteButton.setFont(new Font("Arial", Font.PLAIN, 14));
        clearButton.setFont(new Font("Arial", Font.PLAIN, 14));
        exitButton.setFont(new Font("Arial", Font.PLAIN, 14));

        deleteButton.setBackground(Color.LIGHT_GRAY);
        clearButton.setBackground(Color.LIGHT_GRAY);
        exitButton.setBackground(Color.LIGHT_GRAY);

        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Event handling
        addButton.addActionListener(e -> addTask());
        deleteButton.addActionListener(e -> deleteTask());
        clearButton.addActionListener(e -> clearAllTasks());
        exitButton.addActionListener(e -> System.exit(0));

        taskInput.addActionListener(e -> addTask());
        taskList.addActionListener(e -> deleteTask());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        taskInput.requestFocus();
    }

    private void addTask() {
        String task = taskInput.getText().trim();
        if (!task.isEmpty()) {
            taskList.add(task);
            taskInput.setText("");
            taskInput.requestFocus();
        }
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            taskList.remove(selectedIndex);
        }
    }

    private void clearAllTasks() {
        taskList.removeAll();
        taskInput.requestFocus();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            TodoListApp app = new TodoListApp();
            app.setVisible(true);
        });
    }
}

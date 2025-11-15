package com.course.client;

import com.course.auth.LoginDialog;
import com.course.dao.CourseDAO;
import com.course.dao.EnrollmentDAO;
import com.course.dao.StudentDAO;
import com.course.model.CourseStat;
import com.course.model.EnrollmentDTO;
import com.course.model.StudentStat;
import com.course.util.DBConnection;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Admin panel (side-menu + card layout) with FlatLaf, icons, loading indicators and confirmations.
 */
public class AdminApp extends JFrame {
    private final CourseDAO courseDAO = new CourseDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    // UI components
    private CardLayout cards = new CardLayout();
    private JPanel contentPanel = new JPanel(cards);

    // Courses tab components
    private DefaultTableModel courseModel;
    private JTable courseTable;

    // Students tab components
    private DefaultTableModel studentModel;
    private JTable studentTable;

    // Students-in-course components
    private DefaultTableModel studentsInCourseModel;
    private JTable studentsInCourseTable;
    private JComboBox<CourseStat> courseComboBox;

    // Courses-of-student components
    private DefaultTableModel coursesOfStudentModel;
    private JTable coursesOfStudentTable;
    private JComboBox<StudentStat> studentComboBox;

    // side menu
    private JPanel sideMenu;

    public AdminApp() {
        super("Course Admin — Polished");

        // Install FlatLaf
        try { UIManager.setLookAndFeel(new FlatLightLaf()); }
        catch (Exception ignored) {}

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 680);
        setLocationRelativeTo(null);
        initUI();
        // initial loads (async)
      //  showCard("DASHBOARD");
        refreshAllAsync();
    }

    private void initUI() {
        getContentPane().setLayout(new BorderLayout());
        sideMenu = buildSideMenu();
        getContentPane().add(sideMenu, BorderLayout.WEST);

        // content panel (cards)
        contentPanel.setBorder(new EmptyBorder(12,12,12,12));
       // contentPanel.add(buildDashboardPanel(), "DASHBOARD");
        contentPanel.add(buildCoursesPanel(), "COURSES");
        contentPanel.add(buildStudentsPanel(), "STUDENTS");
        contentPanel.add(buildStudentsInCoursePanel(), "STUDENTS_IN_COURSE");
        contentPanel.add(buildCoursesOfStudentPanel(), "COURSES_OF_STUDENT");

        getContentPane().add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel buildSideMenu() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(12,12,12,8));
        menu.setBackground(new Color(250,250,250));
        menu.setPreferredSize(new Dimension(200, getHeight()));

        // brand
        JLabel logo = new JLabel(" Course Admin");
      //  logo.setIcon(loadIcon("icon_dashboard.png"));
        logo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logo.setBorder(new EmptyBorder(0,0,12,0));
        menu.add(logo);

      
        //menu.add(createMenuButton("Dashboard", "icon_dashboard.png", e -> showCard("DASHBOARD")));
        menu.add(createMenuButton("Courses", "icon_courses.png", e -> { showCard("COURSES"); loadCoursesAsync(); }));
        menu.add(createMenuButton("Students", "icon_students.png", e -> { showCard("STUDENTS"); loadStudentsAsync(); }));
        menu.add(createMenuButton("Students in Course", "icon_people.png", e -> { showCard("STUDENTS_IN_COURSE"); loadCourseComboAsync(); }));
        menu.add(createMenuButton("Courses of Student", "icon_courses_of_student.png", e -> { showCard("COURSES_OF_STUDENT"); loadStudentComboAsync(); }));

        menu.add(Box.createVerticalGlue());

        // quick actions panel
        JPanel quick = new JPanel(new GridLayout(0,1,6,6));
        quick.setOpaque(false);
        JButton btnRefresh = new JButton("Refresh All", loadIcon("icon_refresh.png"));
        btnRefresh.addActionListener(e -> refreshAllAsync());
        quick.add(btnRefresh);

        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(e -> {
            int ok = JOptionPane.showConfirmDialog(this, "Exit admin panel?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) System.exit(0);
        });
        quick.add(btnExit);

        menu.add(quick);
        return menu;
    }

    private JButton createMenuButton(String text, String iconName, ActionListener al) {
        ImageIcon ic = loadIcon(iconName);
        JButton b = new JButton(text, ic);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        b.setFocusPainted(false);
        b.addActionListener(al);
        b.setBackground(new Color(245,245,245));
        b.setBorder(BorderFactory.createEmptyBorder(6,8,6,8));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private ImageIcon loadIcon(String name) {
        try {
            // look for resource under /resources/icons/
            java.net.URL url = getClass().getClassLoader().getResource("resources/icons/" + name);
            if (url != null) return new ImageIcon(url);
        } catch (Exception ignored) {}
        return null; // fallback to no icon
    }

    private void showCard(String name) {
        cards.show(contentPanel, name);
    }

    // ---------------- Dashboard ----------------
    private JPanel buildDashboardPanel() {
        JPanel p = new JPanel(new BorderLayout(8,8));
        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        p.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.add(new JLabel("Quick stats and charts can go here (future)."));
        p.add(center, BorderLayout.CENTER);
        return p;
    }

    // ---------------- Courses Panel ----------------
    private JPanel buildCoursesPanel() {
        JPanel p = new JPanel(new BorderLayout(8,8));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Add", loadIcon("icon_add.png"));
        JButton btnEdit = new JButton("Edit", loadIcon("icon_edit.png"));
        JButton btnDelete = new JButton("Delete", loadIcon("icon_delete.png"));
        JButton btnRefresh = new JButton("Refresh", loadIcon("icon_refresh.png"));

        top.add(btnRefresh); top.add(btnAdd); top.add(btnEdit); top.add(btnDelete);
        p.add(top, BorderLayout.NORTH);

        courseModel = new DefaultTableModel(new Object[]{"ID","Code","Title","Fee","Seats","Enrolled"}, 0) {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        courseTable = new JTable(courseModel);
        courseTable.setRowHeight(26);
        p.add(new JScrollPane(courseTable), BorderLayout.CENTER);

        // actions
        btnRefresh.addActionListener(e -> loadCoursesAsync());
        btnAdd.addActionListener(e -> showAddCourseDialogAsync());
        btnEdit.addActionListener(e -> showEditCourseDialogAsync());
        btnDelete.addActionListener(e -> deleteSelectedCourseAsync());

        // double-click edit
        courseTable.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2) showEditCourseDialogAsync();
            }
        });

        return p;
    }

    // ---------------- Students Panel ----------------
    private JPanel buildStudentsPanel() {
        JPanel p = new JPanel(new BorderLayout(8,8));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRefresh = new JButton("Refresh", loadIcon("icon_refresh.png"));
        JButton btnView = new JButton("View Courses of Selected");
        top.add(btnRefresh); top.add(btnView);
        p.add(top, BorderLayout.NORTH);

        studentModel = new DefaultTableModel(new Object[]{"ID","Username","Name","Email","Phone","Enrolled"}, 0) {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        studentTable = new JTable(studentModel);
        studentTable.setRowHeight(24);
        p.add(new JScrollPane(studentTable), BorderLayout.CENTER);

        btnRefresh.addActionListener(e -> loadStudentsAsync());
        btnView.addActionListener(e -> {
            int sel = studentTable.getSelectedRow();
            if (sel < 0) { JOptionPane.showMessageDialog(this, "Select a student first"); return; }
            int studentId = (int) studentModel.getValueAt(sel,0);
            loadCoursesOfStudentAsync(studentId);
            showCard("COURSES_OF_STUDENT");
            selectStudentInCombo(studentId);
        });
        return p;
    }

    // ---------------- Students-in-course Panel ----------------
    private JPanel buildStudentsInCoursePanel() {
        JPanel p = new JPanel(new BorderLayout(8,8));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        courseComboBox = new JComboBox<>();
        JButton btnLoad = new JButton("Load Students");
        top.add(new JLabel("Select course:")); top.add(courseComboBox); top.add(btnLoad);
        p.add(top, BorderLayout.NORTH);

        studentsInCourseModel = new DefaultTableModel(new Object[]{"ID","Username","Name","Email","Phone"}, 0) {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        studentsInCourseTable = new JTable(studentsInCourseModel);
        studentsInCourseTable.setRowHeight(24);
        p.add(new JScrollPane(studentsInCourseTable), BorderLayout.CENTER);

        btnLoad.addActionListener(e -> {
            CourseStat cs = (CourseStat) courseComboBox.getSelectedItem();
            if (cs == null) { JOptionPane.showMessageDialog(this, "Select a course"); return; }
            loadStudentsInCourseAsync(cs.getCourseId());
        });
        return p;
    }

    // ---------------- Courses-of-student Panel ----------------
    private JPanel buildCoursesOfStudentPanel() {
        JPanel p = new JPanel(new BorderLayout(8,8));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        studentComboBox = new JComboBox<>();
        JButton btnLoad = new JButton("Load Courses");
        top.add(new JLabel("Select student:")); top.add(studentComboBox); top.add(btnLoad);
        p.add(top, BorderLayout.NORTH);

        coursesOfStudentModel = new DefaultTableModel(new Object[]{"EnrollmentID","CourseID","Code","Title","Fee","Enrolled On","Status"}, 0) {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        coursesOfStudentTable = new JTable(coursesOfStudentModel);
        coursesOfStudentTable.setRowHeight(24);
        p.add(new JScrollPane(coursesOfStudentTable), BorderLayout.CENTER);

        btnLoad.addActionListener(e -> {
            StudentStat st = (StudentStat) studentComboBox.getSelectedItem();
            if (st == null) { JOptionPane.showMessageDialog(this, "Select a student"); return; }
            loadCoursesOfStudentAsync(st.getStudentId());
        });
        return p;
    }

    // ---------------------- Async tasks and helpers ----------------------

    // show a modal progress dialog while the SwingWorker runs
    private <T> void runWithLoading(SwingWorker<T,Void> worker, String message) {
        final JDialog dlg = new JDialog(this, true);
        dlg.setUndecorated(true);
        JPanel p = new JPanel(new BorderLayout(8,8));
        p.setBorder(new EmptyBorder(12,12,12,12));
        p.add(new JLabel(message), BorderLayout.NORTH);
        JProgressBar pb = new JProgressBar();
        pb.setIndeterminate(true);
        p.add(pb, BorderLayout.CENTER);
        dlg.getContentPane().add(p);
        dlg.pack();
        dlg.setLocationRelativeTo(this);

        worker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName()) && SwingWorker.StateValue.DONE == evt.getNewValue()) {
                dlg.dispose();
            }
        });

        worker.execute();
        dlg.setVisible(true);
        // after visible returns, worker finished
    }

    private void refreshAllAsync() {
        loadCoursesAsync();
        loadStudentsAsync();
        loadCourseComboAsync();
        loadStudentComboAsync();
    }

    private void loadCoursesAsync() {
        SwingWorker<List<CourseStat>, Void> w = new SwingWorker<>() {
            @Override protected List<CourseStat> doInBackground() throws Exception {
                return courseDAO.getAllWithEnrollCount();
            }
            @Override protected void done() {
                try {
                    List<CourseStat> list = get();
                    courseModel.setRowCount(0);
                    for (CourseStat s : list) {
                        courseModel.addRow(new Object[]{ s.getCourseId(), s.getCode(), s.getTitle(), s.getFee(), s.getSeats(), s.getEnrolledCount() });
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    showError("Load courses failed: " + ex.getMessage());
                }
            }
        };
        runWithLoading(w, "Loading courses...");
    }

    private void loadStudentsAsync() {
        SwingWorker<List<StudentStat>, Void> w = new SwingWorker<>() {
            @Override protected List<StudentStat> doInBackground() throws Exception {
                return studentDAO.getAllWithEnrollCount();
            }
            @Override protected void done() {
                try {
                    List<StudentStat> list = get();
                    studentModel.setRowCount(0);
                    for (StudentStat s : list) {
                        studentModel.addRow(new Object[]{ s.getStudentId(), s.getUsername(), s.getFullName(), s.getEmail(), s.getPhone(), s.getEnrolledCount() });
                    }
                } catch (InterruptedException | ExecutionException ex) { showError("Load students failed: " + ex.getMessage()); }
            }
        };
        runWithLoading(w, "Loading students...");
    }

    private void loadCourseComboAsync() {
        SwingWorker<List<CourseStat>, Void> w = new SwingWorker<>() {
            @Override protected List<CourseStat> doInBackground() throws Exception {
                return courseDAO.getAllWithEnrollCount();
            }
            @Override protected void done() {
                try {
                    List<CourseStat> list = get();
                    courseComboBox.removeAllItems();
                    for (CourseStat s : list) courseComboBox.addItem(s);
                } catch (InterruptedException | ExecutionException ex) { showError("Load courses failed: " + ex.getMessage()); }
            }
        };
        runWithLoading(w, "Loading course list...");
    }

    private void loadStudentComboAsync() {
        SwingWorker<List<StudentStat>, Void> w = new SwingWorker<>() {
            @Override protected List<StudentStat> doInBackground() throws Exception {
                return studentDAO.getAllWithEnrollCount();
            }
            @Override protected void done() {
                try {
                    List<StudentStat> list = get();
                    studentComboBox.removeAllItems();
                    for (StudentStat s : list) studentComboBox.addItem(s);
                } catch (InterruptedException | ExecutionException ex) { showError("Load students failed: " + ex.getMessage()); }
            }
        };
        runWithLoading(w, "Loading student list...");
    }

    private void loadStudentsInCourseAsync(int courseId) {
        SwingWorker<List<StudentStat>, Void> w = new SwingWorker<>() {
            @Override protected List<StudentStat> doInBackground() throws Exception {
                return enrollmentDAO.getStudentsByCourse(courseId);
            }
            @Override protected void done() {
                try {
                    List<StudentStat> list = get();
                    studentsInCourseModel.setRowCount(0);
                    for (StudentStat s : list) {
                        studentsInCourseModel.addRow(new Object[]{ s.getStudentId(), s.getUsername(), s.getFullName(), s.getEmail(), s.getPhone() });
                    }
                } catch (InterruptedException | ExecutionException ex) { showError("Load students for course failed: " + ex.getMessage()); }
            }
        };
        runWithLoading(w, "Loading students for course...");
    }

    private void loadCoursesOfStudentAsync(int studentId) {
        SwingWorker<List<EnrollmentDTO>, Void> w = new SwingWorker<>() {
            @Override protected List<EnrollmentDTO> doInBackground() throws Exception {
                return enrollmentDAO.getEnrollmentsByStudent(studentId);
            }
            @Override protected void done() {
                try {
                    List<EnrollmentDTO> list = get();
                    coursesOfStudentModel.setRowCount(0);
                    for (EnrollmentDTO e : list) {
                        coursesOfStudentModel.addRow(new Object[]{ e.getEnrollmentId(), e.getCourseId(), e.getCourseCode(), e.getCourseTitle(), e.getFee(), e.getEnrollDate(), e.getStatus() });
                    }
                } catch (InterruptedException | ExecutionException ex) { showError("Load courses for student failed: " + ex.getMessage()); }
            }
        };
        runWithLoading(w, "Loading courses for student...");
    }

    // ---------------- Add/Edit/Delete methods (async wrappers with confirmations) ----------------

    private void showAddCourseDialogAsync() {
        // we open the dialog on EDT and perform DB insert in background (simple synchronous insert was used earlier)
        JDialog d = new JDialog(this, "Add Course", true);
        d.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints(); c.insets = new Insets(6,6,6,6); c.fill = GridBagConstraints.HORIZONTAL;
        JTextField codeF = new JTextField(); JTextField titleF = new JTextField(); JTextArea descA = new JTextArea(3, 40);
        JTextField durF = new JTextField("4"); JTextField feeF = new JTextField("2500.00"); JTextField seatsF = new JTextField("20");
        int row = 0;
        c.gridx=0; c.gridy=row; d.add(new JLabel("Code:"), c); c.gridx=1; d.add(codeF,c); row++;
        c.gridx=0; c.gridy=row; d.add(new JLabel("Title:"), c); c.gridx=1; d.add(titleF,c); row++;
        c.gridx=0; c.gridy=row; d.add(new JLabel("Description:"), c); c.gridx=1; d.add(new JScrollPane(descA),c); row++;
        c.gridx=0; c.gridy=row; d.add(new JLabel("Duration (weeks):"), c); c.gridx=1; d.add(durF,c); row++;
        c.gridx=0; c.gridy=row; d.add(new JLabel("Fee:"), c); c.gridx=1; d.add(feeF,c); row++;
        c.gridx=0; c.gridy=row; d.add(new JLabel("Seats:"), c); c.gridx=1; d.add(seatsF,c); row++;
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton save = new JButton("Save"); JButton cancel = new JButton("Cancel"); btns.add(save); btns.add(cancel);
        c.gridx=0; c.gridy=row; c.gridwidth=2; d.add(btns,c);

        save.addActionListener(ev -> {
            String code = codeF.getText().trim();
            String title = titleF.getText().trim();
            String desc = descA.getText().trim();
            int dur; BigDecimal fee; int seats;
            try {
                dur = Integer.parseInt(durF.getText().trim());
                fee = new BigDecimal(feeF.getText().trim());
                seats = Integer.parseInt(seatsF.getText().trim());
            } catch (Exception ex) { JOptionPane.showMessageDialog(d, "Enter valid numeric values"); return; }

            d.dispose();
            // background insert
            SwingWorker<Void,Void> w = new SwingWorker<>() {
                @Override protected Void doInBackground() throws Exception {
                    String insertSql = "INSERT INTO courses (code,title,description,duration_weeks,fee,seats) VALUES (?,?,?,?,?,?)";
                    try (Connection conn = DBConnection.getConnection();
                         java.sql.PreparedStatement ps = conn.prepareStatement(insertSql)) {
                        ps.setString(1, code); ps.setString(2, title); ps.setString(3, desc);
                        ps.setInt(4, dur); ps.setBigDecimal(5, fee); ps.setInt(6, seats);
                        ps.executeUpdate();
                    }
                    return null;
                }
                @Override protected void done() {
                    loadCoursesAsync();
                    loadCourseComboAsync();
                }
            };
            runWithLoading(w, "Saving new course...");
        });

        cancel.addActionListener(ev -> d.dispose());
        d.pack(); d.setLocationRelativeTo(this); d.setVisible(true);
    }

    private void showEditCourseDialogAsync() {
        int sel = courseTable.getSelectedRow();
        if (sel < 0) { JOptionPane.showMessageDialog(this, "Select a course"); return; }
        int courseId = (int) courseModel.getValueAt(sel,0);
        String code = String.valueOf(courseModel.getValueAt(sel,1));
        String title = String.valueOf(courseModel.getValueAt(sel,2));
        BigDecimal fee = (BigDecimal) courseModel.getValueAt(sel,3);
        int seats = (int) courseModel.getValueAt(sel,4);

        JDialog d = new JDialog(this, "Edit Course", true);
        d.setLayout(new GridBagLayout());
        GridBagConstraints c=new GridBagConstraints(); c.insets=new Insets(6,6,6,6); c.fill=GridBagConstraints.HORIZONTAL;
        JTextField codeF = new JTextField(code); codeF.setEditable(false);
        JTextField titleF = new JTextField(title); titleF.setEditable(false);
        JTextField feeF = new JTextField(fee.toString());
        JTextField seatsF = new JTextField(String.valueOf(seats));
        int row=0;
        c.gridx=0; c.gridy=row; d.add(new JLabel("Code:"),c); c.gridx=1; d.add(codeF,c); row++;
        c.gridx=0; c.gridy=row; d.add(new JLabel("Title:"),c); c.gridx=1; d.add(titleF,c); row++;
        c.gridx=0; c.gridy=row; d.add(new JLabel("Fee:"),c); c.gridx=1; d.add(feeF,c); row++;
        c.gridx=0; c.gridy=row; d.add(new JLabel("Seats:"),c); c.gridx=1; d.add(seatsF,c); row++;
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT)); JButton save = new JButton("Save"); JButton cancel = new JButton("Cancel"); btns.add(save); btns.add(cancel);
        c.gridx=0; c.gridy=row; c.gridwidth=2; d.add(btns,c);

        save.addActionListener(ev -> {
            BigDecimal newFee; int newSeats;
            try { newFee = new BigDecimal(feeF.getText().trim()); newSeats = Integer.parseInt(seatsF.getText().trim()); }
            catch (Exception ex) { JOptionPane.showMessageDialog(d, "Enter valid numeric values"); return; }
            d.dispose();
            SwingWorker<Void,Void> w = new SwingWorker<>() {
                @Override protected Void doInBackground() throws Exception {
                    courseDAO.updateCourseSeatsAndFee(courseId, newSeats, newFee);
                    return null;
                }
                @Override protected void done() { loadCoursesAsync(); loadCourseComboAsync(); }
            };
            runWithLoading(w, "Updating course...");
        });
        cancel.addActionListener(ev -> d.dispose());
        d.pack(); d.setLocationRelativeTo(this); d.setVisible(true);
    }

    private void deleteSelectedCourseAsync() {
        int sel = courseTable.getSelectedRow();
        if (sel < 0) { JOptionPane.showMessageDialog(this, "Select a course to delete"); return; }
        int courseId = (int) courseModel.getValueAt(sel,0);
        String title = String.valueOf(courseModel.getValueAt(sel,2));
        int ok = JOptionPane.showConfirmDialog(this, "Delete course \""+title+"\" and its enrollments?","Confirm delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (ok != JOptionPane.YES_OPTION) return;
        SwingWorker<Void,Void> w = new SwingWorker<>() {
            @Override protected Void doInBackground() throws Exception {
                courseDAO.deleteCourseById(courseId);
                return null;
            }
            @Override protected void done() { loadCoursesAsync(); loadCourseComboAsync(); loadStudentsAsync(); loadStudentComboAsync(); }
        };
        runWithLoading(w, "Deleting course...");
    }

    // ------ helper to select student in combo after switching tabs ------
    private void selectStudentInCombo(int studentId) {
        for (int i=0;i<studentComboBox.getItemCount();i++) {
            StudentStat s = studentComboBox.getItemAt(i);
            if (s != null && s.getStudentId() == studentId) { studentComboBox.setSelectedIndex(i); return; }
        }
    }

    // ---------------- Utility ----------------
    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // show login first
            LoginDialog login = new LoginDialog(null);
            login.setVisible(true);
            if (login.isSucceeded()) {
                AdminApp app = new AdminApp();
                app.setVisible(true);
            } else {
                System.out.println("Login cancelled or failed. Exiting.");
                System.exit(0);
            }
        });
    }
}

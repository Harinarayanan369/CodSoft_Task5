import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainFrame extends JFrame {
    private StudentManagementSystem sms = new StudentManagementSystem();

    private JTextField nameField, rollNumberField, gradeField;

    public MainFrame() {
        setTitle("Student Management System");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center the frame
        setLocationRelativeTo(null);

        // Header
        JLabel header = new JLabel("STUDENT MANAGEMENT SYSTEM", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setForeground(Color.BLUE);
        add(header, BorderLayout.NORTH);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(11, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(new JLabel("Name:"));
        nameField = new JTextField(20);
        panel.add(nameField);

        panel.add(new JLabel("Roll Number:"));
        rollNumberField = new JTextField(20);
        panel.add(rollNumberField);

        panel.add(new JLabel("Grade:"));
        gradeField = new JTextField(20);
        panel.add(gradeField);

        JButton addButton = new JButton("Add Student");
        addButton.setFont(new Font("Arial", Font.PLAIN, 16));
        addButton.setBackground(Color.gray);
        addButton.addActionListener(new AddButtonListener());
        panel.add(addButton);

        JButton updateButton = new JButton("Update Student");
        updateButton.setFont(new Font("Arial", Font.PLAIN, 16));
        updateButton.setBackground(Color.gray);
        updateButton.addActionListener(new UpdateButtonListener());
        panel.add(updateButton);

        JButton searchButton = new JButton("Search Student");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 16));
        searchButton.setBackground(Color.gray);
        searchButton.addActionListener(new SearchButtonListener());
        panel.add(searchButton);

        JButton removeButton = new JButton("Remove Student");
        removeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        removeButton.setBackground(Color.gray);
        removeButton.addActionListener(new RemoveButtonListener());
        panel.add(removeButton);

        JButton displayButton = new JButton("Display All Students");
        displayButton.setFont(new Font("Arial", Font.PLAIN, 16));
        displayButton.setBackground(Color.gray);
        displayButton.addActionListener(new DisplayButtonListener());
        panel.add(displayButton);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            int rollNumber;
            try {
                rollNumber = Integer.parseInt(rollNumberField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Roll Number must be an integer.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String grade = gradeField.getText();

            if (name.isEmpty() || grade.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Name and grade cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Student student = new Student(name, rollNumber, grade);
            try {
                sms.addStudent(student);
                JOptionPane.showMessageDialog(null, "Student added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Duplicate roll number. Please use a different roll number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            int rollNumber;
            try {
                rollNumber = Integer.parseInt(rollNumberField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Roll Number must be an integer.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String grade = gradeField.getText();

            if (name.isEmpty() || grade.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Name and grade cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Student student = new Student(name, rollNumber, grade);
            try {
                sms.updateStudent(student);
                JOptionPane.showMessageDialog(null, "Student updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "No student with the given roll number found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int rollNumber;
            try {
                rollNumber = Integer.parseInt(rollNumberField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Roll Number must be an integer.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Student student = sms.searchStudent(rollNumber);
            if (student != null) {
                JOptionPane.showMessageDialog(null, "Student found: " + student, "Student Found", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Student not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class RemoveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int rollNumber;
            try {
                rollNumber = Integer.parseInt(rollNumberField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Roll Number must be an integer.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            sms.removeStudent(rollNumber);
            JOptionPane.showMessageDialog(null, "Student removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class DisplayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            java.util.List<Student> students = sms.getAllStudents();
            StringBuilder message = new StringBuilder();
            if (students.isEmpty()) {
                message.append("No students to display.");
            } else {
                for (Student student : students) {
                    message.append(student).append("\n");
                }
            }
            JOptionPane.showMessageDialog(null, message.toString(), "All Students", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}

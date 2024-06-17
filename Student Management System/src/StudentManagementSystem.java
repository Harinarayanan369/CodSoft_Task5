import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManagementSystem {
    private Connection connection;

    public StudentManagementSystem() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "hari");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addStudent(Student student) throws SQLException {
        if (isDuplicateRollNumber(student.getRollNumber())) {
            throw new SQLException("Duplicate roll number");
        }
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO students (name, roll_number, grade) VALUES (?, ?, ?)")) {
            stmt.setString(1, student.getName());
            stmt.setInt(2, student.getRollNumber());
            stmt.setString(3, student.getGrade());
            stmt.executeUpdate();
        }
    }

    public void removeStudent(int rollNumber) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM students WHERE roll_number = ?")) {
            stmt.setInt(1, rollNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Student searchStudent(int rollNumber) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM students WHERE roll_number = ?")) {
            stmt.setInt(1, rollNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Student(rs.getString("name"), rs.getInt("roll_number"), rs.getString("grade"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            while (rs.next()) {
                students.add(new Student(rs.getString("name"), rs.getInt("roll_number"), rs.getString("grade")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public void updateStudent(Student student) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE students SET name = ?, grade = ? WHERE roll_number = ?")) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getGrade());
            stmt.setInt(3, student.getRollNumber());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("No student with the given roll number found.");
            }
        }
    }

    private boolean isDuplicateRollNumber(int rollNumber) {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM students WHERE roll_number = ?")) {
            stmt.setInt(1, rollNumber);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

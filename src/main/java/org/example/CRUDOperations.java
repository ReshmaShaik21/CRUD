package org.example;
import java.sql.*;
import java.util.Scanner;

public class CRUDOperations {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/studentdb";
        String username = "root";
        String password = "reshma@21";

        try (
                Connection con = DriverManager.getConnection(
                        url, username, password
                );
                Scanner sc = new Scanner(System.in)
        ) {
            System.out.println("Database connected successfully!");

            while (true) {
                System.out.println("\n--- STUDENT CRUD MENU ---");
                System.out.println("1. Create Student");
                System.out.println("2. Read Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();

                switch (choice) {

                    case 1: // CREATE
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter age: ");
                        int age = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter course: ");
                        String course = sc.nextLine();

                        String insertSQL =
                                "INSERT INTO students (id, name, age, course) "
                                        + "VALUES (?, ?, ?, ?)";

                        try (PreparedStatement ps =
                                     con.prepareStatement(insertSQL)) {
                            ps.setInt(1, id);
                            ps.setString(2, name);
                            ps.setInt(3, age);
                            ps.setString(4, course);

                            ps.executeUpdate();
                            System.out.println("Student added successfully!");
                        }
                        break;

                    case 2: // READ
                        String selectSQL = "SELECT * FROM students";

                        try (Statement st = con.createStatement();
                             ResultSet rs = st.executeQuery(selectSQL)) {

                            System.out.println("\nID\tName\tAge\tCourse");

                            while (rs.next()) {
                                System.out.println(
                                        rs.getInt("id") + "\t" +
                                                rs.getString("name") + "\t" +
                                                rs.getInt("age") + "\t" +
                                                rs.getString("course")
                                );
                            }
                        }
                        break;

                    case 3: // UPDATE
                        System.out.print("Enter student ID to update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter new name: ");
                        String newName = sc.nextLine();

                        System.out.print("Enter new age: ");
                        int newAge = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter new course: ");
                        String newCourse = sc.nextLine();

                        String updateSQL =
                                "UPDATE students SET name=?, age=?, course=? "
                                        + "WHERE id=?";

                        try (PreparedStatement ps =
                                     con.prepareStatement(updateSQL)) {
                            ps.setString(1, newName);
                            ps.setInt(2, newAge);
                            ps.setString(3, newCourse);
                            ps.setInt(4, updateId);

                            int rows = ps.executeUpdate();

                            if (rows > 0)
                                System.out.println("Student updated successfully!");
                            else
                                System.out.println("Student ID not found.");
                        }
                        break;

                    case 4: // DELETE
                        System.out.print("Enter student ID to delete: ");
                        int deleteId = sc.nextInt();

                        String deleteSQL =
                                "DELETE FROM students WHERE id=?";

                        try (PreparedStatement ps =
                                     con.prepareStatement(deleteSQL)) {
                            ps.setInt(1, deleteId);

                            int rows = ps.executeUpdate();

                            if (rows > 0)
                                System.out.println("Student deleted successfully!");
                            else
                                System.out.println("Student ID not found.");
                        }
                        break;

                    case 5:
                        System.out.println("Program terminated.");
                        return;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}

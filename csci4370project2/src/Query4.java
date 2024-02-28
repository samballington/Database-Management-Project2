//@author Brendon Prophette
import java.sql.*;

public class Query4 {
    public static String executeQuery() {
        // Using try-with-resources to ensure automatic closing of resources
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://database-1.c94soiwsy7xp.us-east-1.rds.amazonaws.com:3306/employees", "admin", "password")) {

            // SQL query to retrieve female employees born before 1990 with a salary greater than $80,000
            String query = "SELECT DISTINCT e.emp_no, e.first_name, e.last_name, e.birth_date, e.gender, s.salary " +
                    "FROM employees e " +
                    "JOIN dept_manager dm ON e.emp_no = dm.emp_no " +
                    "JOIN salaries s ON e.emp_no = s.emp_no " +
                    "WHERE e.gender = 'F' " +
                    "AND e.birth_date < '1990-01-01' " +
                    "AND s.salary > 80000 " +
                    "AND s.from_date <= CURDATE() " +
                    "AND (s.to_date >= CURDATE() OR s.to_date = '9999-01-01')";

            // Using try-with-resources for PreparedStatement and ResultSet to ensure automatic closing
            try (PreparedStatement pstmt = connection.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {

                // Building the result string based on the query results
                StringBuilder resultBuilder = new StringBuilder();
                while (rs.next()) {
                    int empNo = rs.getInt("emp_no");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String birthDate = rs.getString("birth_date");
                    String gender = rs.getString("gender");
                    int salary = rs.getInt("salary");

                    resultBuilder.append("ID: ").append(empNo)
                            .append(", ").append(firstName)
                            .append(", ").append(lastName)
                            .append(", ").append(birthDate)
                            .append(", ").append(gender)
                            .append(", ").append(salary).append(" salary").append("\n");
                }
                return resultBuilder.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error executing Query 4";
        }
    }
}
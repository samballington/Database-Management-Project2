//@author Brendon Prophette
import java.sql.*;

public class Query5 {
    public static String executeQuery() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://database-1.c94soiwsy7xp.us-east-1.rds.amazonaws.com:3306/employees", "admin", "password")) {

            int empNo1 = 10017; // example ID for E1
            int empNo2 = 10055; // example ID for E2

            String query = "SELECT e1.emp_no AS E1, e2.emp_no AS E2, e1.dept_no AS D1 " +
                    "FROM dept_emp e1 " +
                    "JOIN dept_emp e2 ON e1.dept_no = e2.dept_no AND e1.emp_no != e2.emp_no " +
                    "WHERE e1.emp_no = ? AND e2.emp_no = ? " +
                    "AND e1.from_date <= e2.to_date AND e2.from_date <= e1.to_date";

            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, empNo1);
                pstmt.setInt(2, empNo2);

                try (ResultSet rs = pstmt.executeQuery()) {
                    StringBuilder resultBuilder = new StringBuilder();
                    if (rs.next()) {
                        resultBuilder.append("1-degree separation found between employee ").append(empNo1)
                                .append(" and employee ").append(empNo2)
                                .append(" in department ").append(rs.getString("D1")).append("\n");
                    } else {
                        resultBuilder.append("No 1-degree separation found between employee ").append(empNo1)
                                .append(" and employee ").append(empNo2).append("\n");
                    }
                    return resultBuilder.toString();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error executing Query 5";
        }
    }
}
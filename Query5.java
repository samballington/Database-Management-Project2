import java.sql.*;
// @author Brendon Prophette
public class Query5 {
    public static void main(String[] args) {
        // assuming we're looking for a connection between two specificc employees based on their IDs
        int empNo1 = 10017; // example ID for E1
        int empNo2 = 10055; // example ID for E2

        // SQL query to find 1 degree of separation
        String query = "SELECT e1.emp_no AS E1, e2.emp_no AS E2, e1.dept_no AS D1 " +
                "FROM dept_emp e1 " +
                "JOIN dept_emp e2 ON e1.dept_no = e2.dept_no AND e1.emp_no != e2.emp_no " +
                "WHERE e1.emp_no = ? AND e2.emp_no = ? " +
                "AND e1.from_date <= e2.to_date AND e2.from_date <= e1.to_date";
        try {
            Connection connection = connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "root", "Roblinium12");
            PreparedStatement pstmt = connection.prepareStatement(query); // prepare the SQL query
            {

                pstmt.setInt(1, empNo1); // set the first parameter (E1)
                pstmt.setInt(2, empNo2); // set the second parameter (E2)

                ResultSet rs = pstmt.executeQuery();

                // check if any results are returned
                if (rs.next()) {
                    // output the details if a 1-degree separation exists
                    System.out.println("1-degree separation found between employee " + empNo1 + " and employee " + empNo2 +
                            " in department " + rs.getString("D1"));
                } else {
                    // no 1-degree separation found
                    System.out.println("No 1-degree separation found between employee " + empNo1 + " and employee " + empNo2);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

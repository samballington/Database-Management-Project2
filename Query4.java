import java.sql.*;
// @author Brendon Prophette
public class Query4 {

    public static void main(String[] args) {

        // SQL query to get girl bosses
        String query = "SELECT DISTINCT e.emp_no, e.first_name, e.last_name, e.birth_date, e.gender, s.salary " +
                "FROM employees e " +
                "JOIN dept_manager dm ON e.emp_no = dm.emp_no " +
                "JOIN salaries s ON e.emp_no = s.emp_no " +
                "WHERE e.gender = 'F' " +
                "AND e.birth_date < '1990-01-01' " +
                "AND s.salary > 80000 " +
                "AND s.from_date <= CURDATE() " +
                "AND (s.to_date >= CURDATE() OR s.to_date = '9999-01-01')";

        try {
            Connection connection = connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "root", "Roblinium12");


            PreparedStatement pstmt = connection.prepareStatement(query); // prepare SQL query
            {
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) { // extract data from each row in the result set
                    int empNo = rs.getInt("emp_no");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String birthDate = rs.getString("birth_date");
                    String gender = rs.getString("gender");
                    int salary = rs.getInt("salary");

                    System.out.println("ID: " + empNo + ", " + firstName + ", " + lastName + ", " + birthDate + ", " + gender + ", " + salary + " salary");


            }
     }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
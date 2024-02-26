import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query3 {

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = DriverManager.getConnection("jdbc:mysql://database-1.c94soiwsy7xp.us-east-1.rds.amazonaws.com:3306/employees", "admin", "password");


            String query = "SELECT d.dept_name AS department, " +
                    "FLOOR(YEAR(e.birth_date) / 10) * 10 AS decade, " +
                    "COUNT(*) AS num_employees, " +
                    "AVG(s.salary) AS avg_salary " +
                    "FROM employees e " +
                    "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                    "JOIN departments d ON de.dept_no = d.dept_no " +
                    "JOIN salaries s ON e.emp_no = s.emp_no " +
                    "GROUP BY d.dept_name, FLOOR(YEAR(e.birth_date) / 10) * 10 " +
                    "ORDER BY department, decade";


            preparedStatement = connection.prepareStatement(query);


            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                String department = resultSet.getString("department");
                int decade = resultSet.getInt("decade");
                int numEmployees = resultSet.getInt("num_employees");
                double avgSalary = resultSet.getDouble("avg_salary");


                System.out.println("Department: " + department +
                        ", Decade: " + decade +
                        ", Number of Employees: " + numEmployees +
                        ", Average Salary: " + avgSalary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query1 {

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = DriverManager.getConnection("jdbc:mysql://database-1.c94soiwsy7xp.us-east-1.rds.amazonaws.com:3306/employees", "admin", "password");


            String query = "SELECT d.dept_name AS department, " +
                    "(SUM(CASE WHEN e.gender = 'F' THEN s.salary ELSE 0 END) / " +
                    "SUM(CASE WHEN e.gender = 'M' THEN s.salary ELSE 0 END)) AS gender_salary_ratio " +
                    "FROM employees e " +
                    "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                    "JOIN departments d ON de.dept_no = d.dept_no " +
                    "JOIN salaries s ON e.emp_no = s.emp_no " +
                    "GROUP BY d.dept_no " +
                    "ORDER BY gender_salary_ratio DESC LIMIT 1";


            preparedStatement = connection.prepareStatement(query);


            resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {
                String department = resultSet.getString("department");
                double ratio = resultSet.getDouble("gender_salary_ratio");


                System.out.println("Department with maximum ratio of average female salaries to average male salaries:");
                System.out.println("Department: " + department);
                System.out.println("Gender Salary Ratio: " + ratio);
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

 */
import java.sql.*;

public class Query1 {
    public static String executeQuery() {
        // Database connection variables
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establishing a connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://database-1.c94soiwsy7xp.us-east-1.rds.amazonaws.com:3306/employees", "admin", "password");

            // SQL query to calculate the department with the maximum ratio of average female salaries to average male salaries
            String query = "SELECT d.dept_name AS department, " +
                    "(SUM(CASE WHEN e.gender = 'F' THEN s.salary ELSE 0 END) / " +
                    "SUM(CASE WHEN e.gender = 'M' THEN s.salary ELSE 0 END)) AS gender_salary_ratio " +
                    "FROM employees e " +
                    "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                    "JOIN departments d ON de.dept_no = d.dept_no " +
                    "JOIN salaries s ON e.emp_no = s.emp_no " +
                    "GROUP BY d.dept_no " +
                    "ORDER BY gender_salary_ratio DESC LIMIT 1";

            // Creating a prepared statement to execute the SQL query
            preparedStatement = connection.prepareStatement(query);
            // Executing the query and obtaining the result set
            resultSet = preparedStatement.executeQuery();

            // Building the result string based on the query results
            StringBuilder resultBuilder = new StringBuilder();
            if (resultSet.next()) {
                String department = resultSet.getString("department");
                double ratio = resultSet.getDouble("gender_salary_ratio");

                resultBuilder.append("Department with maximum ratio of average female salaries to average male salaries:\n");
                resultBuilder.append("Department: ").append(department).append("\n");
                resultBuilder.append("Gender Salary Ratio: ").append(ratio);
            }
            return resultBuilder.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error executing Query 1";
        } finally {
            // Closing database resources in the finally block to ensure proper cleanup
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
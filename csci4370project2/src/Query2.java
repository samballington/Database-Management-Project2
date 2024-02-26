import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query2 {

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            connection = DriverManager.getConnection("jdbc:mysql://database-1.c94soiwsy7xp.us-east-1.rds.amazonaws.com:3306/employees", "admin", "password");


            String query = "SELECT e.first_name, e.last_name, de.from_date, de.to_date " +
                    "FROM employees e " +
                    "JOIN dept_manager de ON e.emp_no = de.emp_no " +
                    "WHERE de.to_date != '9999-01-01' " + // Exclude managers with indefinite office tenure
                    "ORDER BY DATEDIFF(de.to_date, de.from_date) DESC LIMIT 1";


            preparedStatement = connection.prepareStatement(query);


            resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String fromDate = resultSet.getString("from_date");
                String toDate = resultSet.getString("to_date");


                System.out.println("Manager(s) who hold office for the longest duration:");
                System.out.println("Name: " + firstName + " " + lastName);
                System.out.println("From Date: " + fromDate);
                System.out.println("To Date: " + toDate);
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
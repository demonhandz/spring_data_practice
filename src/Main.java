import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
//        System.out.println("Password: ");
//        scanner.nextLine();

        // Connect to DB
        Properties credentials = new Properties();
        credentials.setProperty("user", "root");
        credentials.setProperty("password", "");

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/soft_uni", credentials);

        // 0 OR 1 = 1
        // 0; DROP TABLE users;
        String userInput = scanner.nextLine();
        String notPrepared = String.format("SELECT * FROM employees WHERE salary > %s", userInput);

        System.out.println(notPrepared);


        // Execute Query
        PreparedStatement preparedStatement =
            connection.prepareStatement("SELECT * FROM employees WHERE salary > ? LIMIT 10");

        preparedStatement.setDouble(1, 17000.0);
        ResultSet resultSet = preparedStatement.executeQuery();

        // Print Result
        while (resultSet.next()) {
            long id = resultSet.getLong("employee_id");
            String firstName = resultSet.getString("first_name");
            double salary = resultSet.getDouble("salary");
            Timestamp hireDate = resultSet.getTimestamp("hire_date");

            System.out.printf("%d - %s %.2f %s%n",
                    id, firstName, salary, hireDate);
        }
        connection.close();
    }
}
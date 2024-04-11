import java.sql.*;
import java.util.Scanner;

public class Diablo {
    public static void main(String[] args) throws SQLException {
        // Connect to DB
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo", "root", "");

        // Execute Query
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT u.id, u.first_name, u.last_name, COUNT(ug.game_id) " +
                "FROM users AS u " +
                "JOIN users_games AS ug ON ug.user_id = u.id " +
                "WHERE u.user_name = ?;");

        preparedStatement.setString(1, username);
        ResultSet result = preparedStatement.executeQuery();

        // Print Result
        result.next();
        Object userId = result.getObject(1);

        if (userId != null) { // Has valid user data
            System.out.printf("User: %s%n %s %s has played %d games",
                    username,
                    result.getString(1),
                    result.getString(2),
                    result.getInt(3));
        } else { //No user data
            System.out.println("No such user exists");
        }
    }
}

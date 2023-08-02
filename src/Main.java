import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/sakila";
        String username = "root";
        String password = "20_MySQL_$=0";

        try {
            // Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to the database!");

            Statement stmt = connection.createStatement();
            
            ResultSet result = stmt.executeQuery("select * from actor");
 
            // Step 5: Process the results
 
            // Condition check using hasNext() method which
            // holds true till there is single element
            // remaining in List
            while (result.next()) {
 
                // Print name an age
                System.out.println(
                    "Name: " + result.getString("first_name"));
                System.out.println(
                    "id:" + result.getString("actor_id"));
            }


            // Perform your database operations here

            connection.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

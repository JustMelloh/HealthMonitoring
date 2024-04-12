

import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

/**
* The UserDao class is responsible for handling all database operations related to the User entity 
* (e.g., creating, reading, updating, and deleting users).
* The class provides methods for creating, reading, updating, and deleting user records in the database.
* It also provides methods for retrieving user records based on their ID or email, as well as verifying user passwords.
 */
public class UserDao {

    /**
     * Represents the connection to the database.
     */
    private Connection connection;


        /**
         * Constructs a new UserDao object with the specified database connection.
         * 
         * @param connection the database connection to be used by the UserDao
         */
        public UserDao(Connection connection) {
            this.connection = connection;
        }

    /**
     * Returns the connection object used by the UserDao.
     *
     * @return the connection object
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Inserts a new user into the database.
     *
     * @param user the User object representing the user to be created
     * @return true if the user was successfully created, false otherwise
     */
    public User creatingUser(User user) {

        try {
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            String query = "INSERT INTO users (f_name, l_name, email, password, is_doctor) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, hashedPassword);
            statement.setBoolean(5, user.isDoctor());
            int updateRows = statement.executeUpdate();
            if (updateRows > 0) {
                ResultSet Rules = statement.getGeneratedKeys();
                if (Rules.next()) {
                    user.setId(Rules.getInt(1));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

        /**
         * Get the User by their designated ID, gets their User information
         * By Querying in SQL to select all from users where the ID is equal to the ID
         * submitted.
         */
    public User getUserByID(int id) {
    
        User user = null;
        String query = "SELECT * FROM users WHERE id = ?";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                user = new User(
                        results.getString("f_name"),
                        results.getString("l_name"),
                        results.getString("email"),
                        results.getString("password"),
                        results.getBoolean("is_doctor"));
            }

            /*
             * Results are gathered within the results variable and then returned back to
             * the user variable to be used.
             */
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

        /**
         * Get the User by their designated Email, gets their User information
         * By Querying in SQL to select all from users where the Email is equal to the
         * Email submitted.
         */
    public User getUserByEmail(String email) {
    
        User user = null;
        String query = "SELECT * FROM users WHERE email = ?";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, email);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                int id = results.getInt("id");
                user = new User(
                        results.getString("f_name"),
                        results.getString("l_name"),
                        results.getString("email"),
                        results.getString("password"),
                        results.getBoolean("is_doctor"));
                user.setId(id);
            }

            /*
             * Results are gathered within the results variable and then returned back to
             * the user variable to be used.
             */
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Updates the User information in the database based on the provided User
     * object.
     * 
     * @param user The User object containing the updated information.
     * @return true if the User information was successfully updated, false
     *         otherwise.
     */
    public boolean updateUser(User user) {
        boolean bool = false;
        String query = "UPDATE users SET f_name = ?, l_name = ?, email = ?, password = ?, is_doctor = ? WHERE id = ?";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setBoolean(5, user.isDoctor());
            statement.setInt(6, user.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows >= 0) {
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    /**
     * Deletes a user from the database based on their ID.
     * 
     * @param id the ID of the user to be deleted
     * @return true if the user was successfully deleted, false otherwise
     */

    public boolean deleteUser(int id) {
        /*
         * Delete the User by their designated ID, gets their User information
         * By Querying in SQL to delete the users where the ID is equal to the ID
         * submitted.
         */
        boolean bool = false;
        String query = "DELETE FROM users WHERE id = ?";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            int deletedRows = statement.executeUpdate();
            if (deletedRows >= 0) {
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

        /**
         * Verify the password of a given user, SELECTs their password from the users
         * where their Email
         * is equal to the Email submitted, then compares the hashed password with the
         * given password.
         */
    public boolean passwordVerification(String email, String password) {
    
        boolean bool = false;
        String query = "SELECT password FROM users WHERE email = ?";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, email);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                String hashedPassword = results.getString("password");
                bool = BCrypt.checkpw(password, hashedPassword);
            }

            /**
             * Results are gathered within the results variable and then returned back to
             * the user variable to be used.
             */
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }
}
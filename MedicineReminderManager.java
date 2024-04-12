
/* Import Java utilties and modules for MedicineReminderManager */

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



    /**
     * The MedicineReminderManager class represents a manager for medicine reminders.
     * It provides functionality to create and manage reminders for taking medicine.
     */
    public class MedicineReminderManager {
    private List<MedicineReminder> reminders;
    private Connection connection;
        
        /**
         * Constructs a new instance of the MedicineReminderManager class.
         * Initializes the reminders list.
         */
        public MedicineReminderManager() {
            this.reminders = new ArrayList<>();
        }
    

    /**
     * Creates a new instance of the MedicineReminderManager class.
     * 
     * @param connection the database connection to be used by the manager
     */
    public MedicineReminderManager(Connection connection) {
        this.reminders = new ArrayList<>();
        this.connection = connection;
    }

    /**
     * Adds a medicine reminder to the list of reminders and inserts it into the database.
     * 
     * @param reminder the medicine reminder to be added
     */
    public void addReminder(MedicineReminder reminder) {
        reminders.add(reminder);
        /* Creating an addReminder method to add to a reminder with a start and end date. */
        try {
            String query = "INSERT INTO medicine_reminders (u_id, med_name, dose, schedule, start_d, end_d) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, reminder.getUserId());
            statement.setString(2, reminder.getMedicineName());
            statement.setString(3, reminder.getDose());
            statement.setString(4, reminder.getSchedule());
            statement.setDate(5, Date.valueOf(reminder.getStartDate()));
            statement.setDate(6, Date.valueOf(reminder.getEndDate()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
    }
    }
    /**
     * Retrieves a list of medicine reminders for a given user.
     *
     * @param userId the ID of the user
     * @return a list of MedicineReminder objects associated with the user
     */
    public List<MedicineReminder> getUserReminder(int userId) {
        List<MedicineReminder> userReminders = new ArrayList<>();
        try {
            String query = "SELECT * FROM medicine_reminders WHERE u_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int id = results.getInt("id");
                MedicineReminder reminder = new MedicineReminder(
                    results.getInt("u_id"),
                    results.getString("med_name"),
                    results.getString("dose"),
                    results.getString("schedule"),
                    results.getString("start_d"),
                    results.getString("end_d")
                );
                userReminders.add(reminder);
                reminder.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userReminders;
    }

 
    /**
     * Retrieves a list of due medicine reminders for a given user.
     *
     * @param userId the ID of the user
     * @return a list of MedicineReminder objects representing the due reminders
     */
    public List<MedicineReminder> getDueReminders(int userId) {
        List<MedicineReminder> dueReminders = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm");

        try {
            String query = "SELECT * FROM medicine_reminders WHERE u_id = ? AND end_d >= TO_DATE(?, 'DD-MM-YYYY')";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setString(2, now.format(formatter));
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int id = results.getInt("id");
                MedicineReminder reminder = new MedicineReminder(
                    results.getInt("u_id"),
                    results.getString("med_name"),
                    results.getString("dose"),
                    results.getString("schedule"),
                    results.getString("start_d"),
                    results.getString("end_d")
                );
                dueReminders.add(reminder);
                reminder.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dueReminders;
    }


    /**
     * Updates a reminder with a start and end date.
     * 
     * @param reminder The MedicineReminder object containing the updated information.
     */
    public void reminderUpdate(MedicineReminder reminder) {
        /* updateReminder method to update a reminder with a start and end date. */
        try {
            String query = "UPDATE medicine_reminders SET med_name = ?, start_d = ?, end_d = ? WHERE u_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, reminder.getMedicineName());
            statement.setDate(2, Date.valueOf(reminder.getStartDate()));
            statement.setDate(3, Date.valueOf(reminder.getEndDate()));
            statement.setInt(4, reminder.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a medicine reminder from the database.
     *
     * @param reminder The medicine reminder to be deleted.
     */
    public void reminderDelete(MedicineReminder reminder) {
        /* deleteReminder method to delete a reminder with a start and end date. */
        try {
            String query = "DELETE FROM medicine_reminders WHERE u_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, reminder.getUserId());
            statement.executeUpdate();
            int rowsRemoved = statement.executeUpdate();
            if (rowsRemoved > 0) {
                System.out.println("Reminder Removed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
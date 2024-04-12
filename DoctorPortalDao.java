
/* Import Utilities for the DoctorPortalDao  */
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

    /**
     * The DoctorPortalDao class represents a data access object for the Doctor Portal functionality.
     * It provides methods to interact with the database for managing user and health data.
     */
    
public class DoctorPortalDao {
    private static final int doctorId = 0;
    private UserDao userDao;
    private HealthDataDao healthDataDao;



        /**
         * Constructs a new instance of the DoctorPortalDao class.
         * Initializes the UserDao and HealthDataDao objects with the database connection.
         */
        public DoctorPortalDao() {
            userDao = new UserDao(DatabaseConnection.getCon());
            healthDataDao = new HealthDataDao(DatabaseConnection.getCon());
        }
    

    public DoctorPortalDao(Connection con) {
        userDao = new UserDao(con);
        healthDataDao = new HealthDataDao(con);
    }

    public Doctor getDoctorById(int doctorId, Scanner scanner) {
        try {
            Connection con = DatabaseConnection.getCon();
            String query = "SELECT * FROM users WHERE id = ? AND is_doctor = true";

            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, doctorId);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                int id = results.getInt("id");
                String firstName = results.getString("f_name");
                String lastName = results.getString("l_name");
                String email = results.getString("email");
                String password = results.getString("password");
                String medicalLicenseNumber = results.getString("medical_license_number");
                String specialization = results.getString("specialization");
                boolean isDoctor = results.getBoolean("is_doctor");

                /* Check for any empty fields */
                /*
                 * /* If the @param medicalLicenseNumber and @param specialization are NULL
                 * then the user will be prompted to enter the following details to complete
                 * their profile.
                 */
                if (medicalLicenseNumber == null || specialization == null) {
                    System.out.println("Please enter the following details to complete your profile:");
                    System.out.print("Medical License Number: ");
                    medicalLicenseNumber = scanner.nextLine();
                    System.out.print("Specialization: ");
                    specialization = scanner.nextLine();

                    /* Update the doctor's profile */
                    String updateQuery = "UPDATE users SET medical_license_number = ?, specialization = ? WHERE id = ?";
                    PreparedStatement updateStatement = con.prepareStatement(updateQuery);
                    updateStatement.setString(1, medicalLicenseNumber);
                    updateStatement.setString(2, specialization);
                    updateStatement.setInt(3, doctorId);
                    updateStatement.executeUpdate();
                }

                Doctor doctor = new Doctor(id, firstName, lastName, email, password, isDoctor, medicalLicenseNumber,
                        specialization);
                doctor.setId(doctorId);
                return doctor;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a list of patients associated with a specific doctor ID.
     *
     * @param doctorId the ID of the doctor
     * @return a list of User objects representing the patients
     */
    /**
     * Retrieves a list of patients associated with a specific doctor.
     *
     * @param doctorId the ID of the doctor
     * @return a list of User objects representing the patients
     */
    public List<User> getPatientsByDoctorId(int doctorId) {
        List<User> patientList = new ArrayList<>();
        /*
         * Get the list of patients that the doctor currently has based on the doctorID
         */
        try {
            /**
             * The SQL query to select all users associated with a specific doctor.
             */
            String query = "SELECT u.* FROM users u JOIN doctor_patient dp ON u.id = dp.patient_id WHERE dp.doctor_id = ?";

            PreparedStatement statement = userDao.getConnection().prepareStatement(query);
            statement.setInt(1, doctorId);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                String firstName = results.getString("f_name");
                String lastName = results.getString("l_name");
                String email = results.getString("email");
                String password = results.getString("password");
                boolean isDoctor = results.getBoolean("is_doctor");
                User patient = new User(firstName, lastName, email, password, isDoctor);
                patientList.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patientList;
    }

    /**
     * Retrieves a list of health data for a given patient ID.
     *
     * @param patientId the ID of the patient
     * @return a list of HealthData objects associated with the patient ID
     */
    public List<HealthData> getHealthDataByPatientId(int patientId) {
        return healthDataDao.getHealthDataByPatientId(patientId);
    }

    /**
     * Checks if a given doctor ID is valid.
     *
     * @param doctorId the ID of the doctor to check
     * @return true if the doctor ID is valid, false otherwise
     */
    public boolean isDoctorIdValid(int doctorId) {
        boolean isValid = false;
        try {
            Connection con = DatabaseConnection.getCon();
            String query = "SELECT COUNT(*) FROM users WHERE id = ? AND is_doctor = 'true'";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, doctorId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                isValid = count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    /**
     * Adds a doctor to a patient in the database.
     *
     * @param doctorId  the ID of the doctor to be assigned to the patient
     * @param patientId the ID of the patient to whom the doctor is being assigned
     * @return true if the doctor is successfully assigned to the patient, false
     *         otherwise
     */
    public boolean addDoctortoPatient(int doctorId, int patientId) {
        System.out
                .println("DEBUG: addDoctorToPatient called with doctor ID: " + doctorId + ", patient ID: " + patientId);
        if (!isDoctorIdValid(doctorId)) {
            System.out.println("Invalid doctor ID.");
            return false;
        }
        try {
            Connection con = DatabaseConnection.getCon();
            String query = "INSERT INTO doctor_patient (doctor_id, patient_id) VALUES (?, ?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, doctorId);
            statement.setInt(2, patientId);
            int insertRows = statement.executeUpdate();
            if (insertRows > 0) {
                System.out.println("Doctor assigned to patient successfully.");
                return true;
            } else {
                System.out.println("Failed to assign doctor to patient.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* Remove a patient from Doctor */

    /**
     * Removes a patient from a doctor's list of patients.
     *
     * @param doctorId  the ID of the doctor
     * @param patientId the ID of the patient
     * @return true if the patient was successfully removed, false otherwise
     */
    public boolean removePatientFromDoctor(int doctorId, int patientId) {
        try {
            Connection con = DatabaseConnection.getCon();
            String query = "DELETE FROM doctor_patient WHERE doctor_id = ? AND patient_id = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, doctorId);
            statement.setInt(2, patientId);
            int deleteRows = statement.executeUpdate();
            if (deleteRows > 0) {
                System.out.println("Patient removed from doctor successfully.");
                return true;
            } else {
                System.out.println("Failed to remove patient from doctor.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a list of medicine reminders for a specific doctor.
     *
     * @param doctorId the ID of the doctor
     * @return a list of MedicineReminder objects representing the doctor's
     *         reminders
     */
    public List<MedicineReminder> getDoctorReminders(int doctorId) {
        List<MedicineReminder> doctorReminders = new ArrayList<>();
        try {
            String query = "SELECT * FROM medicine_reminders WHERE u_id IN (SELECT patient_id FROM doctor_patient WHERE doctor_id = ?)";
            PreparedStatement statement = userDao.getConnection().prepareStatement(query);
            statement.setInt(1, doctorId);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int id = results.getInt("id");
                MedicineReminder reminder = new MedicineReminder(
                        results.getInt("u_id"),
                        results.getString("med_name"),
                        results.getString("dose"),
                        results.getString("schedule"),
                        results.getString("start_d"),
                        results.getString("end_d"));
                doctorReminders.add(reminder);
                reminder.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorReminders; // Add return statement
    }

    /**
     * Retrieves a list of medicine reminders that are due for a specific doctor.
     *
     * @param dueReminders2 The list of medicine reminders to be replaced with the
     *                      doctorId.
     * @return The list of medicine reminders that are due for the specified doctor.
     */
    public List<MedicineReminder> getDueReminders(List<MedicineReminder> dueReminders2) {
        List<MedicineReminder> dueReminders = new ArrayList<>();
        try {
            String query = "SELECT * FROM medicine_reminders WHERE u_id IN (SELECT patient_id FROM doctor_patient WHERE doctor_id = ?) AND end_d >= CURRENT_DATE";
            PreparedStatement statement = userDao.getConnection().prepareStatement(query);
            statement.setInt(1, doctorId); // Replace dueReminders2 with doctorId
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int id = results.getInt("id");
                MedicineReminder reminder = new MedicineReminder(
                        results.getInt("u_id"),
                        results.getString("med_name"),
                        results.getString("dose"),
                        results.getString("schedule"),
                        results.getString("start_d"),
                        results.getString("end_d"));
                dueReminders.add(reminder);
                reminder.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dueReminders;
    }
}

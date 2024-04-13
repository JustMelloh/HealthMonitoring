
// import com.DataBaseConnection;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;

/** Represents the Main method in which the HealthMonitoring app functions for the User and Doctor */
public class HealthMonitoringApp {

    private static User loggedUser;

    private static UserDao userDao = new UserDao(DatabaseConnection.getCon());
    private static HealthDataDao healthDataDao = new HealthDataDao(DatabaseConnection.getCon());
    private static MedicineReminderManager reminderDao = new MedicineReminderManager(DatabaseConnection.getCon());
    private static DoctorPortalDao doctorPortalDao = new DoctorPortalDao(DatabaseConnection.getCon());
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Test the following functionalities within the Main Application
     * 1. Register a new user
     * 2. Log in the user
     * 3. Add health data
     * 4. Generate recommendations
     * 5. Add a medicine reminder
     * 6. Get reminders for a specific user
     * 7. Get due reminders for a specific user
     * 8. test doctor portal
     */
    public static void main(String[] args) {
        mainMenu();
    }

    /**
     * Registers a new user by collecting their information and creating a user
     * object in the database.
     * Prompts the user to enter their first name, last name, email, password, and
     * whether they are a doctor.
     * Creates a User object with the provided information and calls the
     * `creatingUser` method of the `userDao` object to store the user in the
     * database.
     * Prints a success message if the user is created successfully, or a failure
     * message if the user creation fails.
     */

    /* Create a Method to display Menu */

    public static void mainMenu() {
        boolean close = false;
        while (!close) {

            if (loggedUser != null) {
                System.out.println("Welcome, " + loggedUser.getFirstName() + " " + loggedUser.getLastName());

            } else {
                System.out.println("Welcome to your Health Monitoring System");
            }

            System.out.println("1. User Registration");
            System.out.println("2. User Login");
            System.out.println("3. Add Data");
            System.out.println("4. Generate a Recommendation");
            System.out.println("5. Add a Medicine Reminder");
            System.out.println("6. View User Reminders");
            System.out.println("7. View Due Reminders");
            System.out.println("8. Doctor Portal");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            int selection = scanner.nextInt();
            scanner.nextLine();

            switch (selection) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser(scanner, null, null);
                    break;
                case 3:
                    healthDataAdd();
                    break;
                case 4:
                    generateRecommendations();
                    break;
                case 5:
                    addMedicineReminder();
                    break;
                case 6:
                    getUserReminders();
                    break;
                case 7:
                    getRemindersDueForUser();
                    break;
                case 8:
                    if (loggedUser.isDoctor()) {
                        DoctorPortal();
                    } else {
                        System.out.println("Only doctors can access the Doctor Portal.");
                    }
                    break;
                case 9:
                    System.out.println("Thanks for using the Health Monitoring App, Have a good day!");
                    close = true;
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }

        }
    }

    /**
     * Registers a new user by collecting their personal information and creating a user object.
     * The user object is then stored in the database.
     * 
     * This method prompts the user to enter their first name, last name, email, password, and whether they are a doctor.
     * It creates a User object with the provided information and calls the creatingUser method of the userDao object to store the user in the database.
     * 
     * If the user is successfully created, it prints "User created successfully".
     * If the user creation fails, it prints "User creation failed".
     */
    public static void registerUser() {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter a password for account: ");
        String password = scanner.nextLine();
        System.out.print("Are you a doctor? (Yes or No)");
        String isDoctorEntry = scanner.nextLine();
        boolean isDoctor = isDoctorEntry.equalsIgnoreCase("yes");

        /* Create the user Object for the DB */
        User user = new User(firstName, lastName, email, password, isDoctor);

        User newUser = userDao.creatingUser(user);

        if (newUser != null) {
            System.out.println("User created successfully.");
        } else {
            System.out.println("User creation failed.");
        }
    }

    /**
     * Authenticates a user by prompting for their email and password.
     * 
     * @param scanner  the Scanner object used for user input
     * @param email    the email entered by the user
     * @param password the password entered by the user
     * @return true if the user is successfully authenticated, false otherwise
     */
    public static boolean loginUser(Scanner scanner, String email, String password) {
        while (true) {
            System.out.print("Enter your email: ");
            email = scanner.nextLine();
            System.out.print("Enter your password: ");
            password = scanner.nextLine();
            if (email.isEmpty() || password.isEmpty()) {
                System.out.println("Email and password cannot be empty. Please try again.");
            } else {
                break;
            }
        }

        User user = userDao.getUserByEmail(email);

        if (user != null) {
            String passwordHash = user.getPassword();

            if (BCrypt.checkpw(password, passwordHash)) {
                loggedUser = user;
                return true;
            } else {
                System.out.println("Incorrect Credentials, please try again.");
            }
        } else {
            System.out.println("User not found. Please register to continue.");
        }

        return false;

    }

    /* Create an HealthDataAdd method */
    /**
     * Adds health data for the logged-in user.
     * If no user is logged in, a message is displayed prompting the user to log in.
     * Prompts the user to enter the date, weight, height, steps, and heart rate.
     * Creates a new instance of the HealthData class with the entered data.
     * Calls the createHealthData method of the healthDataDao to store the health
     * data.
     * Displays a success message if the health data is added successfully, or a
     * failure message otherwise.
     */
    public static void healthDataAdd() {
        if (loggedUser == null) {
            System.out.println("Please log in to add health data.");
            return;
        }

        System.out.print("Health Data Entry");

        System.out.print("Enter date (dd-mm-yyyy): ");
        String date = scanner.nextLine();

        System.out.print("Enter your weight (KG): ");
        double weight = scanner.nextDouble();

        System.out.print("Enter your height (CM): ");
        double height = scanner.nextDouble();

        System.out.print("Enter your steps: ");
        int steps = scanner.nextInt();

        System.out.print("Enter your heart rate: ");
        int heartRate = scanner.nextInt();

        int userId = loggedUser.getId();

        HealthData healthData = new HealthData(userId, weight, height, steps, heartRate, date);

        boolean success = healthDataDao.createHealthData(healthData);

        if (success) {
            System.out.println("Health data added successfully.");
        } else {
            System.out.println("Failed to add health data.");
        }
    }

    /**
     * Generates recommendations based on the health data of the logged-in user.
     * If no user is logged in, a message is displayed prompting the user to log in.
     * Retrieves the health data for the logged-in user using the
     * getHealthDataByUserId method of the healthDataDao.
     * Creates an instance of the RecommendationSystem class and calls the
     * generateRecommendations method to get the recommendations.
     * Displays the recommendations to the user.
     */

    public static void generateRecommendations() {
        if (loggedUser == null) {
            System.out.println("Please log in to generate recommendations.");
            return;
        }

        List<HealthData> healthDataList = healthDataDao.getHealthDataByUserId(loggedUser.getId());

        if (healthDataList == null || healthDataList.isEmpty()) {
            System.out.println("No health data found for the user.");
            return;
        }

        System.out.println("Health data list size: " + healthDataList.size()); // New print statement

        RecommendationSystem recommendationSystem = new RecommendationSystem();

        for (HealthData healthData : healthDataList) {
            List<String> recommendations = recommendationSystem.generateRecommendations(healthData);

            System.out.println("Recommendations list size: " + recommendations.size()); // New print statement

            System.out
                    .println("Recommendations for " + loggedUser.getFirstName() + " " + loggedUser.getLastName() + ":");
            for (String recommendation : recommendations) {
                System.out.println(recommendation);
            }
        }
    }

    /* Create a medicine reminder for the loggedUser */

    /**
     * Adds a medicine reminder for the logged-in user.
     * If no user is logged in, a message is displayed to prompt the user to log in.
     * Prompts the user to enter the medicine name, dosage, schedule, start date,
     * and end date.
     * Creates a new MedicineReminder object with the entered information and the
     * user's ID.
     * Adds the reminder to the reminderDao.
     */
    public static void addMedicineReminder() {
        if (loggedUser == null) {
            System.out.println("Please log in to add a medicine reminder.");
            return;
        }

        System.out.print("-- Medicine Reminder Entry --");

        System.out.print("Enter medicine name: ");
        String medicineName = scanner.nextLine();

        System.out.print("Enter dosage in MG: ");
        String dosage = scanner.nextLine();

        System.out.println("Enter schedule (e.g., 2 times a day): ");
        String schedule = scanner.nextLine();

        System.out.print("Enter start date (dd-mm-yyyy): ");
        String startDate = scanner.nextLine();

        System.out.print("Enter end date (dd-mm-yyyy): ");
        String endDate = scanner.nextLine();

        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate date = LocalDate.parse(startDate, inputFormat);
        String formattedStartDate = outputFormat.format(date);

        date = LocalDate.parse(endDate, inputFormat);
        String formattedEndDate = outputFormat.format(date);

        int userId = loggedUser.getId();
        MedicineReminder reminder = new MedicineReminder(userId, medicineName, dosage, schedule, formattedStartDate, formattedEndDate);

        reminderDao.addReminder(reminder, userId);

    }


    /**
     * Retrieves and displays the reminders for the logged-in user.
     * If no user is logged in, a message is printed to prompt the user to log in.
     * If there are no reminders for the user, a message is printed indicating that no reminders were found.
     * Otherwise, the reminders for the user are printed.
     */
    public static void getUserReminders() {
        if (loggedUser == null) {
            System.out.println("Please log in to view your reminders.");
            return;
        }

        System.out.print("User Reminders");

        List<MedicineReminder> userReminders = reminderDao.getUserReminder(loggedUser.getId());

        if (userReminders.isEmpty()) {
            System.out.println("No reminders found for this user.");
        } else {
            System.out.println("Reminders for User ID: " + loggedUser.getId());
            for (MedicineReminder reminder : userReminders) {
                System.out.println(reminder);
            }
        }
    }

    /**
     * Retrieves and displays the reminders that are due for the logged-in user.
     * If no user is logged in, a message will be displayed prompting the user to log in.
     * 
     * @return void
     */
    public static void getRemindersDueForUser() {
        if (loggedUser == null) {
            System.out.println("Please log in to view your reminders.");
            return;
        }

        System.out.println("-- Reminders Due --");

        System.out.println("Fetching reminders due for User ID: " + loggedUser.getId());
        List<MedicineReminder> dueReminders = reminderDao.getDueReminders(loggedUser.getId());

        if (dueReminders.isEmpty()) {
            System.out.println("No reminders are due.");
        } else {
            System.out.println("Due Reminders for User ID: " + loggedUser.getId());
            for (MedicineReminder reminder : dueReminders) {
                System.out.println(reminder);
            }
        }
    }

    /**
     * This method represents the Doctor Portal, where doctors can perform various actions related to patient management and health data.
     * It displays a menu of options and allows the doctor to choose an action based on their selection.
     * The doctor's ID is validated before allowing access to the portal.
     * 
     * @throws InputMismatchException if the user enters an invalid input for the menu selection.
     */
    public static void DoctorPortal() {
        
        int doctorId = loggedUser.getId();
        if (doctorPortalDao.isDoctorIdValid(doctorId)) {
            System.out.println("Doctor ID is valid.");
        } else {
            System.out.println("Doctor ID is invalid.");
            return;
        }

        boolean close = false;

        while (!close) {
            System.out.println("Doctor Portal");
            System.out.println("1. Fetch Doctor by their ID");
            System.out.println("2. Fetch Patients assigned to a specific Doctor");
            System.out.println("3. Fetch Health Data for a specific Patient");
            System.out.println("4. Add medicine reminder for a specific Patient");
            System.out.println("5. Fetch medicine reminders for all of your patients");
            System.out.println("6. Fetch due medicine reminders for all of your patients");
            System.out.println("7. Assign patient to Doctor");
            System.out.println("8. Remove patient from Doctor");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            int selection = scanner.nextInt();
            scanner.nextLine();

            switch (selection) {
                case 1:
                    fetchDoctorInfo();
                    break;
                case 2:

                    if (doctorPortalDao.isDoctorIdValid(doctorId)) {
                        fetchPatientsByDoctorId(doctorId);
                    } else {
                        System.out.println("Only doctors can view a list of patients.");
                    }
                    break;
                case 3:
                    if (doctorPortalDao.isDoctorIdValid(doctorId)) {
                        fetchHealthDataForPatient();
                    } else {
                        System.out.println("Only doctors can view health data for patients.");
                    }
                    break;
                case 4:
                    if (doctorPortalDao.isDoctorIdValid(doctorId)) {
                        assignMedicineReminderToPatient();
                    } else {
                        System.out.println("Only doctors can add medicine reminders.");
                    }
                    break;
                case 5:
                    if(doctorPortalDao.isDoctorIdValid(doctorId)) {
                        fetchRemindersForAllPatients(doctorId);
                    } else {
                        System.out.println("Only doctors can view reminders for all patients.");
                    }
                    break;
                case 6:
                    if(doctorPortalDao.isDoctorIdValid(doctorId)) {
                        getRemindersDueForAllPatients();
                    } else {
                        System.out.println("Only doctors can view due reminders for all patients.");
                    }
                    break;
                case 7:
                    if (doctorPortalDao.isDoctorIdValid(doctorId)) {
                        assignPatientToDoctor(doctorId);

                    } else {
                        System.out.println("Only doctors can assign patients.");
                    }
                    break;
                case 8:
                    if (doctorPortalDao.isDoctorIdValid(doctorId)) {
                        removePatientFromDoctor(doctorId);
                        ;
                    } else {
                        System.out.println("Only doctors can remove patients.");
                    }
                    break;
                case 9:
                    close = true;
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }
    // Add code to Fetch the doctor by ID

    /**
     * Fetches and displays information about a doctor based on the provided ID.
     */
    public static void fetchDoctorInfo() {
        System.out.println("Enter the ID for the Doctor: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine();

        Doctor doctor = doctorPortalDao.getDoctorById(doctorId, scanner);
        if (doctor != null) {
            System.out.println("Doctor Information");
            System.out.println("Name: " + doctor.getFirstName() + " " + doctor.getLastName());
            System.out.println("Email: " + doctor.getEmail());
        } else {
            System.out.println("Doctor not found.");
        }
    }

    

    /**
     * Fetches the list of patients assigned to a doctor based on the doctor's ID.
     *
     * @param doctorId the ID of the doctor
     */
    public static void fetchPatientsByDoctorId(int doctorId) {
        List<User> patients = doctorPortalDao.getPatientsByDoctorId(doctorId);

        if (patients.isEmpty()) {
            System.out.println("No patients found for this doctor.");
        } else {
            System.out.println("Patients assigned to Doctor ID: " + doctorId);
            for (User patient : patients) {
                System.out.println(patient);
            }
        }
    }

    

    /**
     * Fetches health data for a specific patient.
     * 
     * This method prompts the user to enter the ID of the patient and retrieves the health data
     * associated with that patient from the doctor portal DAO. If no health data is found for the
     * patient, a message is displayed indicating that no data is available. Otherwise, the health
     * data is displayed on the console.
     */
    public static void fetchHealthDataForPatient() {

        System.out.println("Enter the ID for the patient: ");
        int patientId = scanner.nextInt();
        scanner.nextLine();

        List<HealthData> healthDataList = doctorPortalDao.getHealthDataByPatientId(patientId);

        if (healthDataList.isEmpty()) {
            System.out.println("No health data found for this patient.");
        } else {
            System.out.println("Health Data for User ID: " + patientId);
            for (HealthData healthData : healthDataList) {
                System.out.println(healthData);
            }
        }

    }
    

    /**
     * Assigns a patient to a doctor.
     * 
     * @param doctorId the ID of the doctor to assign the patient to
     */
    public static void assignPatientToDoctor(int doctorId) {
        if (doctorPortalDao.isDoctorIdValid(doctorId)) {

            System.out.println("Enter the ID of the patient to assign: ");
            int patientId = scanner.nextInt();
            scanner.nextLine();

            doctorPortalDao.addDoctortoPatient(doctorId, patientId);

        }
    }

   

    /**
     * Removes a patient from a doctor's list of patients.
     *
     * @param doctorId the ID of the doctor
     */
    public static void removePatientFromDoctor(int doctorId) {
        if (doctorPortalDao.isDoctorIdValid(doctorId)) {

            System.out.println("Enter the ID of the patient to remove: ");
            int patientId = scanner.nextInt();
            scanner.nextLine();

            doctorPortalDao.removePatientFromDoctor(doctorId, patientId);

        }
    }



    /**
     * Fetches reminders for all patients assigned to a specific doctor.
     *
     * @param doctorId the ID of the doctor
     */
    public static void fetchRemindersForAllPatients(int doctorId) {
        if (doctorPortalDao.isDoctorIdValid(doctorId)) {
            List<MedicineReminder> reminders = doctorPortalDao.getDoctorReminders(doctorId);

            if (reminders.isEmpty()) {
                System.out.println("No reminders found for any patients.");
            } else {
                System.out.println("Reminders for all patients assigned to Doctor ID: " + doctorId);
                for (MedicineReminder reminder : reminders) {
                    System.out.println(reminder);
                }
            }
        }
    }

    
    /**
     * Retrieves and displays the reminders that are due for all patients assigned to the logged-in doctor.
     * If there are no due reminders, a message indicating that will be printed.
     * Otherwise, the due reminders will be printed to the console.
     */
    public static void getRemindersDueForAllPatients() {
        List<MedicineReminder> dueReminders = new ArrayList<>(); // Initialize the variable with an empty list
        dueReminders = doctorPortalDao.getDueReminders(dueReminders);

        if (dueReminders.isEmpty()) {
            System.out.println("No reminders are due for any patients.");
        } else {
            System.out.println("Due Reminders for all patients assigned to Doctor ID: " + loggedUser.getId());
            for (MedicineReminder reminder : dueReminders) {
                System.out.println(reminder);
            }
        }
    }

    /* Allow a doctor to select a patient within the Doctor Portal to add medicine to */

    public static void assignMedicineReminderToPatient() {
    
    
        System.out.println("-- Medicine Reminder Entry --");

        System.out.println("Enter the ID of the patient to assign a medicine reminder: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter medicine name: ");
        String medicineName = scanner.nextLine();

        System.out.print("Enter dosage in MG: ");
        String dosage = scanner.nextLine();

        System.out.println("Enter schedule (e.g., 2 times a day): ");
        String schedule = scanner.nextLine();

        System.out.print("Enter start date (dd-mm-yyyy): ");
        String startDate = scanner.nextLine();

        System.out.print("Enter end date (dd-mm-yyyy): ");
        String endDate = scanner.nextLine();

        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate date = LocalDate.parse(startDate, inputFormat);
        String formattedStartDate = outputFormat.format(date);

        date = LocalDate.parse(endDate, inputFormat);
        String formattedEndDate = outputFormat.format(date);

    
        
        MedicineReminder reminder = new MedicineReminder(userId, medicineName, dosage, schedule, formattedStartDate, formattedEndDate);
    
        MedicineReminderManager reminderManager = new MedicineReminderManager();
        reminderManager.addReminder(reminder, userId);
    }
    
}

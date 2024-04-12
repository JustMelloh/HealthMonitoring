

/**
 * The MedicineReminder class represents a medicine reminder for a user.
 * It stores information such as the user ID, medicine name, dosage, schedule, start date, and end date.
 */
public class MedicineReminder {
    private int id;
    private int userId;
    private String medicineName;
    private String dosage;
    private String schedule;
    private String startDate;
    private String endDate;

    /* Constructor for MedicineReminder */

    /**
     * Represents a medicine reminder for a user.
     */
        /**
         * Constructs a new MedicineReminder object with the specified parameters.
         *
         * @param userId       the ID of the user
         * @param medicineName the name of the medicine
         * @param dosage       the dosage of the medicine
         * @param schedule     the schedule for taking the medicine
         * @param startDate    the start date of the reminder
         * @param endDate      the end date of the reminder
         */
        public MedicineReminder(int userId, String medicineName, String dosage, String schedule, String startDate, String endDate) {
            this.userId = userId;
            this.medicineName = medicineName;
            this.dosage = dosage;
            this.schedule = schedule;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    


    /**
     * Returns the ID of the medicine reminder.
     *
     * @return the ID of the medicine reminder
     */
    public int getId(){
        return id;
    }

    /**
     * Sets the ID of the medicine reminder.
     *
     * @param id the ID of the medicine reminder
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Returns the user ID associated with the medicine reminder.
     *
     * @return the user ID associated with the medicine reminder
     */
    public int getUserId(){
        return userId;
    }

    /**
     * Sets the user ID associated with the medicine reminder.
     *
     * @param userId the user ID associated with the medicine reminder
     */
    public void setUserId(int userId){
        this.userId = userId;
    }

    /**
     * Returns the name of the medicine.
     *
     * @return the name of the medicine
     */
    public String getMedicineName(){
        return medicineName;
    }

    /**
     * Sets the name of the medicine.
     *
     * @param medicineName the name of the medicine
     */
    public void setMedicineName(String medicineName){
        this.medicineName = medicineName;
    }

    /**
     * Returns the dosage of the medicine.
     *
     * @return the dosage of the medicine
     */
    public String getDose(){
        return dosage;
    }

    /**
     * Sets the dosage of the medicine.
     *
     * @param dosage the dosage of the medicine
     */
    public void setDose(String dosage){
        this.dosage = dosage;
    }

    /**
     * Returns the schedule of the medicine reminder.
     *
     * @return the schedule of the medicine reminder
     */
    public String getSchedule(){
        return schedule;
    }

    /**
     * Sets the schedule of the medicine reminder.
     *
     * @param schedule the schedule of the medicine reminder
     */
    public void setSchedule(String schedule){
        this.schedule = schedule;
    }

    /**
     * Returns the start date of the medicine reminder.
     *
     * @return the start date of the medicine reminder
     */
    public String getStartDate(){
        return startDate;
    }

    /**
     * Sets the start date of the medicine reminder.
     *
     * @param startDate the start date of the medicine reminder
     */
    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    /**
     * Returns the end date of the medicine reminder.
     *
     * @return the end date of the medicine reminder
     */
    public String getEndDate(){
        return endDate;
    }

    /**
     * Sets the end date of the medicine reminder.
     *
     * @param endDate the end date of the medicine reminder
     */
    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    /* Create @Override for toString method for Medicine Reminders */
    @Override
    public String toString() {
        return "MedicineReminder{" +
                "id=" + id +
                ", userId=" + userId +
                ", medicineName='" + medicineName + '\'' +
                ", dosage='" + dosage + '\'' +
                ", schedule='" + schedule + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}

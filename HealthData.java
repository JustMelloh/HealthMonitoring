/* Imprt Utilities/Modules for Class */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Represents health data for a user.
 */
public class HealthData {
    private int id;
    private int userId;
    private double weight;
    private double height;
    private int steps;
    private int heartRate;
    private String date;

    // Constructor, getters, and setters

    /* Constructors */

    /**
     * Constructs a new instance of the HealthData class with the specified parameters.
     *
     * @param userId    The ID of the user.
     * @param weight    The weight of the user.
     * @param height    The height of the user.
     * @param steps     The number of steps taken by the user.
     * @param heartRate The heart rate of the user.
     * @param date      The date of the health data.
     */
    public HealthData(int userId, double weight, double height, int steps, int heartRate, String date) {
        this.userId = userId;
        this.weight = weight;
        this.height = height;
        this.steps = steps;
        this.heartRate = heartRate;
        this.date = date;
    }

    /* Getters and Setters */

    /**
     * Gets the ID of the health data.
     *
     * @return The ID of the health data.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the health data.
     *
     * @param id The ID of the health data.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the ID of the user.
     *
     * @return The ID of the user.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user.
     *
     * @param userId The ID of the user.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the weight of the user.
     *
     * @return The weight of the user.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the user.
     *
     * @param weight The weight of the user.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Gets the height of the user.
     *
     * @return The height of the user.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height of the user.
     *
     * @param height The height of the user.
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Gets the number of steps taken by the user.
     *
     * @return The number of steps taken by the user.
     */
    public int getSteps() {
        return steps;
    }

    /**
     * Sets the number of steps taken by the user.
     *
     * @param steps The number of steps taken by the user.
     */
    public void setSteps(int steps) {
        this.steps = steps;
    }

    /**
     * Gets the heart rate of the user.
     *
     * @return The heart rate of the user.
     */
    public int getHeartRate() {
        return heartRate;
    }

    /**
     * Sets the heart rate of the user.
     *
     * @param heartRate The heart rate of the user.
     */
    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    /**
     * Returns the formatted date string in the format "dd-MM-yyyy".
     *
     * @return The formatted date string.
     */
    public String getDate() {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = formatDate.parse(this.date);
            return formatDate.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sets the date of the health data.
     *
     * @param date The date of the health data.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns a string representation of the HealthData object.
     *
     * @return A string representation of the HealthData object.
     */
    @Override
    public String toString() {
        return "HealthData{" +
                "userId=" + userId +
                ", weight=" + weight +
                ", height=" + height +
                ", steps=" + steps +
                ", heartRate=" + heartRate +
                ", date='" + date + '\'' +
                '}';
    }
}

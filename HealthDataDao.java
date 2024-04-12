/* Import utilities for HealthDataDao */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/** Represents the data access object for Health Data */
public class HealthDataDao {

  /* Connection setup */

  private Connection con;

  public HealthDataDao(Connection connection) {
    this.con = connection;
  }

  /**
   * Inserts a new health data record into the database.
   *
   * @param healthData the HealthData object containing the data to be inserted
   * @return true if the insertion is successful, false otherwise
   */
  public boolean createHealthData(HealthData healthData) {
    try {
      String query = "INSERT INTO health_data (u_id, weight, height, steps, heart_rate, date) VALUES (?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = con.prepareStatement(query);
      statement.setInt(1, healthData.getUserId());
      statement.setDouble(2, healthData.getWeight());
      statement.setDouble(3, healthData.getHeight());
      statement.setInt(4, healthData.getSteps());
      statement.setInt(5, healthData.getHeartRate());

      SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
      Date utilDate = sdf.parse(healthData.getDate());
      java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
      statement.setDate(6, sqlDate);

      int rows = statement.executeUpdate();
      return rows > 0;

    } catch (SQLException | ParseException e) {
      e.printStackTrace();
      return false;
    }
  }

  /* Find data by patient ID */

  /**
   * Retrieves a list of health data records associated with a specific patient
   * ID.
   *
   * @param patientId the ID of the patient
   * @return a list of HealthData objects representing the health data records
   */

  public List<HealthData> getHealthDataByPatientId(int patientId) {
    List<HealthData> healthDataList = new ArrayList<>();
    try {
      String query = "SELECT * FROM health_data WHERE u_id = ?";
      PreparedStatement statement = con.prepareStatement(query);
      statement.setInt(1, patientId);
      ResultSet res = statement.executeQuery();
      while (res.next()) {
        int userId = res.getInt("u_id");
        double weight = res.getDouble("weight");
        double height = res.getDouble("height");
        int steps = res.getInt("steps");
        int heartRate = res.getInt("heart_rate");
        String date = res.getString("date");
        healthDataList.add(new HealthData(userId, weight, height, steps, heartRate, date));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return healthDataList;
  }

  /* Get a List of <HealthData> based on the User Id */

  public List<HealthData> getHealthDataByUserId(int userId) {
    /* Create new array list */

    List<HealthData> healthDataList = new ArrayList<>();

    try {
      String query = "SELECT * FROM health_data WHERE u_id = ?"; /*
      * Query the Database for Health data based on the User
      * ID
      */
      PreparedStatement statement = con.prepareStatement(query); /* Prepare the SQL statement */
      statement.setInt(1, userId);
      ResultSet res = statement.executeQuery(); /* Execute the SQL statement */
      while (res.next()) {
        double weight = res.getDouble("weight");
        double height = res.getDouble("height");
        int steps = res.getInt("steps");
        int heartRate = res.getInt("heart_rate");
        String date = res.getString("date");
        HealthData healthData = new HealthData(userId, weight, height, steps, heartRate, date);
        healthDataList.add(healthData);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return healthDataList;
  }

  /* Allow for updating of Healthdata */

  /**
   * Updates an existing health data record in the database.
   *
   * @param healthData the HealthData object containing the updated data
   * @return true if the update is successful, false otherwise
   */

  public boolean updateHealthData(HealthData healthData) {
    try {
      String query = "UPDATE health_data SET weight = ?, height = ?, steps = ?, heart_rate = ?, date = ? WHERE id = ?";
      PreparedStatement statement = con.prepareStatement(query);
      statement.setDouble(1, healthData.getWeight());
      statement.setDouble(2, healthData.getHeight());
      statement.setInt(3, healthData.getSteps());
      statement.setInt(4, healthData.getHeartRate());
      statement.setString(5, healthData.getDate());
      statement.setInt(6, healthData.getId());
      statement.executeUpdate(); /* Execute the UPDATE to the DB */

      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  /* Allow for deletion of HealthData */

  /**
   * Deletes a health data record from the database based on the specified
   * healthDataId.
   *
   * @param healthDataId the ID of the health data record to be deleted
   * @return true if the deletion is successful, false otherwise
   */
  public boolean healthDataDelete(int healthDataId) {
    try {
      String query = "DELETE FROM health_data WHERE id = ?";
      PreparedStatement statement = con.prepareStatement(query);
      statement.setInt(1, healthDataId);
      statement.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }


    
  }

}

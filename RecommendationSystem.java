/* Import utilities and Modules for the RecommendationSystem Class*/

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The RecommendationSystem class provides methods to generate recommendations
 * based on health data,
 * and to add or update recommendations in a database.
 */
public class RecommendationSystem {
        private static final int MIN_HEART_RATE = 50;
        private static final int MAX_HEART_RATE = 200;
        private static final int MIN_STEPS = 3000;

        /**
         * Generates recommendations based on the provided health data.
         *
         * @param healthData the health data used to generate recommendations
         * @return a list of recommendations based on the health data
         */
        public List<String> generateRecommendations(HealthData healthData) {
                List<String> recommendations = new ArrayList<>();

                // Analyze heart rate
                int heartRate = healthData.getHeartRate();

                if (heartRate < MIN_HEART_RATE) {
                        recommendations.add("Your heart rate is lower than the recommended range. " +
                                        "Consider increasing your physical activity to improve your cardiovascular health.");
                } else if (heartRate > MAX_HEART_RATE) {
                        recommendations.add("Your heart rate is higher than the recommended range. " +
                                        "Consider reducing stress and getting more rest to improve your cardiovascular health.");
                } else {
                        recommendations.add("Your heart rate is within the recommended range. " +
                                        "Keep up the good work to maintain your cardiovascular health.");
                }

                int steps = healthData.getSteps();

                if (steps < MIN_STEPS) {
                        recommendations.add("You're not reaching the recommended daily step count. " +
                                        "Try to incorporate more walking or other physical activities into your daily routine.");
                } else if (steps >= MIN_STEPS && steps < 5000) {
                        recommendations.add("You're close to reaching the recommended daily step count. " +
                                        "Keep up the good work and try to increase your steps further for better health.");
                } else {
                        recommendations.add("Congratulations! You've reached the recommended daily step count. " +
                                        "Keep up the good work to maintain your health and fitness.");
                }

                return recommendations;
        }

        /**
         * Adds a list of recommendations to the database for a specific user.
         *
         * @param userId          the ID of the user
         * @param recommendations the list of recommendations to be added
         * @param connection      the database connection
         */
        public void addRecommendationsToDatabase(int userId, List<String> recommendations, Connection connection) {
                try {
                        String query = "INSERT INTO recommendations (u_id, recommendation, date) VALUES (?, ?, ?)";
                        PreparedStatement statement = connection.prepareStatement(query);

                        for (String recommendation : recommendations) {
                                statement.setInt(1, userId);
                                statement.setString(2, recommendation);
                                statement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                                statement.executeUpdate();
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

        /**
         * Updates the recommendations for a given user in the database.
         *
         * @param userId          the ID of the user
         * @param recommendations the list of recommendations to update
         * @param connection      the database connection
         */
        public void updateRecommendation(int userId, List<String> recommendations, Connection connection) {
                try {
                        String query = "UPDATE recommendations SET recommendation = ? WHERE u_id = ?";
                        PreparedStatement statement = connection.prepareStatement(query);

                        for (String recommendation : recommendations) {
                                statement.setString(1, recommendation);
                                statement.setInt(2, userId);
                                statement.executeUpdate();
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }
}

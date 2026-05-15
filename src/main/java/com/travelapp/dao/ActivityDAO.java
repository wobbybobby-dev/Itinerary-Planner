// File: src/main/java/com/travelapp/dao/ActivityDAO.java
package com.travelapp.dao;

import org.springframework.stereotype.Repository;
import com.travelapp.model.Activity;
import com.travelapp.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActivityDAO {

    public Activity createActivity(Activity activity) {
        String sql = "INSERT INTO activities (itinerary_id, activity_name, activity_type, description, " +
                    "location, start_time, end_time, cost, booking_reference, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, activity.getItineraryId());
            pstmt.setString(2, activity.getActivityName());
            pstmt.setString(3, activity.getActivityType());
            pstmt.setString(4, activity.getDescription());
            pstmt.setString(5, activity.getLocation());
            pstmt.setTime(6, activity.getStartTime());
            pstmt.setTime(7, activity.getEndTime());
            pstmt.setBigDecimal(8, activity.getCost());
            pstmt.setString(9, activity.getBookingReference());
            pstmt.setString(10, activity.getNotes());

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                activity.setActivityId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activity;
    }

    public Activity getActivityById(int activityId) {
        String sql = "SELECT * FROM activities WHERE activity_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, activityId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToActivity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Activity> getActivitiesByItineraryId(int itineraryId) {
        String sql = "SELECT * FROM activities WHERE itinerary_id = ? ORDER BY start_time ASC";
        List<Activity> activities = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, itineraryId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                activities.add(mapResultSetToActivity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }

    public boolean updateActivity(Activity activity) {
        String sql = "UPDATE activities SET activity_name = ?, activity_type = ?, description = ?, " +
                    "location = ?, start_time = ?, end_time = ?, cost = ?, booking_reference = ?, notes = ? WHERE activity_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, activity.getActivityName());
            pstmt.setString(2, activity.getActivityType());
            pstmt.setString(3, activity.getDescription());
            pstmt.setString(4, activity.getLocation());
            pstmt.setTime(5, activity.getStartTime());
            pstmt.setTime(6, activity.getEndTime());
            pstmt.setBigDecimal(7, activity.getCost());
            pstmt.setString(8, activity.getBookingReference());
            pstmt.setString(9, activity.getNotes());
            pstmt.setInt(10, activity.getActivityId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteActivity(int activityId) {
        String sql = "DELETE FROM activities WHERE activity_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, activityId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Activity mapResultSetToActivity(ResultSet rs) throws SQLException {
        Activity activity = new Activity();
        activity.setActivityId(rs.getInt("activity_id"));
        activity.setItineraryId(rs.getInt("itinerary_id"));
        activity.setActivityName(rs.getString("activity_name"));
        activity.setActivityType(rs.getString("activity_type"));
        activity.setDescription(rs.getString("description"));
        activity.setLocation(rs.getString("location"));
        activity.setStartTime(rs.getTime("start_time"));
        activity.setEndTime(rs.getTime("end_time"));
        activity.setCost(rs.getBigDecimal("cost"));
        activity.setBookingReference(rs.getString("booking_reference"));
        activity.setNotes(rs.getString("notes"));
        return activity;
    }
}
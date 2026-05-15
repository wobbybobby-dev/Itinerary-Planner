
// File: src/main/java/com/travelapp/dao/TripDAO.java
package com.travelapp.dao;

import org.springframework.stereotype.Repository;
import com.travelapp.model.Trip;
import com.travelapp.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TripDAO {

    public Trip createTrip(Trip trip) {
        String sql = "INSERT INTO trips (user_id, trip_name, destination, start_date, end_date, description, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, trip.getUserId());
            pstmt.setString(2, trip.getTripName());
            pstmt.setString(3, trip.getDestination());
            pstmt.setDate(4, trip.getStartDate());
            pstmt.setDate(5, trip.getEndDate());
            pstmt.setString(6, trip.getDescription());
            pstmt.setString(7, trip.getStatus() != null ? trip.getStatus() : "PLANNED");

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                trip.setTripId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trip;
    }

    public Trip getTripById(int tripId) {
        String sql = "SELECT * FROM trips WHERE trip_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, tripId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToTrip(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Trip> getTripsByUserId(int userId) {
        String sql = "SELECT * FROM trips WHERE user_id = ? ORDER BY start_date DESC";
        List<Trip> trips = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                trips.add(mapResultSetToTrip(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trips;
    }

    public boolean updateTrip(Trip trip) {
        String sql = "UPDATE trips SET trip_name = ?, destination = ?, start_date = ?, end_date = ?, " +
                    "description = ?, status = ? WHERE trip_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, trip.getTripName());
            pstmt.setString(2, trip.getDestination());
            pstmt.setDate(3, trip.getStartDate());
            pstmt.setDate(4, trip.getEndDate());
            pstmt.setString(5, trip.getDescription());
            pstmt.setString(6, trip.getStatus());
            pstmt.setInt(7, trip.getTripId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteTrip(int tripId) {
        String sql = "DELETE FROM trips WHERE trip_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, tripId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Trip> getAllTrips() {
        String sql = "SELECT * FROM trips ORDER BY start_date DESC";
        List<Trip> trips = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                trips.add(mapResultSetToTrip(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trips;
    }

    private Trip mapResultSetToTrip(ResultSet rs) throws SQLException {
        Trip trip = new Trip();
        trip.setTripId(rs.getInt("trip_id"));
        trip.setUserId(rs.getInt("user_id"));
        trip.setTripName(rs.getString("trip_name"));
        trip.setDestination(rs.getString("destination"));
        trip.setStartDate(rs.getDate("start_date"));
        trip.setEndDate(rs.getDate("end_date"));
        trip.setDescription(rs.getString("description"));
        trip.setStatus(rs.getString("status"));
        trip.setCreatedAt(rs.getTimestamp("created_at"));
        trip.setUpdatedAt(rs.getTimestamp("updated_at"));
        return trip;
    }
}




// File: src/main/java/com/travelapp/service/TripService.java

package com.travelapp.service;
 
import com.travelapp.dao.TripDAO;
import com.travelapp.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.sql.Date;
 
@Service
public class TripService {
 
    @Autowired
    private TripDAO tripDAO;
 
    /**
     * Create a new trip
     */
    public Trip createTrip(Trip trip) {
        if (trip.getTripName() == null || trip.getTripName().trim().isEmpty()) {
            throw new IllegalArgumentException("Trip name is required");
        }
        if (trip.getStartDate() == null || trip.getEndDate() == null) {
            throw new IllegalArgumentException("Start and end dates are required");
        }
        if (trip.getStartDate().after(trip.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        
        trip.setStatus("PLANNED");
        return tripDAO.createTrip(trip);
    }
 
    /**
     * Get trip by ID
     */
    public Trip getTripById(int tripId) {
        Trip trip = tripDAO.getTripById(tripId);
        if (trip == null) {
            throw new IllegalArgumentException("Trip not found with ID: " + tripId);
        }
        return trip;
    }
 
    /**
     * Get all trips for a user
     */
    public List<Trip> getTripsByUserId(int userId) {
        return tripDAO.getTripsByUserId(userId);
    }
 
    /**
     * Update trip details
     */
    public Trip updateTrip(Trip trip) {
        if (trip.getTripId() == 0) {
            throw new IllegalArgumentException("Trip ID is required");
        }
        
        Trip existingTrip = getTripById(trip.getTripId());
        
        if (trip.getTripName() != null) {
            existingTrip.setTripName(trip.getTripName());
        }
        if (trip.getDestination() != null) {
            existingTrip.setDestination(trip.getDestination());
        }
        if (trip.getStartDate() != null) {
            existingTrip.setStartDate(trip.getStartDate());
        }
        if (trip.getEndDate() != null) {
            existingTrip.setEndDate(trip.getEndDate());
        }
        if (trip.getDescription() != null) {
            existingTrip.setDescription(trip.getDescription());
        }
        if (trip.getStatus() != null) {
            existingTrip.setStatus(trip.getStatus());
        }
        
        tripDAO.updateTrip(existingTrip);
        return existingTrip;
    }
 
    /**
     * Delete trip
     */
    public boolean deleteTrip(int tripId) {
        getTripById(tripId); // Verify trip exists
        return tripDAO.deleteTrip(tripId);
    }
 
    /**
     * Get all trips
     */
    public List<Trip> getAllTrips() {
        return tripDAO.getAllTrips();
    }
 
    /**
     * Mark trip as completed
     */
    public Trip completeTrip(int tripId) {
        Trip trip = getTripById(tripId);
        trip.setStatus("COMPLETED");
        tripDAO.updateTrip(trip);
        return trip;
    }
 
    /**
     * Get upcoming trips for a user
     */
    public List<Trip> getUpcomingTrips(int userId) {
        List<Trip> trips = getTripsByUserId(userId);
        Date today = new Date(System.currentTimeMillis());
        return trips.stream()
                .filter(trip -> trip.getStartDate().after(today) && !trip.isCompleted())
                .toList();
    }
 
    /**
     * Get past trips for a user
     */
    public List<Trip> getPastTrips(int userId) {
        List<Trip> trips = getTripsByUserId(userId);
        Date today = new Date(System.currentTimeMillis());
        return trips.stream()
                .filter(trip -> trip.getEndDate().before(today) || trip.isCompleted())
                .toList();
    }
 
    /**
     * Validate trip dates
     */
    public boolean validateTripDates(Date startDate, Date endDate) {
        return startDate != null && endDate != null && startDate.before(endDate);
    }
 
    /**
     * Calculate trip duration
     */
    public long calculateTripDuration(int tripId) {
        Trip trip = getTripById(tripId);
        return trip.getDurationDays();
    }
}
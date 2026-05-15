// File: src/main/java/com/travelapp/service/ActivityService.java

package com.travelapp.service;
 
import com.travelapp.dao.ActivityDAO;
import com.travelapp.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
 
@Service
public class ActivityService {
 
    @Autowired
    private ActivityDAO activityDAO;
 
    /**
     * Create a new activity
     */
    public Activity createActivity(Activity activity) {
        if (activity.getActivityName() == null || activity.getActivityName().trim().isEmpty()) {
            throw new IllegalArgumentException("Activity name is required");
        }
        if (activity.getItineraryId() == 0) {
            throw new IllegalArgumentException("Itinerary ID is required");
        }
        if (activity.getLocation() == null || activity.getLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("Location is required");
        }
        
        if (activity.getCost() == null) {
            activity.setCost(BigDecimal.ZERO);
        }
        
        return activityDAO.createActivity(activity);
    }
 
    /**
     * Get activity by ID
     */
    public Activity getActivityById(int activityId) {
        Activity activity = activityDAO.getActivityById(activityId);
        if (activity == null) {
            throw new IllegalArgumentException("Activity not found with ID: " + activityId);
        }
        return activity;
    }
 
    /**
     * Get all activities for an itinerary
     */
    public List<Activity> getActivitiesByItineraryId(int itineraryId) {
        return activityDAO.getActivitiesByItineraryId(itineraryId);
    }
 
    /**
     * Update activity details
     */
    public Activity updateActivity(Activity activity) {
        if (activity.getActivityId() == 0) {
            throw new IllegalArgumentException("Activity ID is required");
        }
        
        Activity existingActivity = getActivityById(activity.getActivityId());
        
        if (activity.getActivityName() != null) {
            existingActivity.setActivityName(activity.getActivityName());
        }
        if (activity.getActivityType() != null) {
            existingActivity.setActivityType(activity.getActivityType());
        }
        if (activity.getDescription() != null) {
            existingActivity.setDescription(activity.getDescription());
        }
        if (activity.getLocation() != null) {
            existingActivity.setLocation(activity.getLocation());
        }
        if (activity.getStartTime() != null) {
            existingActivity.setStartTime(activity.getStartTime());
        }
        if (activity.getEndTime() != null) {
            existingActivity.setEndTime(activity.getEndTime());
        }
        if (activity.getCost() != null) {
            existingActivity.setCost(activity.getCost());
        }
        if (activity.getBookingReference() != null) {
            existingActivity.setBookingReference(activity.getBookingReference());
        }
        if (activity.getNotes() != null) {
            existingActivity.setNotes(activity.getNotes());
        }
        
        activityDAO.updateActivity(existingActivity);
        return existingActivity;
    }
 
    /**
     * Delete activity
     */
    public boolean deleteActivity(int activityId) {
        getActivityById(activityId); // Verify activity exists
        return activityDAO.deleteActivity(activityId);
    }
 
    /**
     * Calculate total cost of activities for an itinerary
     */
    public BigDecimal calculateTotalActivityCost(int itineraryId) {
        List<Activity> activities = getActivitiesByItineraryId(itineraryId);
        return activities.stream()
                .map(Activity::getCost)
                .filter(cost -> cost != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
 
    /**
     * Get activities by type
     */
    public List<Activity> getActivitiesByType(int itineraryId, String activityType) {
        List<Activity> activities = getActivitiesByItineraryId(itineraryId);
        return activities.stream()
                .filter(activity -> activity.getActivityType() != null && 
                        activity.getActivityType().equals(activityType))
                .toList();
    }
 
    /**
     * Get activities by cost range
     */
    public List<Activity> getActivitiesByPriceRange(int itineraryId, 
                                                     BigDecimal minPrice, 
                                                     BigDecimal maxPrice) {
        List<Activity> activities = getActivitiesByItineraryId(itineraryId);
        return activities.stream()
                .filter(activity -> activity.getCost() != null &&
                        activity.getCost().compareTo(minPrice) >= 0 &&
                        activity.getCost().compareTo(maxPrice) <= 0)
                .toList();
    }
 
    /**
     * Check if activity has booking
     */
    public boolean hasBooking(int activityId) {
        Activity activity = getActivityById(activityId);
        return activity.hasBooking();
    }
 
    /**
     * Get duration of activity in hours
     */
    public String getActivityDuration(int activityId) {
        Activity activity = getActivityById(activityId);
        return activity.getDurationHours();
    }
 
    /**
     * Sort activities by start time
     */
    public List<Activity> sortActivitiesByTime(int itineraryId) {
        List<Activity> activities = getActivitiesByItineraryId(itineraryId);
        return activities.stream()
                .sorted((a, b) -> {
                    if (a.getStartTime() == null || b.getStartTime() == null) {
                        return 0;
                    }
                    return a.getStartTime().compareTo(b.getStartTime());
                })
                .toList();
    }
 
    /**
     * Get high-cost activities
     */
    public List<Activity> getExpensiveActivities(int itineraryId, BigDecimal threshold) {
        List<Activity> activities = getActivitiesByItineraryId(itineraryId);
        return activities.stream()
                .filter(activity -> activity.getCost() != null &&
                        activity.getCost().compareTo(threshold) >= 0)
                .toList();
    }
}
// File: src/main/java/com/travelapp/api/controller/ActivityRestController.java
package com.travelapp.api.controller;
 
import com.travelapp.dao.ActivityDAO;
import com.travelapp.model.Activity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
 
@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "*")
public class ActivityRestController {
 
    private ActivityDAO activityDAO = new ActivityDAO();
 
    @GetMapping("/itinerary/{itineraryId}")
    public ResponseEntity<List<Activity>> getActivitiesByItinerary(@PathVariable int itineraryId) {
        List<Activity> activities = activityDAO.getActivitiesByItineraryId(itineraryId);
        return ResponseEntity.ok(activities);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivity(@PathVariable int id) {
        Activity activity = activityDAO.getActivityById(id);
        if (activity != null) {
            return ResponseEntity.ok(activity);
        }
        return ResponseEntity.notFound().build();
    }
 
    @PostMapping("")
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        Activity createdActivity = activityDAO.createActivity(activity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdActivity);
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<String> updateActivity(@PathVariable int id, @RequestBody Activity activity) {
        activity.setActivityId(id);
        boolean success = activityDAO.updateActivity(activity);
        if (success) {
            return ResponseEntity.ok("{\"message\":\"Activity updated successfully\"}");
        }
        return ResponseEntity.badRequest().body("{\"error\":\"Failed to update activity\"}");
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable int id) {
        boolean success = activityDAO.deleteActivity(id);
        if (success) {
            return ResponseEntity.ok("{\"message\":\"Activity deleted successfully\"}");
        }
        return ResponseEntity.badRequest().body("{\"error\":\"Failed to delete activity\"}");
    }
}

// File: src/main/java/com/travelapp/api/controller/TripRestController.java
package com.travelapp.api.controller;
 
import com.travelapp.dao.TripDAO;
import com.travelapp.model.Trip;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
 
@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "*")
public class TripRestController {
 
    private TripDAO tripDAO = new TripDAO();
 
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Trip>> getTripsByUser(@PathVariable int userId) {
        List<Trip> trips = tripDAO.getTripsByUserId(userId);
        return ResponseEntity.ok(trips);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTrip(@PathVariable int id) {
        Trip trip = tripDAO.getTripById(id);
        if (trip != null) {
            return ResponseEntity.ok(trip);
        }
        return ResponseEntity.notFound().build();
    }
 
    @GetMapping("")
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripDAO.getAllTrips();
        return ResponseEntity.ok(trips);
    }
 
    @PostMapping("")
    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip) {
        Trip createdTrip = tripDAO.createTrip(trip);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTrip);
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTrip(@PathVariable int id, @RequestBody Trip trip) {
        trip.setTripId(id);
        boolean success = tripDAO.updateTrip(trip);
        if (success) {
            return ResponseEntity.ok("{\"message\":\"Trip updated successfully\"}");
        }
        return ResponseEntity.badRequest().body("{\"error\":\"Failed to update trip\"}");
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrip(@PathVariable int id) {
        boolean success = tripDAO.deleteTrip(id);
        if (success) {
            return ResponseEntity.ok("{\"message\":\"Trip deleted successfully\"}");
        }
        return ResponseEntity.badRequest().body("{\"error\":\"Failed to delete trip\"}");
    }
}
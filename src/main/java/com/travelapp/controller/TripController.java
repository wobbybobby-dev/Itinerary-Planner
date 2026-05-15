// File: src/main/java/com/travelapp/controller/TripController.java
package com.travelapp.controller;
 
import com.travelapp.dao.TripDAO;
import com.travelapp.model.Trip;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
 
@Controller
@RequestMapping("/trips")
public class TripController {
 
    private TripDAO tripDAO = new TripDAO();
 
    @GetMapping("")
    public String getAllTrips(Model model) {
        int userId = 1;
        List<Trip> trips = tripDAO.getTripsByUserId(userId);
        model.addAttribute("trips", trips);
        return "trips/list";
    }
 
    @GetMapping("/{id}")
    public String getTripDetails(@PathVariable int id, Model model) {
        Trip trip = tripDAO.getTripById(id);
        model.addAttribute("trip", trip);
        return "trips/details";
    }
 
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("trip", new Trip());
        return "trips/create";
    }
 
    @PostMapping("/create")
    public String createTrip(@ModelAttribute Trip trip) {
        trip.setUserId(1);
        tripDAO.createTrip(trip);
        return "redirect:/trips";
    }
 
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        Trip trip = tripDAO.getTripById(id);
        model.addAttribute("trip", trip);
        return "trips/edit";
    }
 
    @PostMapping("/{id}/update")
    public String updateTrip(@PathVariable int id, @ModelAttribute Trip trip) {
        trip.setTripId(id);
        tripDAO.updateTrip(trip);
        return "redirect:/trips/" + id;
    }
 
    @PostMapping("/{id}/delete")
    public String deleteTrip(@PathVariable int id) {
        tripDAO.deleteTrip(id);
        return "redirect:/trips";
    }
}
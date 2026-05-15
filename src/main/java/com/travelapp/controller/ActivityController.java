
// File: src/main/java/com/travelapp/controller/ActivityController.java
package com.travelapp.controller;
 
import com.travelapp.dao.ActivityDAO;
import com.travelapp.model.Activity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
 
@Controller
@RequestMapping("/activities")
public class ActivityController {
 
    private ActivityDAO activityDAO = new ActivityDAO();
 
    @PostMapping("/create")
    public String createActivity(@ModelAttribute Activity activity,
                               @RequestParam int itineraryId) {
        activity.setItineraryId(itineraryId);
        activityDAO.createActivity(activity);
        return "redirect:/itineraries/" + itineraryId;
    }
 
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        Activity activity = activityDAO.getActivityById(id);
        model.addAttribute("activity", activity);
        return "activities/edit";
    }
 
    @PostMapping("/{id}/update")
    public String updateActivity(@PathVariable int id, @ModelAttribute Activity activity) {
        activity.setActivityId(id);
        activityDAO.updateActivity(activity);
        return "redirect:/itineraries/" + activity.getItineraryId();
    }
 
    @PostMapping("/{id}/delete")
    public String deleteActivity(@PathVariable int id) {
        Activity activity = activityDAO.getActivityById(id);
        int itineraryId = activity.getItineraryId();
        activityDAO.deleteActivity(id);
        return "redirect:/itineraries/" + itineraryId;
    }
}
 
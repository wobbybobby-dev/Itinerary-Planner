
// File: src/main/java/com/travelapp/controller/HomeController.java
package com.travelapp.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
 
@Controller
public class HomeController {
 
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("appName", "Travel Itinerary App");
        return "home";
    }
 
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("title", "Dashboard");
        return "dashboard";
    }
}
// File: src/main/java/com/travelapp/controller/BudgetController.java
package com.travelapp.controller;

import com.travelapp.dao.BudgetDAO;
import com.travelapp.model.Budget;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/budgets")
public class BudgetController {

    private BudgetDAO budgetDAO = new BudgetDAO();

    /**
     * Display budget summary for a trip.
     * URL: GET /budgets/trip/{tripId}
     */
    @GetMapping("/trip/{tripId}")
    public String getBudgetByTrip(@PathVariable int tripId, Model model) {
        Budget budget = budgetDAO.getBudgetByTripId(tripId);

        // If no budget exists yet, create an empty object
        if (budget == null) {
            budget = new Budget();
            budget.setTripId(tripId);
        }

        model.addAttribute("budget", budget);
        return "budgets/summary";
    }

    /**
     * Show edit form for a budget.
     * URL: GET /budgets/{id}/edit
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        Budget budget = budgetDAO.getBudgetById(id);
        model.addAttribute("budget", budget);
        return "budgets/edit";
    }

    /**
     * Create a new budget.
     * URL: POST /budgets/create
     */
    @PostMapping("/create")
    public String createBudget(@ModelAttribute Budget budget) {
        Budget createdBudget = budgetDAO.createBudget(budget);
        return "redirect:/budgets/trip/" + createdBudget.getTripId();
    }

    /**
     * Update an existing budget.
     * URL: POST /budgets/{id}/update
     */
    @PostMapping("/{id}/update")
    public String updateBudget(@PathVariable int id,
                               @ModelAttribute Budget budget) {

        budget.setBudgetId(id);
        budgetDAO.updateBudget(budget);

        return "redirect:/budgets/trip/" + budget.getTripId();
    }

    /**
     * Delete a budget.
     * URL: POST /budgets/{id}/delete
     */
    @PostMapping("/{id}/delete")
    public String deleteBudget(@PathVariable int id) {
        Budget budget = budgetDAO.getBudgetById(id);
        int tripId = budget.getTripId();

        budgetDAO.deleteBudget(id);

        return "redirect:/trips/" + tripId;
    }
}
// File: src/main/java/com/travelapp/api/controller/BudgetRestController.java
package com.travelapp.api.controller;

import com.travelapp.dao.BudgetDAO;
import com.travelapp.model.Budget;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = "*")
public class BudgetRestController {

    private BudgetDAO budgetDAO = new BudgetDAO();

    /**
     * Get budget details for a specific trip.
     * GET /api/budgets/trip/{tripId}
     */
    @GetMapping("/trip/{tripId}")
    public ResponseEntity<Budget> getBudgetByTrip(@PathVariable int tripId) {
        Budget budget = budgetDAO.getBudgetByTripId(tripId);

        if (budget != null) {
            return ResponseEntity.ok(budget);
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Get budget by its ID.
     * GET /api/budgets/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudget(@PathVariable int id) {
        Budget budget = budgetDAO.getBudgetById(id);

        if (budget != null) {
            return ResponseEntity.ok(budget);
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Create a new budget.
     * POST /api/budgets
     */
    @PostMapping("")
    public ResponseEntity<Budget> createBudget(@RequestBody Budget budget) {
        Budget createdBudget = budgetDAO.createBudget(budget);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBudget);
    }

    /**
     * Update an existing budget.
     * PUT /api/budgets/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBudget(
            @PathVariable int id,
            @RequestBody Budget budget) {

        budget.setBudgetId(id);

        boolean success = budgetDAO.updateBudget(budget);

        if (success) {
            return ResponseEntity.ok(
                "{\"message\":\"Budget updated successfully\"}"
            );
        }

        return ResponseEntity.badRequest().body(
            "{\"error\":\"Failed to update budget\"}"
        );
    }

    /**
     * Delete a budget.
     * DELETE /api/budgets/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBudget(@PathVariable int id) {
        boolean success = budgetDAO.deleteBudget(id);

        if (success) {
            return ResponseEntity.ok(
                "{\"message\":\"Budget deleted successfully\"}"
            );
        }

        return ResponseEntity.badRequest().body(
            "{\"error\":\"Failed to delete budget\"}"
        );
    }
}
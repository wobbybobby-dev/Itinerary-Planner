// File: src/main/java/com/travelapp/service/BudgetService.java

package com.travelapp.service;
 
import com.travelapp.dao.BudgetDAO;
import com.travelapp.model.Budget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
 
@Service
public class BudgetService {
 
    @Autowired
    private BudgetDAO budgetDAO;
 
    /**
     * Create a new budget
     */
    public Budget createBudget(Budget budget) {
        if (budget.getTripId() == 0) {
            throw new IllegalArgumentException("Trip ID is required");
        }
        if (budget.getCategory() == null || budget.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("Category is required");
        }
        if (budget.getBudgetedAmount() == null || budget.getBudgetedAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Budgeted amount must be greater than zero");
        }
        
        if (budget.getActualAmount() == null) {
            budget.setActualAmount(BigDecimal.ZERO);
        }
        if (budget.getCurrency() == null) {
            budget.setCurrency("USD");
        }
        
        return budgetDAO.createBudget(budget);
    }
 
    /**
     * Get budget by ID
     */
    public Budget getBudgetById(int budgetId) {
        Budget budget = budgetDAO.getBudgetById(budgetId);
        if (budget == null) {
            throw new IllegalArgumentException("Budget not found with ID: " + budgetId);
        }
        return budget;
    }
 
    /**
     * Get all budgets for a trip
     */
    public List<Budget> getBudgetsByTripId(int tripId) {
        return budgetDAO.getBudgetsByTripId(tripId);
    }
 
    /**
     * Update budget
     */
    public Budget updateBudget(Budget budget) {
        if (budget.getBudgetId() == 0) {
            throw new IllegalArgumentException("Budget ID is required");
        }
        
        Budget existingBudget = getBudgetById(budget.getBudgetId());
        
        if (budget.getCategory() != null) {
            existingBudget.setCategory(budget.getCategory());
        }
        if (budget.getBudgetedAmount() != null) {
            existingBudget.setBudgetedAmount(budget.getBudgetedAmount());
        }
        if (budget.getActualAmount() != null) {
            existingBudget.setActualAmount(budget.getActualAmount());
        }
        if (budget.getCurrency() != null) {
            existingBudget.setCurrency(budget.getCurrency());
        }
        
        budgetDAO.updateBudget(existingBudget);
        return existingBudget;
    }
 
    /**
     * Delete budget
     */
    public boolean deleteBudget(int budgetId) {
        getBudgetById(budgetId); // Verify budget exists
        return budgetDAO.deleteBudget(budgetId);
    }
 
    /**
     * Update actual spent amount
     */
    public Budget updateActualSpent(int budgetId, BigDecimal actualAmount) {
        Budget budget = getBudgetById(budgetId);
        budget.setActualAmount(actualAmount);
        return updateBudget(budget);
    }
 
    /**
     * Add to actual spent amount
     */
    public Budget addToActualSpent(int budgetId, BigDecimal amount) {
        Budget budget = getBudgetById(budgetId);
        BigDecimal newAmount = budget.getActualAmount().add(amount);
        budget.setActualAmount(newAmount);
        return updateBudget(budget);
    }
 
    /**
     * Calculate total budgeted for a trip
     */
    public BigDecimal calculateTotalBudgeted(int tripId) {
        List<Budget> budgets = getBudgetsByTripId(tripId);
        return budgets.stream()
                .map(Budget::getBudgetedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
 
    /**
     * Calculate total actual spent for a trip
     */
    public BigDecimal calculateTotalActualSpent(int tripId) {
        List<Budget> budgets = getBudgetsByTripId(tripId);
        return budgets.stream()
                .map(Budget::getActualAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
 
    /**
     * Calculate remaining budget for a trip
     */
    public BigDecimal calculateRemainingBudget(int tripId) {
        BigDecimal totalBudgeted = calculateTotalBudgeted(tripId);
        BigDecimal totalSpent = calculateTotalActualSpent(tripId);
        return totalBudgeted.subtract(totalSpent);
    }
 
    /**
     * Calculate budget usage percentage for a trip
     */
    public double calculateBudgetUsagePercentage(int tripId) {
        BigDecimal totalBudgeted = calculateTotalBudgeted(tripId);
        if (totalBudgeted.compareTo(BigDecimal.ZERO) == 0) {
            return 0.0;
        }
        BigDecimal totalSpent = calculateTotalActualSpent(tripId);
        return (totalSpent.doubleValue() / totalBudgeted.doubleValue()) * 100;
    }
 
    /**
     * Check if budget is over
     */
    public boolean isOverBudget(int budgetId) {
        Budget budget = getBudgetById(budgetId);
        return budget.isOverBudget();
    }
 
    /**
     * Check if any budget category is over for a trip
     */
    public boolean isAnyBudgetOverForTrip(int tripId) {
        List<Budget> budgets = getBudgetsByTripId(tripId);
        return budgets.stream().anyMatch(Budget::isOverBudget);
    }
 
    /**
     * Get overspent categories
     */
    public List<Budget> getOverspentCategories(int tripId) {
        List<Budget> budgets = getBudgetsByTripId(tripId);
        return budgets.stream()
                .filter(Budget::isOverBudget)
                .toList();
    }
 
    /**
     * Get budget by category
     */
    public Budget getBudgetByCategory(int tripId, String category) {
        List<Budget> budgets = getBudgetsByTripId(tripId);
        return budgets.stream()
                .filter(b -> b.getCategory().equals(category))
                .findFirst()
                .orElse(null);
    }
 
    /**
     * Calculate remaining budget for a category
     */
    public BigDecimal getRemainingBudgetForCategory(int tripId, String category) {
        Budget budget = getBudgetByCategory(tripId, category);
        if (budget == null) {
            return BigDecimal.ZERO;
        }
        return budget.getRemainingBudget();
    }
 
    /**
     * Get percentage used for a category
     */
    public double getPercentageUsedForCategory(int budgetId) {
        Budget budget = getBudgetById(budgetId);
        return budget.getPercentageUsed();
    }
}
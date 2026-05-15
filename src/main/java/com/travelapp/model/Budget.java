
// File: src/main/java/com/travelapp/model/Budget.java

package com.travelapp.model;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget implements Serializable {
    private int budgetId;
    private int tripId;
    private String category;
    private BigDecimal budgetedAmount;
    private BigDecimal actualAmount;
    private String currency;
    private Timestamp createdAt;
    private Timestamp updatedAt;
 
    public Budget(int tripId, String category, BigDecimal budgetedAmount) {
        this.tripId = tripId;
        this.category = category;
        this.budgetedAmount = budgetedAmount;
        this.actualAmount = BigDecimal.ZERO;
        this.currency = "USD";
    }
 
    public BigDecimal getRemainingBudget() {
        return budgetedAmount.subtract(actualAmount);
    }
 
    public double getPercentageUsed() {
        if (budgetedAmount.compareTo(BigDecimal.ZERO) == 0) {
            return 0.0;
        }
        return (actualAmount.doubleValue() / budgetedAmount.doubleValue()) * 100;
    }
 
    public boolean isOverBudget() {
        return actualAmount.compareTo(budgetedAmount) > 0;
    }
}
// ============================================================================
// File: src/main/java/com/travelapp/dao/BudgetDAO.java
// ============================================================================
package com.travelapp.dao;

import org.springframework.stereotype.Repository;
import com.travelapp.model.Budget;
import com.travelapp.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BudgetDAO {

    public Budget createBudget(Budget budget) {
        String sql = "INSERT INTO budget (trip_id, category, budgeted_amount, actual_amount, currency) " +
                    "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, budget.getTripId());
            pstmt.setString(2, budget.getCategory());
            pstmt.setBigDecimal(3, budget.getBudgetedAmount());
            pstmt.setBigDecimal(4, budget.getActualAmount());
            pstmt.setString(5, budget.getCurrency());

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                budget.setBudgetId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return budget;
    }

    public Budget getBudgetById(int budgetId) {
        String sql = "SELECT * FROM budget WHERE budget_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, budgetId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToBudget(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Budget> getBudgetsByTripId(int tripId) {
        String sql = "SELECT * FROM budget WHERE trip_id = ? ORDER BY category ASC";
        List<Budget> budgets = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, tripId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                budgets.add(mapResultSetToBudget(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return budgets;
    }

    public boolean updateBudget(Budget budget) {
        String sql = "UPDATE budget SET category = ?, budgeted_amount = ?, actual_amount = ?, currency = ? " +
                    "WHERE budget_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, budget.getCategory());
            pstmt.setBigDecimal(2, budget.getBudgetedAmount());
            pstmt.setBigDecimal(3, budget.getActualAmount());
            pstmt.setString(4, budget.getCurrency());
            pstmt.setInt(5, budget.getBudgetId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBudget(int budgetId) {
        String sql = "DELETE FROM budget WHERE budget_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, budgetId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Budget> getAllBudgets() {
        String sql = "SELECT * FROM budget ORDER BY trip_id, category";
        List<Budget> budgets = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                budgets.add(mapResultSetToBudget(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return budgets;
    }

    private Budget mapResultSetToBudget(ResultSet rs) throws SQLException {
        Budget budget = new Budget();
        budget.setBudgetId(rs.getInt("budget_id"));
        budget.setTripId(rs.getInt("trip_id"));
        budget.setCategory(rs.getString("category"));
        budget.setBudgetedAmount(rs.getBigDecimal("budgeted_amount"));
        budget.setActualAmount(rs.getBigDecimal("actual_amount"));
        budget.setCurrency(rs.getString("currency"));
        budget.setCreatedAt(rs.getTimestamp("created_at"));
        budget.setUpdatedAt(rs.getTimestamp("updated_at"));
        return budget;
    }

    public Budget getBudgetByTripId(int tripId) {
        List<Budget> budgets = getBudgetsByTripId(tripId);
        return budgets.isEmpty() ? null : budgets.get(0);
    }
}
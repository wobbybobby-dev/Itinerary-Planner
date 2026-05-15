// File: src/main/webapp/js/budget-calculator.js

class BudgetCalculator {
    constructor(tripId) {
        this.tripId = tripId;
        this.apiUrl = `/travelapp/api/budgets`;
        this.init();
    }
 
    init() {
        this.loadBudgets();
        this.setupEventListeners();
    }
 
    loadBudgets() {
        fetch(`${this.apiUrl}/trip/${this.tripId}`)
            .then(response => {
                if (!response.ok) throw new Error('Failed to load budgets');
                return response.json();
            })
            .then(budgets => this.renderBudgetSummary(budgets))
            .catch(error => {
                console.error('Error loading budgets:', error);
                this.showError('Failed to load budget data');
            });
    }
 
    renderBudgetSummary(budgets) {
        const container = document.getElementById('budget-summary');
        if (!container) return;
 
        let totalBudgeted = 0;
        let totalSpent = 0;
 
        const rows = budgets.map(budget => {
            totalBudgeted += parseFloat(budget.budgetedAmount || 0);
            totalSpent += parseFloat(budget.actualAmount || 0);
 
            const remaining = budget.budgetedAmount - budget.actualAmount;
            const percentageUsed = (budget.actualAmount / budget.budgetedAmount) * 100;
            const statusClass = remaining < 0 ? 'danger' : percentageUsed >= 80 ? 'warning' : 'success';
            const statusText = remaining < 0 ? 'Over Budget' : percentageUsed >= 80 ? 'High Usage' : 'On Track';
 
            return `
                <tr>
                    <td>${budget.category}</td>
                    <td class="text-end">$${parseFloat(budget.budgetedAmount).toFixed(2)}</td>
                    <td class="text-end">$${parseFloat(budget.actualAmount).toFixed(2)}</td>
                    <td class="text-end">$${remaining.toFixed(2)}</td>
                    <td class="text-end">${percentageUsed.toFixed(1)}%</td>
                    <td><span class="badge bg-${statusClass}">${statusText}</span></td>
                    <td>
                        <button class="btn btn-sm btn-warning" onclick="budgetCalculator.editBudget(${budget.budgetId})">Edit</button>
                        <button class="btn btn-sm btn-danger" onclick="budgetCalculator.deleteBudget(${budget.budgetId})">Delete</button>
                    </td>
                </tr>
            `;
        }).join('');
 
        const overallPercentage = (totalSpent / totalBudgeted) * 100;
        const summaryHtml = `
            <div class="row mb-3">
                <div class="col-md-3">
                    <div class="card text-center">
                        <div class="card-body">
                            <h6 class="card-title text-muted">Total Budget</h6>
                            <h3 class="text-primary">$${totalBudgeted.toFixed(2)}</h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center">
                        <div class="card-body">
                            <h6 class="card-title text-muted">Total Spent</h6>
                            <h3 class="text-danger">$${totalSpent.toFixed(2)}</h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center">
                        <div class="card-body">
                            <h6 class="card-title text-muted">Remaining</h6>
                            <h3 class="text-success">$${(totalBudgeted - totalSpent).toFixed(2)}</h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center">
                        <div class="card-body">
                            <h6 class="card-title text-muted">Usage %</h6>
                            <h3 class="text-warning">${overallPercentage.toFixed(1)}%</h3>
                        </div>
                    </div>
                </div>
            </div>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Category</th>
                        <th class="text-end">Budgeted</th>
                        <th class="text-end">Spent</th>
                        <th class="text-end">Remaining</th>
                        <th class="text-end">Usage %</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    ${rows}
                </tbody>
            </table>
        `;
 
        container.innerHTML = summaryHtml;
    }
 
    addBudget(tripId, category, budgetedAmount) {
        const data = {
            tripId: tripId,
            category: category,
            budgetedAmount: budgetedAmount,
            actualAmount: 0
        };
 
        fetch(this.apiUrl, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (!response.ok) throw new Error('Failed to add budget');
            return response.json();
        })
        .then(() => {
            this.showSuccess('Budget added successfully');
            this.loadBudgets();
        })
        .catch(error => {
            console.error('Error:', error);
            this.showError('Failed to add budget');
        });
    }
 
    updateBudgetSpent(budgetId, actualAmount) {
        const data = { actualAmount: actualAmount };
 
        fetch(`${this.apiUrl}/${budgetId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (!response.ok) throw new Error('Failed to update budget');
            return response.json();
        })
        .then(() => {
            this.showSuccess('Budget updated successfully');
            this.loadBudgets();
        })
        .catch(error => {
            console.error('Error:', error);
            this.showError('Failed to update budget');
        });
    }
 
    editBudget(budgetId) {
        console.log('Edit budget:', budgetId);
        // Implementation for edit modal
    }
 
    deleteBudget(budgetId) {
        if (!confirm('Are you sure you want to delete this budget?')) return;
 
        fetch(`${this.apiUrl}/${budgetId}`, { method: 'DELETE' })
            .then(response => {
                if (!response.ok) throw new Error('Failed to delete budget');
                return response.json();
            })
            .then(() => {
                this.showSuccess('Budget deleted successfully');
                this.loadBudgets();
            })
            .catch(error => {
                console.error('Error:', error);
                this.showError('Failed to delete budget');
            });
    }
 
    calculateTotalSpent(activities) {
        return activities.reduce((total, activity) => {
            return total + (parseFloat(activity.cost) || 0);
        }, 0);
    }
 
    setupEventListeners() {
        // Setup any event listeners needed
    }
 
    showSuccess(message) {
        this.showAlert(message, 'success');
    }
 
    showError(message) {
        this.showAlert(message, 'danger');
    }
 
    showAlert(message, type) {
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
        alertDiv.role = 'alert';
        alertDiv.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        `;
        document.body.insertBefore(alertDiv, document.body.firstChild);
        
        // Auto dismiss after 5 seconds
        setTimeout(() => {
            if (alertDiv.parentNode) {
                alertDiv.remove();
            }
        }, 5000);
    }
}
 
// Initialize on page load
document.addEventListener('DOMContentLoaded', () => {
    const tripId = document.body.dataset.tripId;
    if (tripId) {
        window.budgetCalculator = new BudgetCalculator(tripId);
    }
});

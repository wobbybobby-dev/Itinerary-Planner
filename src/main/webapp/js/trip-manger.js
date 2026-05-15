// File: public/js/activity-manager.js
class ActivityManager {
    constructor(itineraryId) {
        this.itineraryId = itineraryId;
        this.apiUrl = '/travelapp/api/activities';
        this.init();
    }
 
    init() {
        this.loadActivities();
        this.setupEventListeners();
    }
 
    loadActivities() {
        fetch(`${this.apiUrl}/itinerary/${this.itineraryId}`)
            .then(response => {
                if (!response.ok) throw new Error('Failed to load activities');
                return response.json();
            })
            .then(activities => this.renderActivities(activities))
            .catch(error => {
                console.error('Error loading activities:', error);
                this.showError('Failed to load activities');
            });
    }
 
    renderActivities(activities) {
        const container = document.getElementById('activities-container');
        if (!container) return;
 
        container.innerHTML = '';
 
        if (activities.length === 0) {
            container.innerHTML = '<p class="text-center text-light">No activities planned</p>';
            return;
        }
 
        activities.forEach(activity => {
            const card = document.createElement('div');
            card.className = 'card activity-card mb-3';
            card.innerHTML = `
                <div class="card-header">
                    <h5>${activity.activityName}</h5>
                    <span class="badge">${activity.activityType}</span>
                </div>
                <div class="card-body">
                    <p><strong>📍 Location:</strong> ${activity.location}</p>
                    <p><strong>⏰ Time:</strong> ${activity.startTime} - ${activity.endTime}</p>
                    <p><strong>💰 Cost:</strong> $${activity.cost}</p>
                    <p><strong>📝 Notes:</strong> ${activity.notes || 'N/A'}</p>
                </div>
                <div class="card-footer">
                    <button class="btn btn-sm btn-warning" onclick="activityManager.editActivity(${activity.activityId})">Edit</button>
                    <button class="btn btn-sm btn-danger" onclick="activityManager.deleteActivity(${activity.activityId})">Delete</button>
                </div>
            `;
            container.appendChild(card);
        });
    }
 
    createActivity(data) {
        fetch(this.apiUrl, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (!response.ok) throw new Error('Failed to create activity');
            return response.json();
        })
        .then(() => {
            this.showSuccess('Activity created successfully');
            this.loadActivities();
        })
        .catch(error => {
            console.error('Error:', error);
            this.showError('Failed to create activity');
        });
    }
 
    updateActivity(activityId, data) {
        fetch(`${this.apiUrl}/${activityId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (!response.ok) throw new Error('Failed to update activity');
            return response.json();
        })
        .then(() => {
            this.showSuccess('Activity updated successfully');
            this.loadActivities();
        })
        .catch(error => {
            console.error('Error:', error);
            this.showError('Failed to update activity');
        });
    }
 
    deleteActivity(activityId) {
        if (!confirm('Are you sure you want to delete this activity?')) return;
 
        fetch(`${this.apiUrl}/${activityId}`, { method: 'DELETE' })
            .then(response => {
                if (!response.ok) throw new Error('Failed to delete activity');
                return response.json();
            })
            .then(() => {
                this.showSuccess('Activity deleted successfully');
                this.loadActivities();
            })
            .catch(error => {
                console.error('Error:', error);
                this.showError('Failed to delete activity');
            });
    }
 
    editActivity(activityId) {
        console.log('Edit activity:', activityId);
        // Implementation for edit modal
    }
 
    setupEventListeners() {
        const form = document.getElementById('activity-form');
        if (form) {
            form.addEventListener('submit', (e) => {
                e.preventDefault();
                const formData = new FormData(form);
                const data = Object.fromEntries(formData);
                data.itineraryId = this.itineraryId;
                this.createActivity(data);
                form.reset();
            });
        }
    }
 
    showSuccess(message) {
        this.showAlert(message, 'success');
    }
 
    showError(message) {
        this.showAlert(message, 'danger');
    }
 
    showAlert(message, type) {
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert alert-${type}`;
        alertDiv.textContent = message;
        document.body.insertBefore(alertDiv, document.body.firstChild);
        setTimeout(() => alertDiv.remove(), 3000);
    }
}
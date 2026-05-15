// File: src/main/webapp/WEB-INF/views/activities/create.jsp

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Activity</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar navbar-dark" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
        <div class="container-fluid">
            <span class="navbar-brand">✈️ Add Activity</span>
            <a href="javascript:history.back()" class="btn btn-light btn-sm">← Back</a>
        </div>
    </nav>
 
    <div class="container mt-4 mb-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-success text-white">
                        <h5 class="mb-0">Schedule Activity</h5>
                    </div>
                    <div class="card-body">
                        <form method="post" action="${pageContext.request.contextPath}/activities/create">
                            <input type="hidden" name="itineraryId" value="${param.itinerary}">
 
                            <div class="form-group mb-3">
                                <label for="activityName" class="form-label"><strong>Activity Name</strong></label>
                                <input type="text" class="form-control" id="activityName" name="activityName" 
                                       placeholder="e.g., Louvre Museum Visit" required>
                            </div>
 
                            <div class="form-group mb-3">
                                <label for="activityType" class="form-label"><strong>Activity Type</strong></label>
                                <select class="form-select" id="activityType" name="activityType">
                                    <option value="Cultural">Cultural</option>
                                    <option value="Outdoor">Outdoor</option>
                                    <option value="Food">Food & Dining</option>
                                    <option value="Adventure">Adventure</option>
                                    <option value="Shopping">Shopping</option>
                                    <option value="Relaxation">Relaxation</option>
                                    <option value="Transportation">Transportation</option>
                                    <option value="Other">Other</option>
                                </select>
                            </div>
 
                            <div class="form-group mb-3">
                                <label for="location" class="form-label"><strong>Location</strong></label>
                                <input type="text" class="form-control" id="location" name="location" 
                                       placeholder="Address or landmark" required>
                            </div>
 
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group mb-3">
                                        <label for="startTime" class="form-label"><strong>Start Time</strong></label>
                                        <input type="time" class="form-control" id="startTime" name="startTime" required>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group mb-3">
                                        <label for="endTime" class="form-label"><strong>End Time</strong></label>
                                        <input type="time" class="form-control" id="endTime" name="endTime" required>
                                    </div>
                                </div>
                            </div>
 
                            <div class="form-group mb-3">
                                <label for="cost" class="form-label"><strong>Cost (USD)</strong></label>
                                <input type="number" class="form-control" id="cost" name="cost" 
                                       step="0.01" min="0" placeholder="0.00">
                            </div>
 
                            <div class="form-group mb-3">
                                <label for="description" class="form-label"><strong>Description</strong></label>
                                <textarea class="form-control" id="description" name="description" rows="3"
                                          placeholder="Details about this activity..."></textarea>
                            </div>
 
                            <div class="form-group mb-3">
                                <label for="notes" class="form-label"><strong>Notes</strong></label>
                                <textarea class="form-control" id="notes" name="notes" rows="2"
                                          placeholder="Booking info, tickets, reminders..."></textarea>
                            </div>
 
                            <div class="d-flex gap-2">
                                <button type="submit" class="btn btn-success">Add Activity</button>
                                <a href="javascript:history.back()" class="btn btn-secondary">Cancel</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
 
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
 
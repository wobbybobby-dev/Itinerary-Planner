<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${trip.tripName} - Trip Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar navbar-dark" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
        <div class="container-fluid">
            <span class="navbar-brand">✈️ Trip Details</span>
            <div>
                <a href="${pageContext.request.contextPath}/trips" class="btn btn-light btn-sm me-2">← Back to Trips</a>
            </div>
        </div>
    </nav>
 
    <div class="container mt-4">
        <c:if test="${not empty trip}">
            <!-- Trip Header Card -->
            <div class="card mb-4" style="border-left: 5px solid #667eea;">
                <div class="card-body">
                    <h2 class="card-title">${trip.tripName}</h2>
                    <span class="badge bg-primary">${trip.destination}</span>
                    
                    <hr>
                    
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <p><strong>📅 Start Date:</strong> <fmt:formatDate value="${trip.startDate}" pattern="MMMM dd, yyyy"/></p>
                            <p><strong>📅 End Date:</strong> <fmt:formatDate value="${trip.endDate}" pattern="MMMM dd, yyyy"/></p>
                        </div>
                        <div class="col-md-6">
                            <p><strong>⏱️ Duration:</strong> <span id="duration">${trip.durationDays} days</span></p>
                            <p><strong>📌 Status:</strong> 
                                <span class="badge ${trip.completed ? 'bg-success' : 'bg-warning'}">
                                    ${trip.status}
                                </span>
                            </p>
                        </div>
                    </div>
                    
                    <p><strong>📝 Description:</strong></p>
                    <p>${trip.description}</p>
                    
                    <hr>
                    
                    <div class="d-flex gap-2">
                        <a href="${pageContext.request.contextPath}/trips/${trip.tripId}/edit" class="btn btn-warning btn-sm">Edit Trip</a>
                        <form method="post" action="${pageContext.request.contextPath}/trips/${trip.tripId}/delete" style="display:inline;">
                            <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Delete this trip?')">Delete Trip</button>
                        </form>
                    </div>
                </div>
            </div>
 
            <!-- Itinerary Section -->
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h5 class="mb-0">📋 Itinerary</h5>
                </div>
                <div class="card-body">
                    <c:if test="${empty itinerary}">
                        <p class="text-muted text-center py-4">No itinerary planned yet.</p>
                        <div class="text-center">
                            <a href="${pageContext.request.contextPath}/trips/${trip.tripId}/itinerary/create" class="btn btn-primary btn-sm">+ Add Itinerary</a>
                        </div>
                    </c:if>
                    
                    <c:forEach var="day" items="${itinerary}">
                        <div class="card mb-3" style="border-left: 4px solid #764ba2;">
                            <div class="card-header">
                                <h6 class="mb-0">📅 Day ${day.dayNumber} - <fmt:formatDate value="${day.activityDate}" pattern="EEEE, MMMM dd"/></h6>
                            </div>
                            <div class="card-body">
                                <c:if test="${empty day.activities}">
                                    <p class="text-muted">No activities scheduled for this day.</p>
                                </c:if>
                                
                                <c:forEach var="activity" items="${day.activities}">
                                    <div class="mb-3 p-3" style="background-color: #f8f9fa; border-radius: 6px;">
                                        <div class="d-flex justify-content-between align-items-start">
                                            <div>
                                                <h6 class="mb-1">${activity.activityName}</h6>
                                                <p class="mb-1 text-muted small">
                                                    📍 ${activity.location}
                                                </p>
                                                <p class="mb-1 text-muted small">
                                                    ⏰ ${activity.startTime} - ${activity.endTime}
                                                </p>
                                                <c:if test="${activity.cost > 0}">
                                                    <p class="mb-1 text-muted small">
                                                        💰 $${activity.cost}
                                                    </p>
                                                </c:if>
                                                <c:if test="${not empty activity.description}">
                                                    <p class="mb-0 small">${activity.description}</p>
                                                </c:if>
                                            </div>
                                            <div>
                                                <a href="${pageContext.request.contextPath}/activities/${activity.activityId}/edit" class="btn btn-sm btn-warning">Edit</a>
                                                <form method="post" action="${pageContext.request.contextPath}/activities/${activity.activityId}/delete" style="display:inline;">
                                                    <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Delete activity?')">Delete</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                                
                                <a href="${pageContext.request.contextPath}/activities/create?itinerary=${day.itineraryId}" class="btn btn-sm btn-success">+ Add Activity</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
        
        <c:if test="${empty trip}">
            <div class="alert alert-danger" role="alert">
                Trip not found.
            </div>
        </c:if>
    </div>
 
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
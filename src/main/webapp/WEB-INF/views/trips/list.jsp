// File: src/main/webapp/WEB-INF/views/trips/list.jsp

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Trips</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/travelapp/css/style.css">
</head>
<body>
    <nav class="navbar">
        <div class="container">
            <div class="d-flex justify-content-between align-items-center w-100">
                <span class="navbar-brand">✈️ Travel Itinerary</span>
                <a href="/travelapp/trips/create" class="btn btn-light btn-sm">+ New Trip</a>
            </div>
        </div>
    </nav>
 
    <div class="container my-5">
        <h2 class="mb-4">My Trips</h2>
 
        <c:if test="${empty trips}">
            <div class="alert alert-info">
                No trips yet. <a href="/travelapp/trips/create">Create your first trip!</a>
            </div>
        </c:if>
 
        <div class="grid">
            <c:forEach var="trip" items="${trips}">
                <div class="card trip-card">
                    <h5 class="card-title">${trip.tripName}</h5>
                    <span class="badge">${trip.destination}</span>
                    <p class="text-muted">
                        ${trip.startDate} to ${trip.endDate}
                        <br>${trip.description}
                    </p>
                    <div class="d-flex gap-2">
                        <a href="/travelapp/trips/${trip.tripId}" class="btn btn-primary btn-sm">View</a>
                        <a href="/travelapp/trips/${trip.tripId}/edit" class="btn btn-warning btn-sm">Edit</a>
                        <form method="post" action="/travelapp/trips/${trip.tripId}/delete" style="display:inline;">
                            <button type="submit" class="btn btn-danger btn-sm" 
                                   onclick="return confirm('Delete this trip?')">Delete</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
 
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="/travelapp/js/main.js"></script>
</body>
</html>
 
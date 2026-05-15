// File: src/main/webapp/WEB-INF/views/home.jsp

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Travel Itinerary App</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/travelapp/css/style.css">
</head>
<body>
    <nav class="navbar">
        <div class="container">
            <span class="navbar-brand">✈️ ${appName}</span>
        </div>
    </nav>
 
    <div class="container">
        <div class="hero-section text-center py-5">
            <h1>Plan Your Perfect Trip</h1>
            <p class="lead">Organize your travel itinerary with ease</p>
            <a href="/travelapp/trips" class="btn btn-primary btn-lg">View Trips</a>
            <a href="/travelapp/trips/create" class="btn btn-secondary btn-lg">Create New Trip</a>
        </div>
    </div>
 
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="/travelapp/js/main.js"></script>
</body>
</html>
 

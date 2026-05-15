// File: src/main/webapp/WEB-INF/views/trips/create.jsp

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create New Trip</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar navbar-dark" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
        <div class="container-fluid">
            <span class="navbar-brand">✈️ Create Trip</span>
            <a href="${pageContext.request.contextPath}/trips" class="btn btn-light btn-sm">← Back</a>
        </div>
    </nav>
 
    <div class="container mt-4 mb-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">Plan Your Adventure</h5>
                    </div>
                    <div class="card-body">
                        <form method="post" action="${pageContext.request.contextPath}/trips/create">
                            <div class="form-group mb-3">
                                <label for="tripName" class="form-label"><strong>Trip Name</strong></label>
                                <input type="text" class="form-control" id="tripName" name="tripName" 
                                       placeholder="e.g., Summer in Europe" required>
                            </div>
 
                            <div class="form-group mb-3">
                                <label for="destination" class="form-label"><strong>Destination</strong></label>
                                <input type="text" class="form-control" id="destination" name="destination" 
                                       placeholder="e.g., Paris, France" required>
                            </div>
 
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group mb-3">
                                        <label for="startDate" class="form-label"><strong>Start Date</strong></label>
                                        <input type="date" class="form-control" id="startDate" name="startDate" required>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group mb-3">
                                        <label for="endDate" class="form-label"><strong>End Date</strong></label>
                                        <input type="date" class="form-control" id="endDate" name="endDate" required>
                                    </div>
                                </div>
                            </div>
 
                            <div class="form-group mb-3">
                                <label for="description" class="form-label"><strong>Description</strong></label>
                                <textarea class="form-control" id="description" name="description" rows="4"
                                          placeholder="What are your travel goals? Where will you go? What will you do?"></textarea>
                            </div>
 
                            <div class="d-flex gap-2">
                                <button type="submit" class="btn btn-primary">Create Trip</button>
                                <a href="${pageContext.request.contextPath}/trips" class="btn btn-secondary">Cancel</a>
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
// File: src/main/webapp/WEB-INF/views/budgets/summary.jsp

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Budget Summary</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar navbar-dark" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
        <div class="container-fluid">
            <span class="navbar-brand">✈️ Budget Summary</span>
            <a href="${pageContext.request.contextPath}/trips" class="btn btn-light btn-sm">← Back to Trips</a>
        </div>
    </nav>
 
    <div class="container mt-4 mb-4">
        <c:if test="${not empty budgets}">
            <h2 class="mb-4">Trip: ${tripName}</h2>
            
            <!-- Budget Overview Cards -->
            <div class="row mb-4">
                <div class="col-md-3">
                    <div class="card text-center">
                        <div class="card-body">
                            <h6 class="card-title text-muted">Total Budget</h6>
                            <h3 class="text-primary">$<fmt:formatNumber value="${totalBudget}" minFractionDigits="2" maxFractionDigits="2"/></h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center">
                        <div class="card-body">
                            <h6 class="card-title text-muted">Total Spent</h6>
                            <h3 class="text-danger">$<fmt:formatNumber value="${totalSpent}" minFractionDigits="2" maxFractionDigits="2"/></h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center">
                        <div class="card-body">
                            <h6 class="card-title text-muted">Remaining</h6>
                            <h3 class="text-success">$<fmt:formatNumber value="${totalBudget - totalSpent}" minFractionDigits="2" maxFractionDigits="2"/></h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center">
                        <div class="card-body">
                            <h6 class="card-title text-muted">Usage</h6>
                            <h3 class="text-warning"><fmt:formatNumber value="${(totalSpent / totalBudget) * 100}" maxFractionDigits="1"/>%</h3>
                        </div>
                    </div>
                </div>
            </div>
 
            <!-- Budget Details Table -->
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h5 class="mb-0">Budget Breakdown by Category</h5>
                </div>
                <div class="card-body">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Category</th>
                                <th class="text-end">Budgeted</th>
                                <th class="text-end">Spent</th>
                                <th class="text-end">Remaining</th>
                                <th class="text-end">Usage %</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="budget" items="${budgets}">
                                <c:set var="remainingAmount" value="${budget.budgetedAmount - budget.actualAmount}"/>
                                <c:set var="percentageUsed" value="${(budget.actualAmount / budget.budgetedAmount) * 100}"/>
                                <tr>
                                    <td>${budget.category}</td>
                                    <td class="text-end">$<fmt:formatNumber value="${budget.budgetedAmount}" minFractionDigits="2" maxFractionDigits="2"/></td>
                                    <td class="text-end">$<fmt:formatNumber value="${budget.actualAmount}" minFractionDigits="2" maxFractionDigits="2"/></td>
                                    <td class="text-end">$<fmt:formatNumber value="${remainingAmount}" minFractionDigits="2" maxFractionDigits="2"/></td>
                                    <td class="text-end"><fmt:formatNumber value="${percentageUsed}" maxFractionDigits="1"/>%</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${remainingAmount < 0}">
                                                <span class="badge bg-danger">Over</span>
                                            </c:when>
                                            <c:when test="${percentageUsed >= 80}">
                                                <span class="badge bg-warning">High</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-success">OK</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
 
        <c:if test="${empty budgets}">
            <div class="alert alert-info" role="alert">
                No budget data available for this trip.
            </div>
        </c:if>
    </div>
 
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
 
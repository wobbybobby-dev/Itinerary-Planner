// File: src/main/java/com/travelapp/model/Trip.java
package com.travelapp.model;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trip implements Serializable {
    private int tripId;
    private int userId;
    private String tripName;
    private String destination;
    private Date startDate;
    private Date endDate;
    private String description;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
 
    public Trip(int userId, String tripName, String destination, Date startDate, Date endDate) {
        this.userId = userId;
        this.tripName = tripName;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = "PLANNED";
    }
 
    public long getDurationDays() {
        if (startDate != null && endDate != null) {
            return ChronoUnit.DAYS.between(startDate.toLocalDate(), endDate.toLocalDate()) + 1;
        }
        return 0;
    }
 
    public boolean isUpcoming() {
        return new Date(System.currentTimeMillis()).before(startDate);
    }
 
    public boolean isCompleted() {
        return "COMPLETED".equals(status);
    }
}
package com.travelapp.model;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity implements Serializable {
    private int activityId;
    private int itineraryId;
    private String activityName;
    private String activityType;
    private String description;
    private String location;
    private Time startTime;
    private Time endTime;
    private BigDecimal cost;
    private String bookingReference;
    private String notes;
 
    public Activity(int itineraryId, String activityName, String location) {
        this.itineraryId = itineraryId;
        this.activityName = activityName;
        this.location = location;
    }
 
    public String getDurationHours() {
        if (startTime != null && endTime != null) {
            long diffMs = endTime.getTime() - startTime.getTime();
            long hours = diffMs / (60 * 60 * 1000);
            return hours + " hours";
        }
        return "Not specified";
    }
 
    public boolean hasBooking() {
        return bookingReference != null && !bookingReference.isEmpty();
    }
}
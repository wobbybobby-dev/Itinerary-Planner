// File: src/main/java/com/travelapp/model/Itinerary.java
package com.travelapp.model;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Itinerary implements Serializable {
    private int itineraryId;
    private int tripId;
    private Date activityDate;
    private int dayNumber;
    private String notes;
    private Timestamp createdAt;
    private Timestamp updatedAt;
 
    public Itinerary(int tripId, Date activityDate, int dayNumber) {
        this.tripId = tripId;
        this.activityDate = activityDate;
        this.dayNumber = dayNumber;
    }
}
// File: src/main/java/com/travelapp/model/Destination.java

package com.travelapp.model;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Destination implements Serializable {
    private int destinationId;
    private String name;
    private String country;
    private String description;
    private String bestTimeToVisit;
    private String currency;
    private String language;
    private double latitude;
    private double longitude;
    private String imageUrl;
 
    public Destination(String name, String country) {
        this.name = name;
        this.country = country;
    }
 
    public String getFullLocation() {
        return name + ", " + country;
    }
}
 
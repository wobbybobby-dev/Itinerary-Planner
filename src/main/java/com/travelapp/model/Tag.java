// File: src/main/java/com/travelapp/model/tag.java
package com.travelapp.model;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag implements Serializable {
    private int tagId;
    private String tagName;
    private String color;
 
    public Tag(String tagName) {
        this.tagName = tagName;
        this.color = "#000000";
    }
}
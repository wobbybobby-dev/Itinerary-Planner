package com.travelapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private int userId;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public User(String username, String email, String password, String fullName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }
}
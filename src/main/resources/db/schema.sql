-- Create Travel Itinerary Database
CREATE DATABASE IF NOT EXISTS travel_itinerary;
USE travel_itinerary;

-- Users Table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);
-- Trips Table
CREATE TABLE IF NOT EXISTS trips (
    trip_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    trip_name VARCHAR(200) NOT NULL,
    destination VARCHAR(200) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    description TEXT,
    status VARCHAR(50) DEFAULT 'PLANNED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_start_date (start_date)
);

-- Itineraries Table
CREATE TABLE IF NOT EXISTS itineraries (
    itinerary_id INT PRIMARY KEY AUTO_INCREMENT,
    trip_id INT NOT NULL,
    activity_date DATE NOT NULL,
    day_number INT NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (trip_id) REFERENCES trips(trip_id) ON DELETE CASCADE,
    UNIQUE KEY unique_trip_day (trip_id, day_number),
    INDEX idx_activity_date (activity_date)
);

-- Activities Table
CREATE TABLE IF NOT EXISTS activities (
    activity_id INT PRIMARY KEY AUTO_INCREMENT,
    itinerary_id INT NOT NULL,
    activity_name VARCHAR(200) NOT NULL,
    activity_type VARCHAR(100),
    description TEXT,
    location VARCHAR(200),
    start_time TIME,
    end_time TIME,
    cost DECIMAL(10, 2),
    booking_reference VARCHAR(100),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (itinerary_id) REFERENCES itineraries(itinerary_id) ON DELETE CASCADE,
    INDEX idx_itinerary_id (itinerary_id),
    INDEX idx_activity_type (activity_type)
);

-- Tags Table
CREATE TABLE IF NOT EXISTS tags (
    tag_id INT PRIMARY KEY AUTO_INCREMENT,
    tag_name VARCHAR(100) UNIQUE NOT NULL,
    color VARCHAR(7)
);

-- Activity Tags Junction Table (Many-to-Many)
CREATE TABLE IF NOT EXISTS activity_tags (
    activity_id INT NOT NULL,
    tag_id INT NOT NULL,
    PRIMARY KEY (activity_id, tag_id),
    FOREIGN KEY (activity_id) REFERENCES activities(activity_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE
);

-- Budget Table
CREATE TABLE IF NOT EXISTS budget (
    budget_id INT PRIMARY KEY AUTO_INCREMENT,
    trip_id INT NOT NULL,
    category VARCHAR(100) NOT NULL,
    budgeted_amount DECIMAL(10, 2) NOT NULL,
    actual_amount DECIMAL(10, 2) DEFAULT 0,
    currency VARCHAR(3) DEFAULT 'USD',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (trip_id) REFERENCES trips(trip_id) ON DELETE CASCADE,
    INDEX idx_trip_id (trip_id)
);

-- Create indexes for performance
CREATE INDEX idx_trips_user_start ON trips(user_id, start_date);
CREATE INDEX idx_activities_date ON activities(itinerary_id);
CREATE INDEX idx_budget_trip ON budget(trip_id);
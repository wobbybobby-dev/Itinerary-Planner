// File: src/test/java/com/travelapp/service/TripServiceTest.java
//this one from chatgpt

package com.travelapp.service;

import com.travelapp.dao.TripDAO;
import com.travelapp.model.Trip;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {

    @Mock
    private TripDAO tripDAO;

    @InjectMocks
    private TripService tripService;

    private Trip testTrip;

    @Before
    public void setUp() {
        testTrip = new Trip();
        testTrip.setTripId(1);
        testTrip.setUserId(1);
        testTrip.setTripName("Paris Trip");
        testTrip.setDestination("Paris, France");
        testTrip.setStartDate(Date.valueOf("2027-05-15"));
        testTrip.setEndDate(Date.valueOf("2027-05-22"));
        testTrip.setDescription("Vacation in Paris");
        testTrip.setStatus("PLANNED");
    }

    @Test
    public void testCreateTrip() {
        when(tripDAO.createTrip(testTrip)).thenReturn(testTrip);

        Trip createdTrip = tripService.createTrip(testTrip);

        assertNotNull("Created trip should not be null", createdTrip);
        assertEquals("Trip name should match",
                "Paris Trip", createdTrip.getTripName());
        assertEquals("Status should be PLANNED",
                "PLANNED", createdTrip.getStatus());

        verify(tripDAO, times(1)).createTrip(testTrip);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateTripWithEmptyName() {
        testTrip.setTripName("");
        tripService.createTrip(testTrip);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateTripWithInvalidDates() {
        testTrip.setStartDate(Date.valueOf("2027-06-01"));
        testTrip.setEndDate(Date.valueOf("2027-05-01"));

        tripService.createTrip(testTrip);
    }

    @Test
    public void testGetTripById() {
        when(tripDAO.getTripById(1)).thenReturn(testTrip);

        Trip trip = tripService.getTripById(1);

        assertNotNull("Trip should not be null", trip);
        assertEquals("Trip ID should match", 1, trip.getTripId());
        assertEquals("Destination should match",
                "Paris, France", trip.getDestination());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTripByIdNotFound() {
        when(tripDAO.getTripById(999)).thenReturn(null);

        tripService.getTripById(999);
    }

    @Test
    public void testGetTripsByUserId() {
        List<Trip> trips = new ArrayList<>();
        trips.add(testTrip);

        when(tripDAO.getTripsByUserId(1)).thenReturn(trips);

        List<Trip> result = tripService.getTripsByUserId(1);

        assertNotNull("Trips list should not be null", result);
        assertFalse("Trips list should not be empty", result.isEmpty());
        assertEquals("Should contain 1 trip", 1, result.size());
    }

    @Test
    public void testUpdateTrip() {
        Trip updatedTrip = new Trip();
        updatedTrip.setTripId(1);
        updatedTrip.setTripName("Updated Paris Trip");

        when(tripDAO.getTripById(1)).thenReturn(testTrip);
        when(tripDAO.updateTrip(any(Trip.class))).thenReturn(true);

        Trip result = tripService.updateTrip(updatedTrip);

        assertEquals("Trip name should be updated",
                "Updated Paris Trip", result.getTripName());

        verify(tripDAO, times(1)).updateTrip(any(Trip.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateTripWithoutId() {
        Trip trip = new Trip(); // tripId defaults to 0
        tripService.updateTrip(trip);
    }

    @Test
    public void testDeleteTrip() {
        when(tripDAO.getTripById(1)).thenReturn(testTrip);
        when(tripDAO.deleteTrip(1)).thenReturn(true);

        boolean success = tripService.deleteTrip(1);

        assertTrue("Delete should succeed", success);
        verify(tripDAO, times(1)).deleteTrip(1);
    }

    @Test
    public void testCompleteTrip() {
        when(tripDAO.getTripById(1)).thenReturn(testTrip);
        when(tripDAO.updateTrip(any(Trip.class))).thenReturn(true);

        Trip completedTrip = tripService.completeTrip(1);

        assertEquals("Status should be COMPLETED",
                "COMPLETED", completedTrip.getStatus());

        verify(tripDAO, times(1)).updateTrip(any(Trip.class));
    }

    @Test
    public void testValidateTripDates() {
        Date start = Date.valueOf("2027-05-15");
        Date end = Date.valueOf("2027-05-22");

        boolean valid = tripService.validateTripDates(start, end);

        assertTrue("Dates should be valid", valid);
    }

    @Test
    public void testCalculateTripDuration() {
        when(tripDAO.getTripById(1)).thenReturn(testTrip);

        long duration = tripService.calculateTripDuration(1);

        assertEquals("Duration should be 8 days", 8, duration);
    }
}
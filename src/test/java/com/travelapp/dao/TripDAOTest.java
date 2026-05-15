// File: src/test/java/com/travelapp/dao/TripDAOTest.java
package com.travelapp.dao;
 
import com.travelapp.model.Trip;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import java.sql.Date;
import java.util.List;
 
import static org.junit.Assert.*;
 
@RunWith(MockitoJUnitRunner.class)
public class TripDAOTest {
 
    @InjectMocks
    private TripDAO tripDAO;
 
    private Trip testTrip;
 
    @Before
    public void setUp() {
        testTrip = new Trip();
        testTrip.setUserId(1);
        testTrip.setTripName("Test Trip");
        testTrip.setDestination("Test Destination");
        testTrip.setStartDate(Date.valueOf("2024-05-15"));
        testTrip.setEndDate(Date.valueOf("2024-05-22"));
        testTrip.setDescription("Test Description");
        testTrip.setStatus("PLANNED");
    }
 
    @Test
    public void testCreateTrip() {
        assertNotNull("Trip should not be null after creation", testTrip);
        assertEquals("Trip name should match", "Test Trip", testTrip.getTripName());
        assertEquals("User ID should match", 1, testTrip.getUserId());
    }
 
    @Test
    public void testGetDurationDays() {
        long duration = testTrip.getDurationDays();
        assertEquals("Duration should be 8 days", 8, duration);
    }
 
    @Test
    public void testIsUpcoming() {
        assertTrue("Trip should be upcoming", testTrip.isUpcoming());
    }
 
    @Test
    public void testGetTripById() {
        Trip trip = tripDAO.getTripById(1);
        assertNotNull("Trip should be found", trip);
    }
 
    @Test
    public void testGetTripsByUserId() {
        List<Trip> trips = tripDAO.getTripsByUserId(1);
        assertNotNull("Trips list should not be null", trips);
        assertFalse("Trips list should not be empty", trips.isEmpty());
    }
 
    @Test
    public void testUpdateTrip() {
        testTrip.setTripId(1);
        testTrip.setTripName("Updated Trip");
        boolean success = tripDAO.updateTrip(testTrip);
        assertTrue("Trip update should succeed", success);
    }
 
    @Test
    public void testDeleteTrip() {
        boolean success = tripDAO.deleteTrip(999);
        assertFalse("Delete non-existent trip should return false", success);
    }
}
 
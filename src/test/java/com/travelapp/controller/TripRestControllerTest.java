// File: src/test/java/com/travelapp/api/controller/TripRestControllerTest.java
package com.travelapp.api.controller;
 
import com.travelapp.dao.TripDAO;
import com.travelapp.model.Trip;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
 
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
 
@RunWith(MockitoJUnitRunner.class)
public class TripRestControllerTest {
 
    @Mock
    private TripDAO tripDAO;
 
    @InjectMocks
    private TripRestController controller;
 
    private Trip mockTrip;
 
    @Before
    public void setUp() {
        mockTrip = new Trip(1, "Paris Trip", "Paris, France", 
                           Date.valueOf("2024-05-15"), Date.valueOf("2024-05-22"));
        mockTrip.setTripId(1);
    }
 
    @Test
    public void testGetTripsByUser() {
        List<Trip> trips = new ArrayList<>();
        trips.add(mockTrip);
 
        when(tripDAO.getTripsByUserId(1)).thenReturn(trips);
 
        ResponseEntity<List<Trip>> response = controller.getTripsByUser(1);
 
        assertEquals("Status code should be 200", 200, response.getStatusCodeValue());
        assertFalse("Trips list should not be empty", response.getBody().isEmpty());
        assertEquals("Should return 1 trip", 1, response.getBody().size());
    }
 
    @Test
    public void testGetTrip() {
        when(tripDAO.getTripById(1)).thenReturn(mockTrip);
 
        ResponseEntity<Trip> response = controller.getTrip(1);
 
        assertEquals("Status code should be 200", 200, response.getStatusCodeValue());
        assertEquals("Trip name should match", "Paris Trip", response.getBody().getTripName());
    }
 
    @Test
    public void testGetTripNotFound() {
        when(tripDAO.getTripById(999)).thenReturn(null);
 
        ResponseEntity<Trip> response = controller.getTrip(999);
 
        assertEquals("Status code should be 404", 404, response.getStatusCodeValue());
    }
 
    @Test
    public void testCreateTrip() {
        when(tripDAO.createTrip(mockTrip)).thenReturn(mockTrip);
 
        ResponseEntity<Trip> response = controller.createTrip(mockTrip);
 
        assertEquals("Status code should be 201", 201, response.getStatusCodeValue());
        assertEquals("Trip ID should match", 1, response.getBody().getTripId());
    }
 
    @Test
    public void testUpdateTrip() {
        when(tripDAO.updateTrip(mockTrip)).thenReturn(true);
 
        ResponseEntity<String> response = controller.updateTrip(1, mockTrip);
 
        assertEquals("Status code should be 200", 200, response.getStatusCodeValue());
        assertTrue("Response should contain success message", 
                  response.getBody().contains("successfully"));
    }
 
    @Test
    public void testDeleteTrip() {
        when(tripDAO.deleteTrip(1)).thenReturn(true);
 
        ResponseEntity<String> response = controller.deleteTrip(1);
 
        assertEquals("Status code should be 200", 200, response.getStatusCodeValue());
        assertTrue("Response should contain success message", 
                  response.getBody().contains("successfully"));
    }
}
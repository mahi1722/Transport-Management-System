package dao;

import dao.TransportManagementService;
import dao.TransportManagementServiceImpl;
import entity.Vehicle;
import entity.Booking;
import exception.VehicleNotFoundException;
import exception.TripNotFoundException;
import exception.BookingNotFoundException;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

public class TransportManagementServiceImplTest {

    private TransportManagementServiceImpl service;

    @Before
    public void setUp() {
        // Initialize the actual service
        service = new TransportManagementServiceImpl();
    }

    @Test
    public void testAddVehicle() {
        Vehicle vehicle = new Vehicle(0, "Test Model", 10, "Test Type", "Available");
        boolean added = service.addVehicle(vehicle);
        assertTrue("Vehicle should be added successfully.", added);
    }

    @Test
    public void testUpdateVehicle() {
        Vehicle vehicle = new Vehicle(1, "Test Model", 10, "Test Type", "Available");
        service.addVehicle(vehicle); // Add the vehicle first

        Vehicle updatedVehicle = new Vehicle(1, "Updated Model", 12, "Updated Type", "Available");
        try {
            boolean updated = service.updateVehicle(updatedVehicle);
            assertTrue("Vehicle should be updated successfully.", updated);
        } catch (VehicleNotFoundException e) {
            fail("VehicleNotFoundException should not be thrown for an existing vehicle.");
        }
    }

    

    @Test
    public void testScheduleTrip() {
        Vehicle vehicle = new Vehicle(1, "Test Model", 10, "Test Type", "Available");
        service.addVehicle(vehicle); // Add the vehicle first

        try {
            boolean scheduled = service.scheduleTrip(1, 1, "2024-10-15 08:00", "2024-10-15 12:00");
            assertTrue("Trip should be scheduled successfully.", scheduled);
        } catch (VehicleNotFoundException e) {
            fail("VehicleNotFoundException should not be thrown for an existing vehicle.");
        }
    }

    

    @Test
    public void testBookTrip() throws VehicleNotFoundException {
        Vehicle vehicle = new Vehicle(1, "Test Model", 10, "Test Type", "Available");
        service.addVehicle(vehicle); // Add the vehicle first

        try {
            service.scheduleTrip(1, 1, "2024-10-15 08:00", "2024-10-15 12:00");
            boolean booked = service.bookTrip(1, 1, "2024-10-14 14:00");
            assertTrue("Trip should be booked successfully.", booked);
        } catch (TripNotFoundException e) {
            fail("TripNotFoundException should not be thrown for an existing trip.");
        }
    }

    

    @Test
    public void testGetBookingsByPassenger() throws VehicleNotFoundException {
        Vehicle vehicle = new Vehicle(1, "Test Model", 10, "Test Type", "Available");
        service.addVehicle(vehicle); // Add the vehicle first

        try {
            service.scheduleTrip(1, 1, "2024-10-15 08:00", "2024-10-15 12:00");
            service.bookTrip(1, 1, "2024-10-14 14:00");
            List<Booking> bookings = service.getBookingsByPassenger(1);
            assertFalse("There should be bookings for the passenger.", bookings.isEmpty());
        } catch (TripNotFoundException e) {
            fail("TripNotFoundException should not be thrown for an existing trip.");
        }
    }

    @Test
    public void testGetBookingsByTrip() throws VehicleNotFoundException {
        Vehicle vehicle = new Vehicle(1, "Test Model", 10, "Test Type", "Available");
        service.addVehicle(vehicle); // Add the vehicle first

        try {
            service.scheduleTrip(1, 1, "2024-10-15 08:00", "2024-10-15 12:00");
            service.bookTrip(1, 1, "2024-10-14 14:00");
            List<Booking> bookings = service.getBookingsByTrip(1);
            assertFalse("There should be bookings for the trip.", bookings.isEmpty());
        } catch (TripNotFoundException e) {
            fail("TripNotFoundException should not be thrown for an existing trip.");
        }
    }

    @After
    public void tearDown() {
        // No explicit cleanup required if using an in-memory or test database
    }
}

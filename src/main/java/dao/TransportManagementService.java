package dao;

import entity.*;
import exception.VehicleNotFoundException;
import exception.TripNotFoundException;
import exception.BookingNotFoundException;
import java.util.List;

public interface TransportManagementService {

    boolean addVehicle(Vehicle vehicle);

    boolean updateVehicle(Vehicle vehicle) throws VehicleNotFoundException;

    boolean deleteVehicle(int vehicleID) throws VehicleNotFoundException;

    boolean scheduleTrip(int vehicleID, int routeID, String departureDate, String arrivalDate) throws VehicleNotFoundException;

    boolean cancelTrip(int tripID) throws TripNotFoundException;

    boolean bookTrip(int tripID, int passengerID, String bookingDate) throws TripNotFoundException;

    boolean cancelBooking(int bookingID) throws BookingNotFoundException;

    List<Booking> getBookingsByPassenger(int passengerID);

    List<Booking> getBookingsByTrip(int tripID);
}

package dao;

import entity.*;
import exception.VehicleNotFoundException;
import exception.TripNotFoundException;
import exception.BookingNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DBConnectionUtil;

public class TransportManagementServiceImpl implements TransportManagementService {

    private Connection connection;

    public TransportManagementServiceImpl() {
        this.connection = DBConnectionUtil.getConnection();
    }

    @Override
    public boolean addVehicle(Vehicle vehicle) {
        String query = "INSERT INTO Vehicles (model, capacity, type, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, vehicle.getModel());
            statement.setDouble(2, vehicle.getCapacity());
            statement.setString(3, vehicle.getType());
            statement.setString(4, vehicle.getStatus());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateVehicle(Vehicle vehicle) throws VehicleNotFoundException {
        String query = "UPDATE Vehicles SET model = ?, capacity = ?, type = ?, status = ? WHERE vehicleID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, vehicle.getModel());
            statement.setDouble(2, vehicle.getCapacity());
            statement.setString(3, vehicle.getType());
            statement.setString(4, vehicle.getStatus());
            statement.setInt(5, vehicle.getVehicleID());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new VehicleNotFoundException("Vehicle with ID " + vehicle.getVehicleID() + " not found.");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteVehicle(int vehicleID) throws VehicleNotFoundException {
        String query = "DELETE FROM Vehicles WHERE vehicleID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, vehicleID);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new VehicleNotFoundException("Vehicle with ID " + vehicleID + " not found.");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean scheduleTrip(int vehicleID, int routeID, String departureDate, String arrivalDate) throws VehicleNotFoundException {
        if (!vehicleExists(vehicleID)) {
            throw new VehicleNotFoundException("Vehicle with ID " + vehicleID + " not found.");
        }
        String query = "INSERT INTO Trips (vehicleID, routeID, departureDate, arrivalDate, status, tripType, maxPassengers) VALUES (?, ?, ?, ?, 'Scheduled', 'Passenger', 0)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, vehicleID);
            statement.setInt(2, routeID);
            statement.setString(3, departureDate);
            statement.setString(4, arrivalDate);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean cancelTrip(int tripID) throws TripNotFoundException {
        String query = "DELETE FROM Trips WHERE tripID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tripID);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new TripNotFoundException("Trip with ID " + tripID + " not found.");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean bookTrip(int tripID, int passengerID, String bookingDate) throws TripNotFoundException {
        if (!tripExists(tripID)) {
            throw new TripNotFoundException("Trip with ID " + tripID + " not found.");
        }
        String query = "INSERT INTO Bookings (tripID, passengerID, bookingDate, status) VALUES (?, ?, ?, 'Confirmed')";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tripID);
            statement.setInt(2, passengerID);
            statement.setString(3, bookingDate);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean cancelBooking(int bookingID) throws BookingNotFoundException {
        String query = "DELETE FROM Bookings WHERE bookingID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookingID);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new BookingNotFoundException("Booking with ID " + bookingID + " not found.");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Booking> getBookingsByPassenger(int passengerID) {
        String query = "SELECT * FROM Bookings WHERE passengerID = ?";
        List<Booking> bookings = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, passengerID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setBookingID(resultSet.getInt("bookingID"));
                booking.setTripID(resultSet.getInt("tripID"));
                booking.setPassengerID(resultSet.getInt("passengerID"));
                booking.setBookingDate(resultSet.getDate("bookingDate"));
                booking.setStatus(resultSet.getString("status"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> getBookingsByTrip(int tripID) {
        String query = "SELECT * FROM Bookings WHERE tripID = ?";
        List<Booking> bookings = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tripID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setBookingID(resultSet.getInt("bookingID"));
                booking.setTripID(resultSet.getInt("tripID"));
                booking.setPassengerID(resultSet.getInt("passengerID"));
                booking.setBookingDate(resultSet.getDate("bookingDate"));
                booking.setStatus(resultSet.getString("status"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    private boolean vehicleExists(int vehicleID) {
        String query = "SELECT 1 FROM Vehicles WHERE vehicleID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, vehicleID);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean tripExists(int tripID) {
        String query = "SELECT 1 FROM Trips WHERE tripID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tripID);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

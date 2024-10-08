package main;

import dao.TransportManagementService;
import dao.TransportManagementServiceImpl;
import entity.Vehicle;
import entity.Booking;
import exception.VehicleNotFoundException;
import exception.TripNotFoundException;
import exception.BookingNotFoundException;

import java.util.List;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TransportManagementService service = new TransportManagementServiceImpl();

        while (true) {
            System.out.println("===== Transport Management System =====");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Update Vehicle");
            System.out.println("3. Delete Vehicle");
            System.out.println("4. Schedule Trip");
            System.out.println("5. Cancel Trip");
            System.out.println("6. Book Trip");
            System.out.println("7. Cancel Booking");
            System.out.println("8. Get Bookings by Passenger");
            System.out.println("9. Get Bookings by Trip");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            try {
                switch (choice) {
                    case 1: // Add Vehicle
                        System.out.print("Enter Model: ");
                        String model = scanner.nextLine();
                        System.out.print("Enter Capacity: ");
                        double capacity = scanner.nextDouble();
                        scanner.nextLine(); // Consume the newline
                        System.out.print("Enter Type: ");
                        String type = scanner.nextLine();
                        System.out.print("Enter Status: ");
                        String status = scanner.nextLine();

                        Vehicle vehicle = new Vehicle(0, model, capacity, type, status);
                        boolean added = service.addVehicle(vehicle);
                        if (added) {
                            System.out.println("Vehicle added successfully!");
                        } else {
                            System.out.println("Failed to add vehicle.");
                        }
                        break;

                    case 2: // Update Vehicle
                        System.out.print("Enter Vehicle ID to update: ");
                        int updateID = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline
                        System.out.print("Enter new Model: ");
                        String newModel = scanner.nextLine();
                        System.out.print("Enter new Capacity: ");
                        double newCapacity = scanner.nextDouble();
                        scanner.nextLine(); // Consume the newline
                        System.out.print("Enter new Type: ");
                        String newType = scanner.nextLine();
                        System.out.print("Enter new Status: ");
                        String newStatus = scanner.nextLine();

                        Vehicle updatedVehicle = new Vehicle(updateID, newModel, newCapacity, newType, newStatus);
                        boolean updated = service.updateVehicle(updatedVehicle);
                        if (updated) {
                            System.out.println("Vehicle updated successfully!");
                        }
                        break;

                    case 3: // Delete Vehicle
                        System.out.print("Enter Vehicle ID to delete: ");
                        int vehicleID = scanner.nextInt();
                        boolean deleted = service.deleteVehicle(vehicleID);
                        if (deleted) {
                            System.out.println("Vehicle deleted successfully!");
                        }
                        break;

                    case 4: // Schedule Trip
                        System.out.print("Enter Vehicle ID: ");
                        int vehicleForTrip = scanner.nextInt();
                        System.out.print("Enter Route ID: ");
                        int routeID = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline
                        System.out.print("Enter Departure Date (yyyy-MM-dd HH:mm): ");
                        String departureDate = scanner.nextLine();
                        System.out.print("Enter Arrival Date (yyyy-MM-dd HH:mm): ");
                        String arrivalDate = scanner.nextLine();

                        boolean scheduled = service.scheduleTrip(vehicleForTrip, routeID, departureDate, arrivalDate);
                        if (scheduled) {
                            System.out.println("Trip scheduled successfully!");
                        }
                        break;

                    case 5: // Cancel Trip
                        System.out.print("Enter Trip ID to cancel: ");
                        int tripID = scanner.nextInt();
                        boolean cancelled = service.cancelTrip(tripID);
                        if (cancelled) {
                            System.out.println("Trip cancelled successfully!");
                        }
                        break;

                    case 6: // Book Trip
                        System.out.print("Enter Trip ID: ");
                        int tripToBook = scanner.nextInt();
                        System.out.print("Enter Passenger ID: ");
                        int passengerID = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline
                        System.out.print("Enter Booking Date (yyyy-MM-dd HH:mm): ");
                        String bookingDate = scanner.nextLine();

                        boolean booked = service.bookTrip(tripToBook, passengerID, bookingDate);
                        if (booked) {
                            System.out.println("Trip booked successfully!");
                        }
                        break;

                    case 7: // Cancel Booking
                        System.out.print("Enter Booking ID to cancel: ");
                        int bookingID = scanner.nextInt();
                        boolean bookingCancelled = service.cancelBooking(bookingID);
                        if (bookingCancelled) {
                            System.out.println("Booking cancelled successfully!");
                        }
                        break;

                    case 8: // Get Bookings by Passenger
                        System.out.print("Enter Passenger ID: ");
                        int passengerIDToCheck = scanner.nextInt();
                        List<Booking> bookingsByPassenger = service.getBookingsByPassenger(passengerIDToCheck);
                        if (bookingsByPassenger.isEmpty()) {
                            System.out.println("No bookings found for this passenger.");
                        } else {
                            System.out.println("Bookings for Passenger ID " + passengerIDToCheck + ":");
                            for (Booking booking : bookingsByPassenger) {
                                System.out.println("Booking ID: " + booking.getBookingID() + ", Trip ID: " + booking.getTripID() + ", Status: " + booking.getStatus());
                            }
                        }
                        break;

                    case 9: // Get Bookings by Trip
                        System.out.print("Enter Trip ID: ");
                        int tripIDToCheck = scanner.nextInt();
                        List<Booking> bookingsByTrip = service.getBookingsByTrip(tripIDToCheck);
                        if (bookingsByTrip.isEmpty()) {
                            System.out.println("No bookings found for this trip.");
                        } else {
                            System.out.println("Bookings for Trip ID " + tripIDToCheck + ":");
                            for (Booking booking : bookingsByTrip) {
                                System.out.println("Booking ID: " + booking.getBookingID() + ", Passenger ID: " + booking.getPassengerID() + ", Status: " + booking.getStatus());
                            }
                        }
                        break;

                    case 0: // Exit
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid option! Please select again.");
                }
            } catch (VehicleNotFoundException | TripNotFoundException | BookingNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

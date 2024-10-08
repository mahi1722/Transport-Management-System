package entity;

import java.util.Date;

public class Booking {
    private int bookingID;
    private int tripID;
    private int passengerID;
    private Date bookingDate;
    private String status;

    // Default constructor
    public Booking() {}

    // Parameterized constructor
    public Booking(int bookingID, int tripID, int passengerID, Date bookingDate, String status) {
        this.bookingID = bookingID;
        this.tripID = tripID;
        this.passengerID = passengerID;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    // Getters and Setters
    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public int getPassengerID() {
        return passengerID;
    }

    public void setPassengerID(int passengerID) {
        this.passengerID = passengerID;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

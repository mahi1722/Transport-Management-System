create database transportmanagement;

use transportmanagement;



CREATE TABLE Vehicles (
    vehicleID INT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(255),
    capacity DECIMAL(10, 2),
    type VARCHAR(50),
    status VARCHAR(50)
);

CREATE TABLE Routes (
    routeID INT AUTO_INCREMENT PRIMARY KEY,
    startDestination VARCHAR(255),
    endDestination VARCHAR(255),
    distance DECIMAL(10, 2)
);

CREATE TABLE Trips (
    tripID INT AUTO_INCREMENT PRIMARY KEY,
    vehicleID INT,
    routeID INT,
    departureDate DATETIME,
    arrivalDate DATETIME,
    status VARCHAR(50),
    tripType VARCHAR(50) DEFAULT 'Freight',
    maxPassengers INT,
    FOREIGN KEY (vehicleID) REFERENCES Vehicles(vehicleID),
    FOREIGN KEY (routeID) REFERENCES Routes(routeID)
);

CREATE TABLE Passengers (
    passengerID INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(255),
    gender VARCHAR(255),
    age INT,
    email VARCHAR(255) UNIQUE,
    phoneNumber VARCHAR(50)
);

CREATE TABLE Bookings (
    bookingID INT AUTO_INCREMENT PRIMARY KEY,
    tripID INT,
    passengerID INT,
    bookingDate DATETIME,
    status VARCHAR(50),
    FOREIGN KEY (tripID) REFERENCES Trips(tripID),
    FOREIGN KEY (passengerID) REFERENCES Passengers(passengerID)
);

INSERT INTO Vehicles (model, capacity, type, status) VALUES 
('Toyota Van', 12, 'Van', 'Available'),
('Mercedes Truck', 40, 'Truck', 'Maintenance'),
('Volvo Bus', 50, 'Bus', 'On Trip'),
('Honda Van', 15, 'Van', 'Available');



INSERT INTO Routes (startDestination, endDestination, distance) VALUES 
('New York', 'Los Angeles', 4500.00),
('San Francisco', 'Las Vegas', 900.00),
('Boston', 'Chicago', 1600.00),
('Dallas', 'Houston', 380.00);


INSERT INTO Trips (vehicleID, routeID, departureDate, arrivalDate, status, tripType, maxPassengers) VALUES 
(1, 1, '2024-10-15 08:00:00', '2024-10-17 17:00:00', 'Scheduled', 'Passenger', 12),
(2, 2, '2024-10-16 09:00:00', '2024-10-16 21:00:00', 'Scheduled', 'Freight', 0),
(3, 3, '2024-10-18 06:00:00', '2024-10-18 18:00:00', 'Scheduled', 'Passenger', 50),
(4, 4, '2024-10-20 05:30:00', '2024-10-20 09:30:00', 'Scheduled', 'Freight', 0);



INSERT INTO Passengers (firstName, gender, age, email, phoneNumber) VALUES 
('John Doe', 'Male', 34, 'john.doe@example.com', '555-1234'),
('Jane Smith', 'Female', 29, 'jane.smith@example.com', '555-5678'),
('Alice Johnson', 'Female', 42, 'alice.j@example.com', '555-8765'),
('Bob Brown', 'Male', 37, 'bob.b@example.com', '555-4321');



INSERT INTO Bookings (tripID, passengerID, bookingDate, status) VALUES 
(1, 1, '2024-10-12 14:30:00', 'Confirmed'),
(3, 2, '2024-10-14 09:00:00', 'Confirmed'),
(1, 3, '2024-10-13 16:00:00', 'Cancelled'),
(2, 4, '2024-10-15 08:30:00', 'Confirmed');


-- To check all vehicle records:
SELECT * FROM Vehicles;

-- To check all route records:
SELECT * FROM Routes;

-- To check all trip records:
SELECT * FROM Trips;

-- To see trips along with associated vehicle and route details:
SELECT Trips.tripID, Vehicles.model AS Vehicle, Routes.startDestination, Routes.endDestination, 
       Trips.departureDate, Trips.arrivalDate, Trips.status, Trips.tripType, Trips.maxPassengers
FROM Trips
JOIN Vehicles ON Trips.vehicleID = Vehicles.vehicleID
JOIN Routes ON Trips.routeID = Routes.routeID;


-- To check all passenger records:
SELECT * FROM Passengers;

-- To check all booking records:
SELECT * FROM Bookings;

-- To see bookings along with associated trip and passenger details:
SELECT Bookings.bookingID, Passengers.firstName AS Passenger, Trips.tripID, Bookings.bookingDate, Bookings.status
FROM Bookings
JOIN Passengers ON Bookings.passengerID = Passengers.passengerID
JOIN Trips ON Bookings.tripID = Trips.tripID;


-- Find all available vehicles:
SELECT * FROM Vehicles WHERE status = 'Available';

-- Get all trips that are scheduled:
SELECT * FROM Trips WHERE status = 'Scheduled';

-- List passengers who have confirmed bookings:
SELECT Passengers.firstName, Bookings.status
FROM Passengers
JOIN Bookings ON Passengers.passengerID = Bookings.passengerID
WHERE Bookings.status = 'Confirmed';


-- Check bookings for a specific trip (trip ID = 3):
SELECT * FROM Bookings WHERE tripID = 3;














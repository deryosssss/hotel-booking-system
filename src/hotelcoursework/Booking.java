package hotelcoursework;

import java.time.LocalDate;

public class Booking implements Overlappable {
    private Customer customer;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String bookingId;

    public Booking(Customer customer, Room room, LocalDate checkIn, LocalDate checkOut) {
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    // Getter and setter for customer
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        this.customer = customer;
    }

    // Getter and setter for room
    public Room getRoom() {
        return room;
    }
    
        // Getter for room number
    public int getRoomNumber() {
        return room.getRoomNumber();
    }

    public void setRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        this.room = room;
    }

    // Getter and setter for check-in date
    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        if (checkIn == null) {
            throw new IllegalArgumentException("Check-in date cannot be null.");
        }
        if (checkIn.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past.");
        }
        if (this.checkOut != null && checkIn.isAfter(this.checkOut)) {
            throw new IllegalArgumentException("Check-in date cannot be after the check-out date.");
        }
        this.checkIn = checkIn;
    }

    // Getter and setter for check-out date
    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        if (checkOut == null) {
            throw new IllegalArgumentException("Check-out date cannot be null.");
        }
        if (this.checkIn != null && checkOut.isBefore(this.checkIn)) {
            throw new IllegalArgumentException("Check-out date cannot be before the check-in date.");
        }
        this.checkOut = checkOut;
    }

    // Getter and setter for bookingId
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new IllegalArgumentException("Booking ID cannot be null or empty.");
        }
        this.bookingId = bookingId;
    }

    // Method from Overlappable interface to check for overlaps
    @Override
    public boolean overlaps(Booking other) {
        if (other == null) return false;

        // Check if this booking overlaps with the other booking
        return this.checkIn.isBefore(other.checkOut) && this.checkOut.isAfter(other.checkIn);
    }

    // New method to check overlap with a date range
    public boolean overlaps(LocalDate startDate, LocalDate endDate) {
        // Check if the booking's date range overlaps with the provided range
        return (this.checkIn.isBefore(endDate) || this.checkIn.isEqual(endDate)) &&
               (this.checkOut.isAfter(startDate) || this.checkOut.isEqual(startDate));
    }

    public String getBookingDetails() {
        return "Booking ID: " + bookingId +
               ", Room Number: " + getRoomNumber() +
               ", Customer: " + customer.getName() + 
               ", Contact Details: " + customer.getContactDetails() +
               ", Check-In: " + checkIn +
               ", Check-Out: " + checkOut;
    }

    @Override
    public String toString() {
        return getBookingDetails();
    }
}


package hotelcoursework;

import java.time.LocalDate;
import java.util.List;

public interface HotelCustomer {
    List<Room> findAvailableRooms(LocalDate startDate, LocalDate endDate); // Finds available rooms in the given date range   
    Booking bookRoom(Customer customer, int roomNumber, LocalDate startDate, LocalDate endDate); // Books a room
    void deleteBooking(String bookingId); // Deletes a booking by its ID
}
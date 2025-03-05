package hotelcoursework;

import java.time.LocalDate;
import java.util.List;

public interface HotelManager {
    
    void addRoom(Room room); // Adds a new room to the system
    void deleteRoom(int roomNumber); // Deletes a room by its number
    List<Room> listRooms(String sortBy); // Lists rooms sorted dynamically by a criterion
    void generateBookingReport(LocalDate startDate, LocalDate endDate); // Generates a booking report
}
    

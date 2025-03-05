package hotelcoursework;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;


public class HotelSystem implements HotelManager, HotelCustomer {
    private List<Room> rooms;
    private List<Booking> bookings;

    public HotelSystem() {
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
    }
    
    @Override
    public void addRoom(Room room) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == room.getRoomNumber()) {
                System.out.println("Room with this number already exists!");
                return;
            }
        }
        rooms.add(room);
        System.out.println("Room added successfully!");
    }

@Override
public void deleteRoom(int roomNumber) {
    for (int i = 0; i < rooms.size(); i++) {
        if (rooms.get(i).getRoomNumber() == roomNumber) {
            rooms.remove(i);
            System.out.println("Room deleted successfully!");
            return;
        }
    }
    System.out.println("Room with number " + roomNumber + " not found.");
}


    @Override
    public List<Room> listRooms(String sortBy) {
        if (sortBy.equalsIgnoreCase("roomnumber")) {
            rooms.sort(new RoomNumberComparator());
        } else if (sortBy.equalsIgnoreCase("floor")) {
            rooms.sort(new FloorComparator());
        } else if (sortBy.equalsIgnoreCase("price")) {
            rooms.sort(new PriceComparator());
        } else {
            System.out.println("Invalid sort option!");
            return rooms;
        }
        
        for (Room room : rooms) {
            System.out.println(room.getRoomInfo());
        }
        return rooms;
    }



    @Override
    public void generateBookingReport(LocalDate startDate, LocalDate endDate) {
       try (PrintWriter writer = new PrintWriter("booking_report.txt", "UTF-8")) {
//  UTF-8 Ensures your text files are readable on any system.This ensures any special characters or non-English text are encoded correctly in the output file.
// not required but thought it would be nice to include 
           writer.println("Booking Report from " + startDate + " to " + endDate);
           writer.println("---------------------------------------------------");

           boolean bookingFound = false;
           for (Booking booking : bookings) {
               if ((booking.getCheckIn().isEqual(startDate) || booking.getCheckIn().isAfter(startDate)) &&
                   (booking.getCheckOut().isEqual(endDate) || booking.getCheckOut().isBefore(endDate))) {
                   writer.println(booking.getBookingDetails());
                   bookingFound = true;
               }
           }

           if (!bookingFound) {
               writer.println("No bookings found within the specified date range.");
           }

           System.out.println("Booking report generated.");
       } catch (IOException e) {
           System.out.println("Error generating booking report: " + e.getMessage());
       }
   }



    @Override
    public List<Room> findAvailableRooms(LocalDate startDate, LocalDate endDate) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            boolean isAvailable = true;

            // Check all bookings to see if any overlap with the specified date range
            for (Booking booking : bookings) {
                if (booking.getRoom().getRoomNumber() == room.getRoomNumber() && booking.overlaps(startDate, endDate)) {
                    isAvailable = false;
                    break; // No need to check further if an overlap is found
                }
            }

            // If no overlap is found, add the room to the available list
            if (isAvailable) {
                availableRooms.add(room);
            }
        }

        // Sort rooms by price (lowest to highest)
        availableRooms.sort(new PriceComparator());
        return availableRooms;
    }



    @Override
    public Booking bookRoom(Customer customer, int roomNumber, LocalDate startDate, LocalDate endDate) {
        Room roomToBook = null;

        // Find the room to book
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                roomToBook = room;
                break;
            }
        }

        if (roomToBook == null) {
            throw new IllegalArgumentException("Room with number " + roomNumber + " does not exist.");
        }

        // Check if the room is available
        boolean isAvailable = true;
        for (Booking booking : bookings) {
            if (booking.getRoom().getRoomNumber() == roomNumber && booking.overlaps(startDate, endDate)) {
                isAvailable = false;
                break; // No need to check further if an overlap is found
            }
        }

        if (!isAvailable) {
            throw new IllegalArgumentException("Room is not available for the specified dates.");
        }

        // Create and add the new booking
        Booking newBooking = new Booking(customer, roomToBook, startDate, endDate);
        newBooking.setBookingId(generateBookingId());
        bookings.add(newBooking);

        // Print booking details
        System.out.println("Booking Successful!");
        System.out.println("Booking ID: " + newBooking.getBookingId());
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Check-in Date: " + startDate);
        System.out.println("Check-out Date: " + endDate);
        System.out.println("Total Cost: £" + roomToBook.getPrice());

        return newBooking;
    }


    private String generateBookingId() {
        return "BKG-" + (bookings.size() + 1);
    }

    @Override
    public void deleteBooking(String bookingId) {
        boolean removed = false;

        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingId().equals(bookingId)) {
                bookings.remove(i);
                removed = true;
                break; // Exit the loop once the booking is removed
            }
        }

        if (removed) {
            System.out.println("Booking " + bookingId + " deleted.");
        } else {
            System.out.println("Booking not found.");
        }
    }


    public List<Booking> getBookings() {
        return bookings;
    }

    // Comparator for sorting by room number
    private static class RoomNumberComparator implements java.util.Comparator<Room> {
        @Override
        public int compare(Room r1, Room r2) {
            return Integer.compare(r1.getRoomNumber(), r2.getRoomNumber());
        }
    }

    // Comparator for sorting by floor
    private static class FloorComparator implements java.util.Comparator<Room> {
        @Override
        public int compare(Room r1, Room r2) {
            return Integer.compare(r2.getFloor(), r1.getFloor());
        }
    }

    // Comparator for sorting by price
    private static class PriceComparator implements java.util.Comparator<Room> {
        @Override
        public int compare(Room r1, Room r2) {
            return Double.compare(r1.getPrice(), r2.getPrice());
        }
    }
}
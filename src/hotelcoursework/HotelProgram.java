package hotelcoursework;

import java.util.Scanner;
// import java.util.Date;
// With its immutability, clarity, and timezone support, LocalDateTime simplifies date manipulation and reduces the risk of errors and bugs.
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ArrayList;


public class HotelProgram {
    private static HotelSystem hotelSystem = new HotelSystem();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

// Prepopulating some test data
        hotelSystem.addRoom(new StandardRoom(101, 1, "Single", 100, 2));
        hotelSystem.addRoom(new StandardRoom(102, 1, "Double", 120, 2));
        hotelSystem.addRoom(new DeluxeRoom(201, 2, "Double", 200, 10, "Mountain view"));
        hotelSystem.addRoom(new SuiteRoom(301, 3, "Triple", 300, 50, 2, true));
        hotelSystem.addRoom(new SuiteRoom(301,7,"Triple", 300, 50, 0, true));
// prepopulating customer       
        Customer customer = new Customer("Alice", "Smith", "1990-01-01", "alice@example.com");
        System.out.println(customer);
// preppopulatng room 
        Room standardRoom = new StandardRoom(101, 1, "Double", 100.0, 2);
        System.out.println(standardRoom);
        Room room = new StandardRoom(101, 1, "Triple", 100.0, 3);
        System.out.println(room.getRoomInfo());
        System.out.println("Type: " + room.getRoomType());
// Creating an instance of Booking and showing its functionality
        Booking booking = new Booking(customer, standardRoom, LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15));
        booking.setBookingId("B001");
        System.out.println(booking);
// trying to catch booking error example       
        try {
            booking.setCheckIn(LocalDate.of(2024, 12, 31)); // Past date
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
// overlap detection in action     
        Booking otherBooking = new Booking(customer, standardRoom, LocalDate.of(2025, 1, 14), LocalDate.of(2025, 1, 18));
        System.out.println("Booking 1 and Booking 2 overlap: " + booking.overlaps(otherBooking));
       
// List available rooms
        // List available rooms
        System.out.println("Available rooms:");
        List<Room> availableRooms = hotelSystem.findAvailableRooms(LocalDate.of(2025, 1, 12), LocalDate.of(2025, 1, 16));
        for (Room test : availableRooms) {
            System.out.println(test);
        }
// Generate report
        hotelSystem.generateBookingReport(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 31));
  

       



        
        


        int choice = -1; // Initialize choice
        do {
            System.out.println("\nWelcome to the Hotel Management System");
            System.out.println("1. Customer Menu");
            System.out.println("2. Admin Menu");
            System.out.println("0. Exit");
            System.out.print("Enter your choice (0, 1, or 2): ");

            if (scanner.hasNextInt()) { // Validate input is an integer if not show and erro
                choice = scanner.nextInt(); // Parse the valid integer input

                if (choice >= 0 && choice <= 2) {
                    // Handle valid menu choices
                    switch (choice) {
                        case 1:
                            customerMenu(scanner);
                            break;
                        case 2:
                            adminMenu(scanner);
                            break;
                        case 0:
                            System.out.println("Exiting the system...");
                            break;
                    }
                } else {
                    // Integer is out of valid range (0-2)
                    System.out.println("Invalid choice. Please enter a number between 0, 1, and 2.");
                }
            } else {
                // Handle non-integer input
                System.out.println("Invalid input. Please enter a valid number (0, 1, or 2).");
                scanner.next(); // Consume and discard the invalid input to clear the scanner buffer
            }
        } while (choice != 0);
    // if input 0 close scanner 
        scanner.close();
    }

// customer menu 
    private static void customerMenu(Scanner scanner) {
    int choice = -1; // Initialize choice
    do {
        System.out.println("\nCustomer Menu:");
        System.out.println("1. List available rooms sorted by price (lower price first)");
        System.out.println("2. List available rooms according to room type (Standard Room, Suite Room or Deluxe Room) and occupancy (e.g., Single, Double  or triple) ");
        System.out.println("3. Book a room");
        System.out.println("4. Cancel a booking");
        System.out.println("0. Return to main menu");
        System.out.print("Enter your choice: ");

        if (scanner.hasNextInt()) { // Validate input is an integer
            choice = scanner.nextInt();

            switch (choice) {
// List available rooms sorted by price
            case 1: 
                String startDateStr1 = null;
                String endDateStr1 = null;

                // Validate start date
                while (true) {
                    System.out.print("Enter start date (yyyy-MM-dd): ");
                    startDateStr1 = scanner.next();
                    try {
                        LocalDate.parse(startDateStr1, DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Validate format
                        break; // Exit loop if the date is valid
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please try again (e.g., 2024-12-31).");
                    }
                }

                // Validate end date
                while (true) {
                    System.out.print("Enter end date (yyyy-MM-dd): ");
                    endDateStr1 = scanner.next();
                    try {
                        LocalDate.parse(endDateStr1, DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Validate format
                        break; // Exit loop if the date is valid
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please try again (e.g., 2024-12-31).");
                    }
                }

                System.out.println(" \n Here are the available rooms:\n");
                try {
                    // Parse the validated dates
                    LocalDate startDate = LocalDate.parse(startDateStr1, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate endDate = LocalDate.parse(endDateStr1, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    // Get available rooms
                    List<Room> availableRooms = hotelSystem.findAvailableRooms(startDate, endDate);

                    if (availableRooms.isEmpty()) {
                        System.out.println("No available rooms found for the specified date range.");
                    } else {
                        for (Room room : availableRooms) {
                            System.out.println(room);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("An unexpected error occurred: " + e.getMessage());
                }
                break;
// List available rooms according to room type and occupancy 
            case 2:   
                String startDateStr2;
                String endDateStr2;
                String roomType;
                String occupancy;

                // Validate start date
                while (true) {
                    System.out.print("Enter start date (yyyy-MM-dd): ");
                    startDateStr2 = scanner.next();
                    try {
                        LocalDate.parse(startDateStr2, DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Validate format
                        break; // Exit loop if the date is valid
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid start date format. Please use yyyy-MM-dd (e.g., 2024-12-31).");
                    }
                }

                // Validate end date
                while (true) {
                    System.out.print("Enter end date (yyyy-MM-dd): ");
                    endDateStr2 = scanner.next();
                    try {
                        LocalDate.parse(endDateStr2, DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Validate format
                        break; // Exit loop if the date is valid
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid end date format. Please use yyyy-MM-dd (e.g., 2024-12-31).");
                    }
                }

                // Validate room type
                while (true) {
                    System.out.print("Enter room type (Standard, Suite, Deluxe): ");
                    roomType = scanner.next();
                    if (roomType.equalsIgnoreCase("Standard") ||
                        roomType.equalsIgnoreCase("Suite") ||
                        roomType.equalsIgnoreCase("Deluxe")) {
                        break; // Exit loop if the room type is valid
                    } else {
                        System.out.println("Invalid room type. Please enter Standard, Suite, or Deluxe.");
                    }
                }

                // Validate occupancy
                while (true) {
                    System.out.print("Enter occupancy (Single, Double, Triple): ");
                    occupancy = scanner.next();
                    if (occupancy.equalsIgnoreCase("Single") ||
                        occupancy.equalsIgnoreCase("Double") ||
                        occupancy.equalsIgnoreCase("Triple")) {
                        break; // Exit loop if the occupancy is valid
                    } else {
                        System.out.println("Invalid occupancy. Please enter Single, Double, or Triple.");
                    }
                }

                System.out.println("Here are the available rooms according to room type and occupancy:");

                try {
                    // Parse the validated dates
                    LocalDate startDate = LocalDate.parse(startDateStr2, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate endDate = LocalDate.parse(endDateStr2, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    // Fetch available rooms
                    List<Room> availableRooms = hotelSystem.findAvailableRooms(startDate, endDate);
                    List<Room> filteredRooms = new ArrayList<>();

                    // Filter rooms based on type and occupancy
                    for (Room room : availableRooms) {
                        boolean matchesType = false;

                        if (roomType.equalsIgnoreCase("standard") && room instanceof StandardRoom) {
                            matchesType = true;
                        } else if (roomType.equalsIgnoreCase("suite") && room instanceof SuiteRoom) {
                            matchesType = true;
                        } else if (roomType.equalsIgnoreCase("deluxe") && room instanceof DeluxeRoom) {
                            matchesType = true;
                        }

                        boolean matchesOccupancy = room.getOccupancy().equalsIgnoreCase(occupancy);

                        // Add room to filtered list if it matches both criteria
                        if (matchesType && matchesOccupancy) {
                            filteredRooms.add(room);
                        }
                    }

                    // Output filtered rooms
                    if (filteredRooms.isEmpty()) {
                        System.out.println("No available rooms match the specified criteria.");
                    } else {
                        System.out.println("Available rooms:");
                        for (Room room : filteredRooms) {
                            System.out.println(room);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("An unexpected error occurred: " + e.getMessage());
                }
                break;

// Book a room
               case 3: 
                    int roomNumber;
                    String startDateStr3;
                    String endDateStr3;
                    String firstName;
                    String lastName;
                    String dateOfBirth;
                    String contact;

                    // Validate room number
                    while (true) {
                        System.out.print("Enter room number: ");
                        if (scanner.hasNextInt()) {
                            roomNumber = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            if (roomNumber > 0) {
                                break; // Exit loop if room number is valid
                            } else {
                                System.out.println("Room number must be positive. Please try again.");
                            }
                        } else {
                            System.out.println("Invalid room number.");
                            scanner.next(); // Clear invalid input
                        }
                    }

                    // Validate start date
                    LocalDate startDate = null;
                    while (true) {
                        System.out.print("Enter start date (yyyy-MM-dd): ");
                        startDateStr3 = scanner.next();
                        try {
                            startDate = LocalDate.parse(startDateStr3, DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Validate format
                            break; // Exit loop if the date is valid
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid start date format. Please use yyyy-MM-dd (e.g., 2024-12-31).");
                        }
                    }

                    // Validate end date
                    LocalDate endDate = null;
                    while (true) {
                        System.out.print("Enter end date (yyyy-MM-dd): ");
                        endDateStr3 = scanner.next();
                        try {
                            endDate = LocalDate.parse(endDateStr3, DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Validate format

                            // Check if end date is after start date
                            if (endDate.isAfter(startDate)) {
                                break; // Exit loop if the date is valid
                            } else {
                                System.out.println("End date must be after the start date. Please try again.");
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid end date format. Please use yyyy-MM-dd (e.g., 2024-12-31).");
                        }
                    }

                    scanner.nextLine(); // Consume newline

                    // Validate first name
                    while (true) {
                        System.out.print("Enter your first name: ");
                        firstName = scanner.nextLine().trim();
                        if (!firstName.isEmpty() && firstName.matches("[A-Za-z\\-\\s]+")) {
                            break; // Exit loop if the first name is valid
                        } else {
                            System.out.println("Invalid first name. Only letters, hyphens (-), and spaces are allowed.");
                        }
                    }

                    // Validate last name
                    while (true) {
                        System.out.print("Enter your last name: ");
                        lastName = scanner.nextLine().trim();
                        if (!lastName.isEmpty() && lastName.matches("[A-Za-z\\-\\s]+")) {
                            break; // Exit loop if the last name is valid
                        } else {
                            System.out.println("Invalid last name. Only letters, hyphens (-), and spaces are allowed.");
                        }
                    }

                    // Validate date of birth
                    while (true) {
                        System.out.print("Enter your date of birth (yyyy-MM-dd): ");
                        dateOfBirth = scanner.nextLine().trim();
                        try {
                            LocalDate dob = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Validate format
                            if (dob.isBefore(LocalDate.now())) {
                                break; // Exit loop if the date of birth is valid
                            } else {
                                System.out.println("Date of birth must be in the past. Please try again.");
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date of birth format. Please use yyyy-MM-dd.");
                        }
                    }

                    // Validate contact details
                    while (true) {
                        System.out.print("Enter your contact details (Phone number and/or Email): ");
                        contact = scanner.nextLine().trim();
                        if (contact.matches("\\d{10,15}") || contact.contains("@")) { // Check for valid phone number or email
                            break; // Exit loop if contact details are valid
                        } else {
                            System.out.println("Invalid contact details. Enter a valid phone number (10-15 digits) or a valid email address.");
                        }
                    }

                    // Attempt to book the room
                    try {
                        hotelSystem.bookRoom(
                            new Customer(firstName, lastName, dateOfBirth, contact), // Create Customer object
                            roomNumber,
                            startDate,
                            endDate
                        );

                        System.out.println("Booking successful!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

// cancel room
                case 4:
                    System.out.print("Enter booking ID to cancel: ");
                    String bookingId = scanner.next();
                    hotelSystem.deleteBooking(bookingId);
                    break;
// return to main menu                  
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } else {
            // Handle non-integer input
            System.out.println("Invalid input. Please enter a valid choice.");
            scanner.next(); // Consume and discard the invalid input
        }
    } while (choice != 0);
}


    private static void adminMenu(Scanner scanner) {
    int choice = -1; // Initialize choice
    do {
        System.out.println("\nAdmin Menu:");
        System.out.println("1. Add a new room");
        System.out.println("2. Delete a room");
        System.out.println("3. List rooms by room number (Ascending Order)");
        System.out.println("4. List rooms by floor (Descending Order)");
        System.out.println("5. Generate booking report");
        System.out.println("0. Return to main menu");
        System.out.print("Enter your choice: ");

        if (scanner.hasNextInt()) { // Validate input is an integer
            choice = scanner.nextInt();

            switch (choice) {
// Add a new room                
                case 1: 
                    System.out.print("Enter room type (Standard, Suite, Deluxe): ");
                    String roomType = scanner.next();

                    // Validate room type in a loop
                    while (!isValidRoomType(roomType)) {
                        System.out.println("Invalid room type. Please enter Standard, Suite, or Deluxe.");
                        roomType = scanner.next();
                    }

                    System.out.print("Enter room number: ");
                    int roomNumber = getValidatedInteger(scanner, "Invalid input. Room number must be a number.");

                    System.out.print("Enter floor number: ");
                    int floor = getValidatedInteger(scanner, "Invalid input. Floor number must be a number.");

                    System.out.print("Enter occupancy (Single, Double, Triple): ");
                    String occupancy = scanner.next();

                    // Validate occupancy in a loop
                    while (!isValidOccupancy(occupancy)) {
                        System.out.println("Invalid occupancy. Please enter Single, Double, or Triple.");
                        occupancy = scanner.next();
                    }

                    System.out.print("Enter price: £");
                    double price = getValidatedDouble(scanner, "Invalid input. Price must be a number greater than 0.");

                    // Adding specific attributes for different room types
                    switch (roomType.toLowerCase()) {
                        case "standard":
                            System.out.print("Enter number of windows: ");
                            int numberOfWindows = getValidatedInteger(scanner, "Invalid input. Number of windows must be a non-negative number.");
                            hotelSystem.addRoom(new StandardRoom(roomNumber, floor, occupancy, price, numberOfWindows));
                            System.out.println("Standard room added successfully!");
                            break;

                        case "deluxe":
                            System.out.print("Enter balcony size (m²): ");
                            double balconySize = getValidatedDouble(scanner, "Invalid input. Balcony size must be a non-negative number.");
                            System.out.print("Enter view type (Sea, City, Mountain): ");
                            scanner.nextLine(); // Consume newline
                            String viewType = scanner.nextLine();

                            // Validate view type in a loop
                            while (viewType.trim().isEmpty()) {
                                System.out.println("Invalid input. View type cannot be empty.");
                                viewType = scanner.nextLine();
                            }
                            hotelSystem.addRoom(new DeluxeRoom(roomNumber, floor, occupancy, price, balconySize, viewType));
                            System.out.println("Deluxe room added successfully!");
                            break;

                        case "suite":
                            System.out.print("Enter living area size (m²): ");
                            double livingArea = getValidatedDouble(scanner, "Invalid input. Living area size must be a number greater than 0.");
                            System.out.print("Enter number of bathrooms: ");
                            int bathrooms = getValidatedInteger(scanner, "Invalid input. Number of bathrooms must be at least 1.");
                            System.out.print("Has kitchenette (true/false): ");
                            boolean hasKitchenette = getValidatedBoolean(scanner, "Invalid input. Please enter true or false.");
                            hotelSystem.addRoom(new SuiteRoom(roomNumber, floor, occupancy, price, livingArea, bathrooms, hasKitchenette));
                            System.out.println("Suite room added successfully!");
                            break;

                        default:
                            // This should never be reached due to the room type validation loop
                            System.out.println("Unexpected error occurred.");
                            break;
                    }
                    break;
// Delete a room
                case 2:
                    int roomNum = -1;
                    boolean validInput = false;
                    while (!validInput) { // Keep prompting until valid input is provided
                        System.out.print("Enter the room number to delete: ");
                        if (scanner.hasNextInt()) {
                            roomNum = scanner.nextInt();
                            validInput = true; // Input is valid, exit the loop
                        } else {
                            System.out.println("Invalid input. Please enter a valid room number.");
                            scanner.next(); // Clear invalid input
                        }
                    }
                    hotelSystem.deleteRoom(roomNum); // Proceed with deleting the room
                    break;
// List rooms by room number (Ascending Order)
                case 3:
                    hotelSystem.listRooms("roomnumber");
                    break;
// List rooms by floor (Descending Order)                    
                case 4:
                    hotelSystem.listRooms("floor");
                    break;
// Generate booking report                    
                case 5:
                    LocalDate startDate = null;
                    LocalDate endDate = null;

                    // Validate start date
                    while (startDate == null) {
                        System.out.print("Enter start date (yyyy-MM-dd): ");
                        String startDateStr = scanner.next();
                        try {
                            startDate = parseDate(startDateStr); // Attempt to parse the start date
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid start date format. Please use yyyy-MM-dd (e.g., 2024-12-31).");
                        }
                    }

                    // Validate end date
                    while (endDate == null) {
                        System.out.print("Enter end date (yyyy-MM-dd): ");
                        String endDateStr = scanner.next();
                        try {
                            endDate = parseDate(endDateStr); // Attempt to parse the end date
                            if (!endDate.isAfter(startDate)) {
                                System.out.println("End date must be after start date. Please try again.");
                                endDate = null; // Reset endDate to force retry
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid end date format. Please use yyyy-MM-dd (e.g., 2024-12-31).");
                        }
                    }

                    // Generate booking report
                    try {
                        hotelSystem.generateBookingReport(startDate, endDate);
                    } catch (Exception e) {
                        System.out.println("An error occurred: " + e.getMessage());
                    }
                    break;

// Return to main menu
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } else {
            // Handle non-integer input
            System.out.println("Invalid input. Please enter a valid choice.");
            scanner.next(); // Consume and discard the invalid input
        }
    } while (choice != 0);
}


    private static LocalDate parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateStr, formatter);
    }
        
        // Method to validate room type
    private static boolean isValidRoomType(String roomType) {
        return roomType.equalsIgnoreCase("Standard") ||
               roomType.equalsIgnoreCase("Suite") ||
               roomType.equalsIgnoreCase("Deluxe");
    }

    // Method to validate occupancy
    private static boolean isValidOccupancy(String occupancy) {
        return occupancy.equalsIgnoreCase("Single") ||
               occupancy.equalsIgnoreCase("Double") ||
               occupancy.equalsIgnoreCase("Triple");
    }

    // Method to validate integer input
    private static int getValidatedInteger(Scanner scanner, String errorMessage) {
        while (!scanner.hasNextInt()) {
            System.out.println(errorMessage);
            scanner.next(); // Clear invalid input
        }
        int value = scanner.nextInt();
        if (value < 0) {
            System.out.println("Value must be non-negative. Please try again.");
            return getValidatedInteger(scanner, errorMessage);
        }
        return value;
    }

    // Method to validate double input
    private static double getValidatedDouble(Scanner scanner, String errorMessage) {
        while (!scanner.hasNextDouble()) {
            System.out.println(errorMessage);
            scanner.next(); // Clear invalid input
        }
        double value = scanner.nextDouble();
        if (value <= 0) {
            System.out.println("Value must be greater than 0. Please try again.");
            return getValidatedDouble(scanner, errorMessage);
        }
        return value;
    }

    // Method to validate boolean input
    private static boolean getValidatedBoolean(Scanner scanner, String errorMessage) {
        while (!scanner.hasNextBoolean()) {
            System.out.println(errorMessage);
            scanner.next(); // Clear invalid input
        }
        return scanner.nextBoolean();
        }

    }


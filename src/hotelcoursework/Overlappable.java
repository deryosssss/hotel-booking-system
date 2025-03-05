package hotelcoursework;

/**
 * Represents an interface to check for overlapping bookings.
 */

public interface Overlappable {
    boolean overlaps(Booking other);
}


package hotelcoursework;

public abstract class Room {
    private int roomNumber;
    private int floor;
    private String occupancy;
    private double price;

    public Room(int roomNumber, int floor, String occupancy, double price) {
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.occupancy = occupancy;
        this.price = price;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getFloor() {
        return floor;
    }

    public String getOccupancy() {
        return occupancy;
    }

    public double getPrice() {
        return price;
    }

    public void setOccupancy(String occupancy) {
        if (occupancy == null || occupancy.isEmpty()) {
            throw new IllegalArgumentException("Occupancy cannot be null or empty.");
        }

        if (!occupancy.equalsIgnoreCase("Single") &&
            !occupancy.equalsIgnoreCase("Double") &&
            !occupancy.equalsIgnoreCase("Triple")) {
            throw new IllegalArgumentException("Invalid occupancy. Valid values are: Single, Double, Triple.");
        }

        this.occupancy = occupancy;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }

        this.price = price;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getRoomInfo() {
        return "Room Number: " + roomNumber +
               ", Floor: " + floor +
               ", Occupancy: " + occupancy +
               ", Price: £" + price;
    }

    // Abstract method to enforce specific behavior in subclasses
    public abstract String getRoomType();

    @Override
    public String toString() {
        return getRoomInfo() + ", Type: " + getRoomType();
    }
}

package hotelcoursework;

public class StandardRoom extends Room {
    private int numberOfWindows;

    public StandardRoom(int roomNumber, int floor, String occupancy, double price, int numberOfWindows) {
        super(roomNumber, floor, occupancy, price);
        this.numberOfWindows = numberOfWindows;
    }

    public int getNumberOfWindows() {
        return numberOfWindows;
    }

    public void setNumberOfWindows(int numberOfWindows) {
        if (numberOfWindows < 0) {
            throw new IllegalArgumentException("Number of windows cannot be negative.");
        }
        this.numberOfWindows = numberOfWindows;
    }

    @Override
    public String getRoomType() {
        return "Standard Room";
    }

    @Override
    public String toString() {
        return super.toString() + ", Windows: " + numberOfWindows;
    }
}

package hotelcoursework;

public class DeluxeRoom extends Room {
    private double balconySize;
    private String view;

    public DeluxeRoom(int roomNumber, int floor, String occupancy, double price, double balconySize, String view) {
        super(roomNumber, floor, occupancy, price);
        this.balconySize = balconySize;
        this.view = view;
    }

    public double getBalconySize() {
        return balconySize;
    }

    public void setBalconySize(double balconySize) {
        if (balconySize < 0) {
            throw new IllegalArgumentException("Balcony size cannot be negative.");
        }
        this.balconySize = balconySize;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        if (view == null || view.trim().isEmpty()) {
            throw new IllegalArgumentException("View cannot be empty.");
        }
        this.view = view;
    }

    @Override
    public String getRoomType() {
        return "Deluxe Room";
    }

    @Override
    public String toString() {
        return super.toString() +
               ", Balcony: " + balconySize + " m²" +
               ", View: " + view;
    }
}

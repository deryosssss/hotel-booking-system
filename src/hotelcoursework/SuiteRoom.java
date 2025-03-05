package hotelcoursework;

public class SuiteRoom extends Room { 
    /**
 * Represents a suite room in the hotel.
 * Extends the Room superclass and adds luxury features like living area size, number of bathrooms, and kitchenette option.
 */
    private double livingArea;      // Size of the living area in square meters
    private int numberOfBathrooms;     // Number of bathrooms in the suite
    private boolean hasKitchenette;    // Whether the suite includes a kitchenette

    // Constructor
    public SuiteRoom(int roomNumber, int floor, String occupancy, double price, double livingArea, int numberOfBathrooms, boolean hasKitchenette) {
        super(roomNumber, floor, occupancy, price); // Call to superclass constructor
        this.livingArea = livingArea;
        this.numberOfBathrooms = numberOfBathrooms;
        this.hasKitchenette = hasKitchenette;
    }
    // Getter and Setter for livingArea
    public double getLivingArea() {
        return livingArea;
    }

    public void setLivingArea(double livingArea) {
        if (livingArea <= 0) {
            throw new IllegalArgumentException("Living area must be greater than 0.");
        }
        this.livingArea = livingArea;
    }

    // Getter and Setter for numberOfBathrooms
    public int getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public void setNumberOfBathrooms(int numberOfBathrooms) {
        if (numberOfBathrooms < 1) {
            throw new IllegalArgumentException("Number of bathrooms must be at least 1.");
        }
        this.numberOfBathrooms = numberOfBathrooms;
    }

    // Getter and Setter for hasKitchenette
    public boolean hasKitchenette() {
        return hasKitchenette;
    }

    public void setHasKitchenette(boolean hasKitchenette) {
        this.hasKitchenette = hasKitchenette; // No extra validation needed
    }
    @Override
    public String getRoomType() {
        return "Suite Room";
    }

    @Override
    public String toString() {
        return super.toString() +
               ", Living Area: " + livingArea + " m²" +
               ", Bathrooms: " + numberOfBathrooms +
               ", Kitchenette: " + (hasKitchenette ? "Yes" : "No");
    }
}

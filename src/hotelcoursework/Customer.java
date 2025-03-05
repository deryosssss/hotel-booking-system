package hotelcoursework;

public class Customer {
    private String name;
    private String surname;
    private String dateOfBirth;
    private String contactDetails;

    // Constructor
    public Customer(String name, String surname, String dateOfBirth, String contactDetails) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.contactDetails = contactDetails;
    }


    // Getters and setters with enhanced validation
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && c != '-' && c != ' ') {
                throw new IllegalArgumentException("Name can only contain letters, hyphens (-), and spaces.");
            }
        }
        this.name = name.trim();
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (surname == null || surname.trim().isEmpty()) {
            throw new IllegalArgumentException("Surname cannot be empty.");
        }
        for (char c : surname.toCharArray()) {
            if (!Character.isLetter(c) && c != '-' && c != ' ') {
                throw new IllegalArgumentException("Surname can only contain letters, hyphens (-), and spaces.");
            }
        }
        this.surname = surname.trim();
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.trim().isEmpty()) {
            throw new IllegalArgumentException("Date of birth cannot be empty.");
        }
        this.dateOfBirth = dateOfBirth.trim();
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        if (contactDetails == null || contactDetails.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact details cannot be empty.");
        }
        this.contactDetails = contactDetails.trim();
    }

  

    @Override
    public String toString() {
        return "Customer Name: " + name + ", Last Name: " + surname +
               ", Contact Details: " + contactDetails;
    }
}


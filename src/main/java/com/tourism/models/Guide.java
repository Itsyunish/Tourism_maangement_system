package com.tourism.models;

import java.util.ArrayList;
import java.util.List;

// Guide class inheriting from Person
public class Guide extends Person {
    private List<String> languages;
    private int experienceYears;
    private List<String> specializations;
    private double totalEarnings;
    private List<Booking> assignedBookings;
    private boolean isAvailable;
    
    public Guide(String username, String password, String fullName, String email, String phone, 
                 List<String> languages, int experienceYears) {
        super(username, password, fullName, email, phone);
        
        // Ensure all collections are properly initialized
        this.languages = languages != null ? new ArrayList<>(languages) : new ArrayList<>();
        this.experienceYears = experienceYears;
        this.specializations = new ArrayList<>();
        this.totalEarnings = 0.0;
        this.assignedBookings = new ArrayList<>();
        this.isAvailable = true;
        
        System.out.println("Guide created: " + fullName + " with " + this.languages.size() + " languages");
    }
    
    // Encapsulation
    public List<String> getLanguages() { return new ArrayList<>(languages); }
    public void addLanguage(String language) { this.languages.add(language); }
    
    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
    
    public List<String> getSpecializations() { return new ArrayList<>(specializations); }
    public void addSpecialization(String specialization) { this.specializations.add(specialization); }
    
    public double getTotalEarnings() { return totalEarnings; }
    public void addEarnings(double earnings) { this.totalEarnings += earnings; }
    public void setTotalEarnings(double totalEarnings) { this.totalEarnings = totalEarnings; }
    
    public List<Booking> getAssignedBookings() { return new ArrayList<>(assignedBookings); }
    public void assignBooking(Booking booking) { 
        if (this.assignedBookings == null) {
            this.assignedBookings = new ArrayList<>();
        }
        
        // Check if booking is already assigned to avoid duplicates
        boolean alreadyAssigned = this.assignedBookings.stream()
            .anyMatch(b -> b.getBookingId() == booking.getBookingId());
        
        if (!alreadyAssigned) {
            this.assignedBookings.add(booking);
            // Calculate commission (30% of booking price)
            double commission = booking.getTotalPrice() * 0.30;
            addEarnings(commission);
            System.out.println("Guide " + getUsername() + " earned $" + String.format("%.2f", commission) + 
                " from booking " + booking.getBookingId() + ". Total earnings: $" + String.format("%.2f", totalEarnings));
        }
    }
    
    public void removeBooking(Booking booking) {
        if (this.assignedBookings != null) {
            this.assignedBookings.remove(booking);
            // Remove commission (30% of booking price)
            double commission = booking.getTotalPrice() * 0.30;
            this.totalEarnings -= commission;
            if (this.totalEarnings < 0) this.totalEarnings = 0;
            System.out.println("Guide " + getUsername() + " lost $" + String.format("%.2f", commission) + " from cancelled booking " + booking.getBookingId());
        }
    }
    
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { this.isAvailable = available; }
    
    // Polymorphism - Override abstract methods
    @Override
    public String getRole() {
        return "Guide";
    }
    
    @Override
    public String getDashboardInfo() {
        return "Welcome Guide " + getFullName() + "!\n" +
               "Experience: " + experienceYears + " years\n" +
               "Languages: " + String.join(", ", languages) + "\n" +
               "Total Earnings: $" + String.format("%.2f", totalEarnings) + "\n" +
               "Assigned Bookings: " + assignedBookings.size() + "\n" +
               "Status: " + (isAvailable ? "Available" : "Busy");
    }
    
    // Guide-specific methods
    public double calculateCommission(double bookingPrice) {
        return bookingPrice * 0.30; // 30% commission
    }
    
    public boolean canTakeBooking() {
        return isAvailable && assignedBookings.size() < 5; // Max 5 concurrent bookings
    }
    
    public String getLanguagesString() {
        return String.join(", ", languages);
    }
    
    @Override
    public String toString() {
        return super.toString() + "\n" +
               "Languages: " + String.join(", ", languages) + "\n" +
               "Experience: " + experienceYears + " years\n" +
               "Specializations: " + String.join(", ", specializations) + "\n" +
               "Total Earnings: $" + String.format("%.2f", totalEarnings) + "\n" +
               "Available: " + (isAvailable ? "Yes" : "No");
    }
}

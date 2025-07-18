package com.tourism.models;

import java.util.ArrayList;
import java.util.List;

// Tourist class inheriting from Person
public class Tourist extends Person {
    private String nationality;
    private List<Booking> bookings;
    private double totalSpent;
    
    public Tourist(String username, String password, String fullName, String email, String phone, String nationality) {
        super(username, password, fullName, email, phone);
        this.nationality = nationality;
        this.bookings = new ArrayList<>();
        this.totalSpent = 0.0;
    }
    
    // Encapsulation
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    
    public List<Booking> getBookings() { return new ArrayList<>(bookings); } // Return copy for encapsulation
    public void addBooking(Booking booking) { 
        this.bookings.add(booking);
        if ("Confirmed".equals(booking.getStatus()) || "Completed".equals(booking.getStatus())) {
            this.totalSpent += booking.getTotalPrice();
        }
    }
    
    public void removeBooking(Booking booking) {
        if (this.bookings.remove(booking)) {
            // Only reduce total spent if the booking was confirmed/completed
            if ("Confirmed".equals(booking.getStatus()) || "Completed".equals(booking.getStatus())) {
                this.totalSpent -= booking.getTotalPrice();
                if (this.totalSpent < 0) this.totalSpent = 0;
            }
        }
    }
    
    public void updateBookingInList(Booking updatedBooking) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingId() == updatedBooking.getBookingId()) {
                // Remove old spending if it was confirmed
                Booking oldBooking = bookings.get(i);
                if ("Confirmed".equals(oldBooking.getStatus()) || "Completed".equals(oldBooking.getStatus())) {
                    this.totalSpent -= oldBooking.getTotalPrice();
                }
                
                // Add new spending if it's confirmed
                if ("Confirmed".equals(updatedBooking.getStatus()) || "Completed".equals(updatedBooking.getStatus())) {
                    this.totalSpent += updatedBooking.getTotalPrice();
                }
                
                bookings.set(i, updatedBooking);
                break;
            }
        }
    }
    
    public double getTotalSpent() { return totalSpent; }
    public void setTotalSpent(double totalSpent) { this.totalSpent = totalSpent; }
    
    // Polymorphism - Override abstract methods
    @Override
    public String getRole() {
        return "Tourist";
    }
    
    @Override
    public String getDashboardInfo() {
        return "Welcome Tourist " + getFullName() + "!\n" +
               "Total Bookings: " + bookings.size() + "\n" +
               "Total Spent: $" + String.format("%.2f", totalSpent) + "\n" +
               "Nationality: " + nationality;
    }
    
    // Tourist-specific methods
    public boolean canBook() {
        return bookings.size() < 10; // Max 10 bookings per tourist
    }
    
    public int getBookingCount() {
        return bookings.size();
    }
    
    @Override
    public String toString() {
        return super.toString() + "\n" +
               "Nationality: " + nationality + "\n" +
               "Total Bookings: " + bookings.size() + "\n" +
               "Total Spent: $" + String.format("%.2f", totalSpent);
    }
}

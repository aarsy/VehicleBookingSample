package com.example.models;

public class Booking {
  private User user;
  private Branch branch;
  private Vehicle vehicle;
  private Double price;
  private BookingTimeSlot bookingSlot;

  public Booking(User user, Branch branch, Vehicle vehicle, Double price, BookingTimeSlot bookingSlot) {
    this.user = user;
    this.branch = branch;
    this.vehicle = vehicle;
    this.price = price;
    this.bookingSlot = bookingSlot;
  }

  public User getUser() {
    return user;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }

  public Double getPrice() {
    return price;
  }

  public BookingTimeSlot getBookingSlot() {
    return bookingSlot;
  }

  public Branch getBranch() {
    return branch;
  }
}
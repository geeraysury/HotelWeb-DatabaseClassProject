package com.ehotels;

import java.util.List;

public class Room {
    private int roomNumber;
    private int hotelId;
    private double price;
    private int capacity;
    private String view;
    private boolean extendable;
    private List<String> amenities;


    public List<String> getAmenities() { return amenities; }
    public void setAmenities(List<String> amenities) { this.amenities = amenities; }

    // Getters and setters
    public int getRoomNumber() { return roomNumber; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }

    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getView() { return view; }
    public void setView(String view) { this.view = view; }

    public boolean isExtendable() { return extendable; }
    public void setExtendable(boolean extendable) { this.extendable = extendable; }
}

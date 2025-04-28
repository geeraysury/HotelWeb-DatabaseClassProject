package com.ehotels;

import java.sql.Date;

public class BookingRecord {
    private int bookingId;
    private int roomNumber;
    private int hotelId;
    private String address;
    private String customerName;
    private Date checkInDate;
    private Date checkOutDate;

    public BookingRecord(int bookingId, int roomNumber, int hotelId, String address, String customerName, Date checkInDate, Date checkOutDate) {
        this.bookingId = bookingId;
        this.roomNumber = roomNumber;
        this.hotelId = hotelId;
        this.address = address;
        this.customerName = customerName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public int getBookingId() { return bookingId; }
    public int getRoomNumber() { return roomNumber; }
    public int getHotelId() { return hotelId; }
    public String getAddress() { return address; }
    public String getCustomerName() { return customerName; }
    public Date getCheckInDate() { return checkInDate; }
    public Date getCheckOutDate() { return checkOutDate; }
}

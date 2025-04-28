package com.ehotels;

import java.sql.Date;

public class ProblemReport {
    private int problemId;
    private int roomNumber;
    private int hotelId;
    private String description;
    private Date reportedDate;
    private Date fixedDate;
    private String status;
    private String hotelName;

    // Constructor, Getters and Setters
    public ProblemReport(int problemId, int roomNumber, int hotelId, String description,
                         Date reportedDate, Date fixedDate, String status) {
        this.problemId = problemId;
        this.roomNumber = roomNumber;
        this.hotelId = hotelId;
        this.description = description;
        this.reportedDate = reportedDate;
        this.fixedDate = fixedDate;
        this.status = status;
    }

    public int getProblemId() { return problemId; }
    public void setProblemId(int problemId) { this.problemId = problemId; }
    public int getRoomNumber() { return roomNumber; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Date getReportedDate() { return reportedDate; }
    public void setReportedDate(Date reportedDate) { this.reportedDate = reportedDate; }
    public Date getFixedDate() { return fixedDate; }
    public void setFixedDate(Date fixedDate) { this.fixedDate = fixedDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }
}

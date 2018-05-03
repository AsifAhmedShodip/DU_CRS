package com.example.rafi.du_crs;

/**
 * Created by Rafi on 01-May-18.
 */

public class Ground_object {
    String groundName;
    String startTime, endTime;
    String date;
    boolean showTime;
    boolean isBooked;
    String eventName;
    String bookedBy;
    String bookingID;

    public Ground_object(String groundName, String startTime, String endTime, String date, boolean showTime) {
        this.groundName = groundName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.showTime = showTime;
        this.isBooked = false;
    }

    public Ground_object() {
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getGroundName() {
        return groundName;
    }

    public void setGroundName(String groundName) {
        this.groundName = groundName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isShowTime() {
        return showTime;
    }

    public void setShowTime(boolean showTime) {
        this.showTime = showTime;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }
}

package com.example.rafi.du_crs;

/**
 * Created by Rafi on 03-Apr-18.
 */

public class detail {
    String name;
    String location;
    String startTime;
    String endTime;
    int capacity;
    public detail()
    {
        capacity=180;
        name=location=startTime=endTime="omi";
    }
    public detail(String name, String location, String startTime, String endTime, int capacity) {
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}

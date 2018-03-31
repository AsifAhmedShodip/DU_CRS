/**
 * Created by aniomi on 3/26/18.
 */

package com.example.omi.du_crs;

public class ExamHallSlot {
    public ExamHallSlot(int startTime, int endTime, String reservetionId, String reserverId, String isCancelled) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservetionId = reservetionId;
        this.reserverId = reserverId;
        this.isCancelled = isCancelled;
    }

    public ExamHallSlot() {
        startTime=endTime=counter=0;
        isCancelled="No Subject";
        reservetionId=reserverId="Omi";
        rdate="12/12/2012";
    }

    int startTime;
    int endTime;
    int counter;
    String rdate;

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getRdate() {
        return rdate;
    }

    public ExamHallSlot(int startTime, int endTime, int counter, String rdate, String reservetionId, String reserverId, String isCancelled) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.counter = counter;
        this.rdate = rdate;
        this.reservetionId = reservetionId;
        this.reserverId = reserverId;
        this.isCancelled = isCancelled;
    }

    public ExamHallSlot(int startTime, int endTime, int counter, String reservetionId, String reserverId, String isCancelled) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.counter = counter;
        this.reservetionId = reservetionId;
        this.reserverId = reserverId;
        this.isCancelled = isCancelled;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public void setReservetionId(String reservetionId) {
        this.reservetionId = reservetionId;
    }

    public void setReserverId(String reserverId) {
        this.reserverId = reserverId;
    }

    public void setCancelled(String cancelled) {
        isCancelled = cancelled;
    }

    public int getStartTime() {
        return startTime;

    }

    public int getEndTime() {
        return endTime;
    }

    public String getReservetionId() {
        return reservetionId;
    }

    public String getReserverId() {
        return reserverId;
    }

    public String isCancelled() {
        return isCancelled;
    }

    String reservetionId;
    String reserverId;
    String isCancelled;

    public void setIsCancelled(String isCancelled) {
        this.isCancelled = isCancelled;
    }

    public String getIsCancelled() {
        return isCancelled;
    }
}

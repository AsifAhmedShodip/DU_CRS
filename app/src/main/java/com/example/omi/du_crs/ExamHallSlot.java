/**
 * Created by aniomi on 3/26/18.
 */

package com.example.omi.du_crs;

public class ExamHallSlot {
    public ExamHallSlot(int startTime, int endTime, int reservetionId, String reserverId, boolean isCancelled) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservetionId = reservetionId;
        this.reserverId = reserverId;
        this.isCancelled = isCancelled;
    }

    public ExamHallSlot() {
    }

    int startTime;
    int endTime;
    int counter;

    public ExamHallSlot(int startTime, int endTime, int counter, int reservetionId, String reserverId, boolean isCancelled) {
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

    public void setReservetionId(int reservetionId) {
        this.reservetionId = reservetionId;
    }

    public void setReserverId(String reserverId) {
        this.reserverId = reserverId;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public int getStartTime() {
        return startTime;

    }

    public int getEndTime() {
        return endTime;
    }

    public int getReservetionId() {
        return reservetionId;
    }

    public String getReserverId() {
        return reserverId;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    int reservetionId;
    String reserverId;
    boolean isCancelled;

}

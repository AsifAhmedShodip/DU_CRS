package com.example.asif.du_crs;

/**
 * Created by asif on 19-Apr-18.
 */

public class Classroom_Object {
    String room,bookedBy,sTiem,eTime,detail;

    public Classroom_Object() {
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public String getsTiem() {
        return sTiem;
    }

    public void setsTiem(String sTiem) {
        this.sTiem = sTiem;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }
}

package com.example.omi.du_crs;

/**
 * Created by aniomi on 5/1/18.
 */

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
class mypair {

    int v1, v2;

    mypair(int a, int b) {
        v1 = a;
        v2 = b;
    }
}

public class AuditorioumDetails {

    int status;// 0 pending 1 accepted 2 cancel
    int startt;
    int endt;
    String requesterid;
    String subject,rdate;

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public AuditorioumDetails(int status, int startt, int endt, String requesterid, String subject, String rdate, String picloc, String reservetionid, String venue) {
        this.status = status;
        this.startt = startt;
        this.endt = endt;
        this.requesterid = requesterid;
        this.subject = subject;
        this.rdate = rdate;
        this.picloc = picloc;
        this.reservetionid = reservetionid;
        this.venue = venue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStartt() {
        return startt;
    }

    public void setStartt(int startt) {
        this.startt = startt;
    }

    public int getEndt() {
        return endt;
    }

    public void setEndt(int endt) {
        this.endt = endt;
    }

    public String getRequesterid() {
        return requesterid;
    }

    public void setRequesterid(String requesterid) {
        this.requesterid = requesterid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPicloc() {
        return picloc;
    }

    public void setPicloc(String picloc) {
        this.picloc = picloc;
    }

    public String getReservetionid() {
        return reservetionid;
    }

    public void setReservetionid(String reservetionid) {
        this.reservetionid = reservetionid;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    String picloc;
    String reservetionid;
    String venue;

    public AuditorioumDetails(int status, int startt, int endt,
                              String requesterid, String subject, String picloc, String reservetionid, String venue) {
        this.status = status;
        this.startt = startt;
        this.endt = endt;
        this.requesterid = requesterid;
        this.subject = subject;
        this.picloc = picloc;
        this.reservetionid = reservetionid;
        this.venue = venue;
    }

    AuditorioumDetails() {
        status = startt = endt = 0;
        venue=requesterid = subject =picloc=reservetionid= "N/A";
    }

    static boolean isfree(ArrayList<AuditorioumDetails> arr, int st, int ed,int needl,int needh)
    {
        int hp = 24 * 60 + 10;
        int[] flag = new int[hp];
        for (int i = 0; i < st; i++) {
            flag[i] = 1;
        }
        for (int i = ed + 1; i < hp; i++) {
            flag[i] = 1;
        }

        for (int i = 0; i < arr.size(); i++) {
            AuditorioumDetails tt = arr.get(i);
            int ss = tt.startt, ee = tt.endt;
            for (int k = ss; k <= ee; k++) {
                flag[k] = 1;
            }
        }

        for(int i=needl;i<=needh;i++)
        {
            if(flag[i]==1)
            {
                return false;
            }
        }

        return true;
    }

    static ArrayList<mypair> audfreeslots(ArrayList<AuditorioumDetails> arr, int st, int ed) {
        ArrayList<mypair> vec = new ArrayList<>();
        int hp = 24 * 60 + 10;
        int[] flag = new int[hp];
        for (int i = 0; i < st; i++) {
            flag[i] = 1;
        }
        for (int i = ed + 1; i < hp; i++) {
            flag[i] = 1;
        }

        for (int i = 0; i < arr.size(); i++) {
            AuditorioumDetails tt = arr.get(i);
            int ss = tt.startt, ee = tt.endt;
            for (int k = ss; k <= ee; k++) {
                flag[k] = 1;
            }
        }

        int ss = -1, ee = 0, cc = 1;

        for (int i = st; i <= ed; i++) {
            if (flag[i] == 0) {
                ss = i;
                break;
            }
        }
        if (ss == -1) {
            return vec;
        }

        boolean isCum = true;
        int t = 0;

        for (int i = ss; i <= ed + 1; i++) {
            if (isCum) {
                if (flag[i]==0) {
                    ee = i;
                } else {
                    vec.add(new mypair(ss,ee));
                    isCum = false;
                }

            } else {
                if ( flag[i] == 0) {
                    ss = i;
                    ee = i;
                    isCum = true;
                }
            }
        }

        return vec;
    }
}


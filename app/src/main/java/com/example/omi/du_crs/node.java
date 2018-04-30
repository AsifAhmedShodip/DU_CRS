package com.example.omi.du_crs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aniomi on 3/26/18.
 */

public class node {
    int y,m,d;
    static List<String> mp=new ArrayList<>();

    public node(int y, int m, int d) {
        this.y = y;
        this.m = m;
        this.d = d;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setM(int m) {
        this.m = m;
    }

    public void setD(int d) {
        this.d = d;
    }

    public node() {
    }
    boolean isGreater(node another)
    {
        if(this.y>another.y) return true;
        if(this.y==another.y && this.m>another.m) return true;
        if(this.y==another.y && this.m==another.m && this.d>another.d) return true;
        else return false;
    }
    static node stringtoclass(String s)
    {
        int a=(s.charAt(0)-'0')*10;
        a+=(s.charAt(1)-'0')*1;
        int b=(s.charAt(3)-'0')*10;
        b+=(s.charAt(4)-'0')*1;
        int c=(s.charAt(6)-'0')*1000;
        c+=(s.charAt(7)-'0')*100;
        c+=(s.charAt(8)-'0')*10;
        c+=(s.charAt(9)-'0')*1;
        return new node(c,b,a);
    }
    static String classtostring(node n)
    {
        String s="";
        if(n.d<10) s+="0"+n.d+"/";
        else s+=n.d+"/";
        if(n.m<10) s+="0"+n.m+"/";
        else s+=n.m+"/";
        return s+n.y;
    }
}

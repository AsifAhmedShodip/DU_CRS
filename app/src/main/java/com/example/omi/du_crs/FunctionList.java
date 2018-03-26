
package com.example.omi.du_crs;
import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aniomi on 3/26/18.
 */
public class FunctionList {
    static int getminute(int hourss,int minutess)
    {
        return hourss*60+minutess;
    }
    static boolean isTheTimeSlotFree(int st, int ed,int capacity,int need, ArrayList<ExamHallSlot> Bookedslots)
    {
        int flag[]=new int[2000];
        for(int i=0;i<2000;i++)
        {
            flag[i]=capacity;
        }
        int l=Bookedslots.size();
        for(int i=0;i<l;i++)
        {
            int stt=Bookedslots.get(i).startTime;
            int ett=Bookedslots.get(i).endTime;
            int count=Bookedslots.get(i).counter;
            for(int j=stt;j<=ett;j++)
            {
                flag[j]-=count;
            }
        }
        for(int i=st;i<=ed;i++)
        {
            if(flag[i]<need) return false;
        }
        return true;
    }
    static <Type> List<Type> createListFromFirebase(Type temp,Object obj,DataSnapshot dataSnapshot) throws ClassNotFoundException {
        obj.getClass();
        List<Type> list=new ArrayList<>();

        for(DataSnapshot users : dataSnapshot.getChildren())
        {
            Class cl=obj.getClass();
            obj=users.getValue(obj.getClass());
            list.add((Type) obj);
        }
        return list;
        //push in package
    }
    static node nextday(node prev)
    {
        int daycount[]=new int[20];
        for(int i=1;i<=7;i+=2)
        {
            daycount[i]=31;
        }
        for(int i=8;i<=12;i+=2)
        {
            daycount[i]=31;
        }
        for(int i=9;i<=12;i+=2)
        {
            daycount[i]=30;
        }
        for(int i=2;i<=7;i+=2)
        {
            daycount[i]=30;
        }
        daycount[2]=28;
        int x=prev.y;
        boolean xp=((x%400==0) || (x%4==0 && x%100!=0));
        if(xp==true) daycount[2]+=1;
        node next=new node();
        if(prev.d==daycount[prev.m] && prev.m==12)
        {
            next.y=prev.y+1;
            next.d=next.m=1;
        }
        else if(prev.d==daycount[prev.m])
        {
            next.y=prev.y;
            next.d=1;
            next.m=prev.m+1;
        }
        else
        {
            next.y=prev.y;
            next.d=prev.d+1;
            next.m=prev.m;
        }
        return next;
    }
    boolean isNormalHoliday(node curr)
    {
        return true;
    }

}

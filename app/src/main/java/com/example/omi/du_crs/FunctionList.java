
package com.example.omi.du_crs;
import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by aniomi on 3/26/18.
 */

class ExamResComp implements Comparator<ExamHallSlot>
{
    public int compare(ExamHallSlot a,ExamHallSlot b)
    {
        node na=node.stringtoclass(a.getRdate());
        node nb=node.stringtoclass(b.getRdate());
        if(na.isGreater(nb) && nb.isGreater(na))
        {
            if(a.getStartTime()!=b.getStartTime())
            {
                if(a.getEndTime()>b.getEndTime())
                {
                    return 0;
                }
                return 1;
            }
            else
            {
                if(a.getStartTime()>b.getStartTime())
                {
                    return 0;
                }
                return 1;
            }
        }
        else if(na.isGreater(nb)) return 0;
        return 1;
    }
}

class free_slots
{
    String start_t,end_t,mdate;

    public free_slots(String start_t, String end_t, String mdate) {
        this.start_t = start_t;
        this.end_t = end_t;
        this.mdate = mdate;
    }

    public free_slots() {
    }
}
class search_hall_date_range
{
    String start_date,end_date,hall_name;
    int count;

    static String standardtime(int ms)
    {
        String s="";
        String isPm="AM";
        if(ms>12*60) isPm="PM";
        ms=(ms%(12*60));
        int sh=ms/60;
        int sm=ms%60;
        if(sh==0) sh=12;

        s=sh+":"+sm+" "+isPm;
        s="";
        if(sh<10) s+="0"+sh+":";
        else s+=sh+":";
        if(sm<10) s+="0"+sm;
        else s+=sm;
        s+=" "+isPm;
        return s;
    }

    public search_hall_date_range(String start_date, String end_date, String hall_name, int count) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.hall_name = hall_name;
        this.count = count;
    }

    public search_hall_date_range() {
    }
}
public class FunctionList {
    static int getminute(String s)
    {
        int t=0;
        t+=(((s.charAt(0)-'0')*10)+((s.charAt(1)-'0')*1))*60;
        t+=(((s.charAt(3)-'0')*10)+((s.charAt(4)-'0')*1));
        if(s.charAt(6)=='P')
        {
            t+=(12*60);
        }
        return t;
    }
    static search_hall_date_range exam_hall_search=new search_hall_date_range();
    static boolean isTheTimeSlotFree(int st, int ed,int rangel,int rangeh,int capacity,int need, ArrayList<ExamHallSlot> Bookedslots)
    {
        int flag[]=new int[2000];
        for(int i=0;i<2000;i++)
        {
            flag[i]=capacity;
        }
        for(int i=0;i<rangel;i++) flag[i]=0;
        for(int i=rangeh+1;i<2000;i++) flag[i]=0;
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
    static ArrayList<free_slots> EmptySlots(int need,int rangel,int rangeh,int capacity,ArrayList<ExamHallSlot> Bookedslots,String rdates)
    {
        ArrayList<free_slots> FreeList=new ArrayList<>();
        int flag[]=new int[2000];
        for(int i=0;i<2000;i++)
        {
            flag[i]=capacity;
        }
        for(int i=0;i<rangel;i++)
        {
            flag[i]=0;
        }
        for(int i=rangeh+1;i<2000;i++)
        {
            flag[i]=0;
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
        int ss=-1,ee=0,cc=1;
        for(int i=rangel;i<=rangeh;i++)
        {
            if(flag[i]>=need)
            {
                ss=i;
                break;
            }
        }
        if(ss==-1) return FreeList;
        boolean isCum=true;
        int t=0;
        for(int i=ss;i<=rangeh+1;i++)
        {
            if(isCum)
            {
                if(need<=flag[i])
                {
                    ee=i;
                }
                else
                {
                    FreeList.add(new free_slots(search_hall_date_range.standardtime(ss),search_hall_date_range.standardtime(ee),rdates));
                    isCum=false;
                }

            }
            else
            {
                if(need<=flag[i])
                {
                    ss=i;
                    ee=i;
                    isCum=true;
                }
            }
        }
        return FreeList;
    }

}

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
        //
    }

}

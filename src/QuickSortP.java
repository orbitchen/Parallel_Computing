import java.util.concurrent.CountDownLatch;

public class QuickSortP {
    //1 0

    public static Integer LOCK=0;

    int[] data;
    //在data中排好序
    int begin,end;
    private void exchange(int a,int b)
    {
        int temp=data[a];
        data[a]=data[b];
        data[b]=temp;
    }

    private int partition(int a,int b)
    {
        int p=data[b];
        int begin=a;
        for(int i=a;i<b;i++)
        {
            if(data[i]<p)
            {
                exchange(begin, i);
                begin++;
            }
        }
        exchange(begin,b);
        return begin;

    }

    private void quickSort(int a,int b)
    {
        if(a>=b)
            return;
        int r=partition(a,b);
        quickSort(a,r-1);
        quickSort(r+1,b);
    }

    CountDownLatch cdl;
    public void sort()
    {
        data=Main.getData();
        begin=0;end=data.length-1;
        cdl=new CountDownLatch(2);

        Timer t=new Timer();
        t.timerBegin();

        if(begin>=end)
            return;

        int mid=partition(begin,end);
        QuickSortP_Thread son1,son2;
        son1=new QuickSortP_Thread();
        son2=new QuickSortP_Thread();
        son1.setValues(data,cdl,begin,mid);
        son2.setValues(data,cdl,mid+1,end);

        son1.start();
        son2.start();

        try {
            cdl.await();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        t.timerEnd(1,0);
    }

    public int[] getSortedData()
    {
        return data;
    }
}

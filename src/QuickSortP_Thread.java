import java.util.concurrent.CountDownLatch;

public class QuickSortP_Thread extends Thread {

    int[] data;
    CountDownLatch cdl_father;
    CountDownLatch cdl;

    int begin;
    int end;

    public static final int MAX=128;

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

    public void setValues(int[] data,CountDownLatch fcdl,int data_begin,int data_end)
    {
        this.data=data;
        cdl_father=fcdl;
        begin=data_begin;
        end=data_end;
        cdl=new CountDownLatch(2);
        //System.out.println("子线程："+begin+","+end);
    }

    @Override
    public void run()
    {
        //System.out.println("子线程："+begin+","+end);
        if(begin>=end)
        {
            cdl_father.countDown();
            return;
        }

        if(end-begin+1<=MAX)
        {
            quickSort(begin,end);
            cdl_father.countDown();
        }
        else
        {
            int mid=partition(begin,end);
            QuickSortP_Thread son1,son2;
            son1=new QuickSortP_Thread();
            son2=new QuickSortP_Thread();
            son1.setValues(data,cdl,begin,mid-1);
            son2.setValues(data,cdl,mid+1,end);
            son1.start();
            son2.start();

            try{
                cdl.await();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            cdl_father.countDown();

        }

    }
}

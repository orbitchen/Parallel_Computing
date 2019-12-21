import java.util.concurrent.CountDownLatch;

public class EnumSortP {
    //1 1
    public int[] data;
    public int[] sortedData;
    public Timer t;

    public CountDownLatch cdl=new CountDownLatch(Main.threadNum);
    public void sort()
    {
        data=Main.getData();
        sortedData=new int[data.length];

        t=new Timer();
        t.timerBegin();

        int part=data.length/Main.threadNum;
        int begin=0;int end=part;
        for(int i=0;i<Main.threadNum;i++)
        {
            EnumSortP_Thread th=new EnumSortP_Thread();
            th.setValue(begin,end-1,this);
            th.start();
            begin=end;
            end+=part;
            if(i+2==Main.threadNum)
                end=data.length;
        }

        try {
            cdl.await();
            t.timerEnd(1,1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public int[] getSortedData()
    {
        return sortedData;
    }
}

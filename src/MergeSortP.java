import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class MergeSortP {
    //1 2
    public int[] data;
    MergeSortP_Thread[] sons=new MergeSortP_Thread[Main.threadNum];

    public CountDownLatch cdl1=new CountDownLatch(Main.threadNum);

    public int[][] phase1Sample;
    public int[] phase1SampleArray;

    public int[][] phase3Sample=new int[Main.threadNum][];

    public CountDownLatch cdl2=new CountDownLatch(Main.threadNum);

    public CountDownLatch cdl3=new CountDownLatch(Main.threadNum);

    public static int[] randomSelect(int[] a,int p)
    {
        boolean[] selected=new boolean[a.length];
        for(int i=0;i<selected.length;i++)
            selected[i]=false;

        int k=0;
        int[] retVal=new int[p];
        Random random=new Random();
        for(k=0;k<p;k++)
        {
            int r=random.nextInt(a.length);
            while(selected[r])
                r=random.nextInt(a.length);
            retVal[k]=a[r];
        }

        return retVal;

    }

    private void mergeSort(int[] array,int a,int b)
    {
        if(a>=b) return;
        int mid=(a+b)/2;
        mergeSort(array,a,mid);
        mergeSort(array,mid+1,b);

        int[] temp=new int[b-a+1];
        int i=a;
        int j=mid+1;
        int k=0;
        while(i<=mid&&j<=b)
        {
            if(array[i]<=array[j])
                temp[k++]=array[i++];
            else
                temp[k++]=array[j++];
        }
        while(i<=mid)
            temp[k++]=array[i++];
        while(j<=b)
            temp[k++]=array[j++];

        for(i=0;i<k;i++)
            array[a+i]=temp[i];

    }

    public void sort() throws Exception
    {
        data=Main.getData();
        Timer t=new Timer();
        t.timerBegin();
        phase1();
        cdl1.await();
        phase2();
        synchronized (cdl1)
        {
            cdl1.notifyAll();
        }


        cdl2.await();
        synchronized (cdl2) {
            cdl2.notifyAll();
        }

        cdl3.await();

        phase3();
        t.timerEnd(1,2);


    }

    public int[] getSortedData()
    {
        return data;
    }

    private void phase1()
    {
        //均匀划分
        int part=data.length/Main.threadNum;
        int begin=0;int end=part;
        for(int i=0;i<Main.threadNum;i++)
        {
            MergeSortP_Thread mspt=new MergeSortP_Thread();
            sons[i]=mspt;
            mspt.setValues(i, Arrays.copyOfRange(data,begin,end),this,sons);
            begin=end;
            end+=part;
            if(i+2==Main.threadNum)
                end=data.length;
        }

        phase1Sample=new int[Main.threadNum][Main.threadNum];

        for(int i=0;i<Main.threadNum;i++)
            sons[i].start();
    }

    private void phase2()
    {
        phase1SampleArray=new int[Main.threadNum*Main.threadNum];
        for(int i=0;i<Main.threadNum;i++){
            for(int j=0;j<Main.threadNum;j++){
                phase1SampleArray[i*Main.threadNum+j]=phase1Sample[i][j];}}

        mergeSort(phase1SampleArray,0,phase1SampleArray.length-1);
        int[] phase2Sample=randomSelect(phase1SampleArray,Main.threadNum-1);

        mergeSort(phase2Sample,0,phase2Sample.length-1);

        for(int i=0;i<Main.threadNum;i++)
        {
            sons[i].phase2Sample=phase2Sample;
        }
    }

    private  void phase3()
    {
        int k=0;
        for(int i=0;i<Main.threadNum;i++)
        {
            if(phase3Sample[i]!=null)
            {
                for(int j=0;j<phase3Sample[i].length;j++) {
                    data[k] = phase3Sample[i][j];
                    k++;
                }
            }
        }

    }
}

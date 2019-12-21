import java.util.Arrays;

public class MergeSortP_Thread extends Thread {
    public int[] data;
    MergeSortP father;
    MergeSortP_Thread[] brothers;

    int ID;

    int[] phase2Sample;

    int [][]phase2Received=new int[Main.threadNum][];

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



    public void setValues(int ID,int[] data,MergeSortP f,MergeSortP_Thread[] b)
    {
        this.ID=ID;
        this.data=data;
        father=f;
        brothers=b;
    }

    @Override
    public void run()
    {
        phase1();
        synchronized (father.cdl1)
        {
            try {
                father.cdl1.countDown();
                father.cdl1.wait();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        phase2();
        synchronized (father.cdl2)
        {
            try {
                father.cdl2.countDown();
                father.cdl2.wait();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        phase3();
        father.cdl3.countDown();
    }

    private void phase1()
    {
        mergeSort(data,0,data.length-1);
        synchronized (father.phase1Sample)
        {
            father.phase1Sample[ID]=MergeSortP.randomSelect(data,Main.threadNum);
        }
    }

    private void phase2()
    {
        int begin=0;
        int j=0;

        int[] temp=new int[Main.threadNum];
        for(int i=0;i<Main.threadNum-1;i++)
            temp[i]=phase2Sample[i];
        temp[Main.threadNum-1]=9999999;
        phase2Sample=temp;

        for(int i=0;i<data.length;i++)
        {
            if(data[i]>phase2Sample[j])
            {
                brothers[j].phase2Received[ID]=Arrays.copyOfRange(data,begin,i);
                begin=i;
                j++;
            }
        }
        brothers[j].phase2Received[ID]=Arrays.copyOfRange(data,begin,data.length);
    }

    private void phase3()
    {
        int[] phase3Data;
        int size=0;
        for(int i=0;i<Main.threadNum;i++)
        {
            if(phase2Received[i]!=null)
                size+=phase2Received[i].length;
        }
        phase3Data=new int[size];
        int k=0;
        for(int i=0;i<Main.threadNum;i++)
        {
            if(phase2Received[i]!=null)
            {
                for(int j=0;j<phase2Received[i].length;j++)
                {
                    phase3Data[k]=phase2Received[i][j];
                    k++;
                }
            }
        }

        mergeSort(phase3Data,0,phase3Data.length-1);
        father.phase3Sample[ID]=phase3Data;
    }
}

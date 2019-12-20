public class QuickSort {
    //0 0
    int[] data;

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
    public void sort()
    {
        Timer t=new Timer();
        t.timerBegin();
        data=Main.getData();
        quickSort(0,data.length-1);
        t.timerEnd(0,0);

    }
}

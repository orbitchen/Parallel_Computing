public class MergeSort {
    //0 2
    int[] data;
    int[] sortedData;
    private void mergeSort(int a,int b)
    {
        if(a>=b) return;
        int mid=(a+b)/2;
        mergeSort(a,mid);
        mergeSort(mid+1,b);

        int[] temp=new int[b-a+1];
        int i=a;
        int j=mid+1;
        int k=0;
        while(i<=mid&&j<=b)
        {
            if(data[i]<=data[j])
                temp[k++]=data[i++];
            else
                temp[k++]=data[j++];
        }
        while(i<=mid)
            temp[k++]=data[i++];
        while(j<=b)
            temp[k++]=data[j++];

        for(i=0;i<k;i++)
            data[a+i]=temp[i];

    }
    public void sort()
    {
        data=Main.getData();
        Timer t=new Timer();
        t.timerBegin();
        mergeSort(0,data.length-1);
        t.timerEnd(0,2);


    }
}

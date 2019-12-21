public class EnumSort {
    //0 1
    private int[] data;
    private int[] sortedData;
    //在sortedData中
    private void sortOne(int location)
    {
        //获得data[location]应该所在的新的位置，并放置。
        int new_location=0;
        for(int i=0;i<data.length;i++)
        {
            if(data[i]==data[location]&&i<location)
                new_location++;
            else if(data[i]<data[location])
                new_location++;
        }
        sortedData[new_location]=data[location];
    }
    public void sort()
    {
        data=Main.getData();
        sortedData=new int[data.length];
        Timer t=new Timer();
        t.timerBegin();
        for(int i=0;i<data.length;i++)
            sortOne(i);
        t.timerEnd(0,1);
    }

    public int[] getSortedData()
    {
        return sortedData;
    }
}

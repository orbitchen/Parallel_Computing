public class EnumSortP_Thread extends Thread {
    private int indexBegin;
    private int indexEnd;

    EnumSortP father;
    public void setValue(int begin,int end,EnumSortP f)
    {
        indexBegin=begin;indexEnd=end;

        father=f;
    }

    private void  sortOne(int location)
    {
        //获得data[location]应该所在的新的位置，并放置。
        int new_location=0;
        for(int i=0;i<father.data.length;i++)
        {
            if(father.data[i]==father.data[location]&&i<location)
                new_location++;
            else if(father.data[i]<father.data[location])
                new_location++;
        }
        synchronized (this) {
            father.sortedData[new_location] = father.data[location];
        }
    }

    @Override
    public void run()
    {
        for(int i=indexBegin;i<=indexEnd;i++)
            sortOne(i);

        synchronized (this){
            father.threadFinish++;
        }

    }

}

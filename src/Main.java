import java.io.*;
import java.nio.Buffer;

public class Main {

    //快排 枚举 归并
    public static int[] data;
    public static double[][] globalTime;
    public static final int threadNum=8;
    //线程数量
    public static void main(String[] argv)
    {
        read();
        globalTime=new double[2][3];

        new QuickSort().sort();
        new QuickSortP().sort();

        new EnumSort().sort();
        new EnumSortP().sort();

        new MergeSort().sort();
        new MergeSortP().sort();
    }

    private static void read()
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("random.txt"));
            String temp=reader.readLine();
            String[] temps=temp.split(" ");
            data=new int[temps.length];
            for(int i=0;i<temps.length;i++)
                data[i]=Integer.parseInt(temps[i]);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public static int[] getData()
    {
        return data.clone();
    }
}

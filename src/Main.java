import java.io.*;
import java.nio.Buffer;
import java.util.Arrays;

public class Main {

    //快排 枚举 归并
    public static int[] data;
    public static double[][] globalTime;
    public static final int threadNum=4;
    //线程数量
    static int[][] sortedData=new int[6][];
    public static void main(String[] argv)
    {
        read();
        globalTime=new double[2][3];

        try {

            QuickSort qs=new QuickSort();
            qs.sort();sortedData[0]=qs.getSortedData();

            QuickSortP qsp=new QuickSortP();
            qsp.sort();sortedData[1]=qsp.getSortedData();

            EnumSort es=new EnumSort();
            es.sort();sortedData[2]=es.getSortedData();

            EnumSortP esp=new EnumSortP();
            esp.sort();sortedData[3]=esp.getSortedData();

            MergeSort ms=new MergeSort();
            ms.sort();sortedData[4]=ms.getSortedData();

            MergeSortP msp=new MergeSortP();
            msp.sort();sortedData[5]=msp.getSortedData();

            Arrays.sort(data);
            for(int i=0;i<6;i++)
            {
                if(!Arrays.equals(data,sortedData[i]))
                    System.out.println("排序结果错误："+i);
            }

            writeFiles();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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

    public static void writeFiles()
    {
        try {
            for (int i = 0; i < 6; i++) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(("order" + (i + 1) + ".txt")));
                for(int j=0;j<sortedData[i].length;j++)
                    bw.write(sortedData[i][j]+" ");
                bw.close();
            }

            BufferedWriter bw2=new BufferedWriter(new FileWriter("time.txt"));
            bw2.write(" 快速排序 枚举排序 归并排序\n");
            bw2.write("串行"+" "+globalTime[0][0]+" "+globalTime[0][1]+" "+globalTime[0][2]+"\n");
            bw2.write("并行"+" "+globalTime[1][0]+" "+globalTime[1][1]+" "+globalTime[1][2]);
            bw2.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

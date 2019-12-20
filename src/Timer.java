public class Timer {
    private long timeS;
    private long timeE;
    public void timerBegin()
    {
        timeS=System.currentTimeMillis();
    }
    public void timerEnd(int a,int b)
    {
        timeE=System.currentTimeMillis();
        Main.globalTime[a][b]=(double)(timeE-timeS)/1000.0;
    }
}

import java.util.*;

public class LogLane
{
    private Deque<Log> heldLogs = new ArrayDeque<>();
    private ArrayList<Log> visibleLogs = new ArrayList<>();
    private int laneY;
    private float gapTime;
    private float tempo;
    private int numLogs;
    private int logCount = 0;
    //private long elapsedNanoTime = 0;
    private double elapsedTime = 0;
    public LogLane(float speed, int numLogs, int laneY, float gapTime, float tempo)
    {
        this.laneY = laneY;
        this.gapTime = gapTime;
        this.tempo = tempo;
        this.numLogs = numLogs;
        for(int i = 0; i < numLogs; i++)
        {
            heldLogs.add(new Log(new Coord(-20, laneY), speed));
        }
    }

    public void updateLogLane(double dTime, long elapsedNanoTime, int areaWidth)
    {
        for(int i = 0; i < visibleLogs.size(); i++)
        {
            visibleLogs.get(i).moveLog(dTime);
            if(visibleLogs.get(i).getCenter().getX() > areaWidth)
            {
                visibleLogs.get(i).resetX();
                heldLogs.add(visibleLogs.get(i));
                visibleLogs.remove(i);
            }
        }

        elapsedTime +=  dTime;
        if(logCount < numLogs)
        {
            //System.out.println(logCount);
            if(elapsedTime > tempo)
            {
                visibleLogs.add(heldLogs.remove());
                this.elapsedTime = 0;
                logCount++;
            }
        }
        else
        {
            if(elapsedTime > gapTime && !heldLogs.isEmpty())
            {
                logCount = 0;
                this.elapsedTime = 0;
            }
        }

    }

    public ArrayList<Log> getVisibleLogs() {
        return visibleLogs;
    }
}
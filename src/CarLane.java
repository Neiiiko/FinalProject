import java.util.*;

public class CarLane
{
    private Deque<Car> heldCars = new ArrayDeque<>();
    private ArrayList<Car> visibleCars = new ArrayList<>();
    private int laneY;
    private float gapTime;
    private float tempo;
    private int numCars;
    private int carCount = 0;
    private long elapsedNanoTime = 0;

    public CarLane(float speed, int numCars, int laneY, float gapTime, float tempo)
    {
        this.laneY = laneY;
        this.gapTime = gapTime;
        this.tempo = tempo;
        this.numCars = numCars;
        for(int i = 0; i < numCars; i++)
        {
            heldCars.add(new Car(new Coord(-20, laneY), speed));
        }
    }

    public void updateLane(double dTime, long elapsedNanoTime, int areaWidth)
    {
        for(int i = 0; i < visibleCars.size(); i++)
        {
            visibleCars.get(i).moveCar(dTime);
            if(visibleCars.get(i).getCenter().getX() > areaWidth)
            {
                visibleCars.get(i).resetX();
                heldCars.add(visibleCars.get(i));
                visibleCars.remove(i);
            }
        }

        double elapsedSeconds = this.elapsedNanoTime / 1000000000.0;
        if(carCount < numCars)
        {
            //System.out.println(carCount);
            if(elapsedSeconds > tempo)
            {
                visibleCars.add(heldCars.remove());
                this.elapsedNanoTime = 0;
                carCount++;
            }
            else
            {
                this.elapsedNanoTime += elapsedNanoTime;
            }
        }
        else
        {
            if(elapsedSeconds > gapTime && !heldCars.isEmpty())
            {
                carCount = 0;
                this.elapsedNanoTime = 0;
            }
            else
            {
                this.elapsedNanoTime += elapsedNanoTime;
            }
        }

    }

    public ArrayList<Car> getVisibleCars() {
        return visibleCars;
    }
}

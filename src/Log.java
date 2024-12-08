public class Log
{
    private Coord center;
    //private int length = 120;
    private int size = 40;
    private Collider collider;
    private Coord upperRightCorner;
    private float speed;
    private double lastAmountMoved;


    public Log(Coord center, float speed)
    {
        this.speed = speed;
        this.center = center;
        this.upperRightCorner = new Coord(center.getX() - size/2, center.getY() - size/2);
        collider = new Collider(center, size);
    }

    public int getSize()
    {
        return size;
    }

//    private int getWidth()
//    {
//        return width;
//    }

    public Coord getCenter()
    {
        return center;
    }

    public void resetX()
    {
        center.setX(-size/2);
        this.upperRightCorner = new Coord(center.getX() - size/2, center.getY() - size/2);
    }

    public Coord getUpperRightCorner()
    {
        return upperRightCorner;
    }

    public void moveLog(double dTime)
    {
        lastAmountMoved = speed*dTime;
        center.addToX(lastAmountMoved);
        this.upperRightCorner = new Coord(center.getX() - size/2, center.getY() - size/2);
        //System.out.println(center);
    }

    public double getLastAmountMoved()
    {
        return lastAmountMoved;
    }

        //change to work with colliders
        //make colliders

        //got collision equation from here
        //https://stackoverflow.com/questions/17157785/i-have-two-squares-drawn-on-the-screen-how-can-i-detect-collision-on-the-edges
    public boolean isCollidingWith(Frog other)
    {
        return collider.isCollidingWith(other);
    }
}

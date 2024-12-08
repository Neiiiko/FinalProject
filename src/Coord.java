public class Coord
{
    private double x = 0;
    private double y = 0;

    public Coord(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void addToX(double num)
    {
        x = x + num;
    }
    public void addToY(double num)
    {
        y = y + num;
    }

    public boolean equals(Coord other) {
        return other.x == this.x && other.y == this.y;
    }

    @Override
    public String toString()
    {
        return x + ", " + y;
    }
}

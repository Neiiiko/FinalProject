public class Collider
{
    //size must be divisible by 2
    private int width;
    private int height;
    private Coord center;

    public Collider(Coord center, int size)
    {
        this.center = center;
        this.height = size;
        this.width = size;
    }

    public Collider(Coord center, int width, int height)
    {
        this.center = center;
        this.height = height;
        this.width = width;
    }

    public Coord getCenter()
    {
        return center;
    }

    public int getHeight()
    {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isCollidingWith(Frog other)
    {
        return Math.abs(center.getX() - other.getCollider().getCenter().getX()) <= width / 2 + other.getCollider().getWidth() / 2
                && Math.abs(center.getY() - other.getCollider().getCenter().getY()) < height / 2 + other.getCollider().getHeight() / 2;
    }
}
